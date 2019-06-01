package org.knowm.xchange.zaif;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.zaif.dto.account.ZaifAccountInfo;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBookTier;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.knowm.xchange.zaif.dto.trade.ZaifTrades;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTicker;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZaifAdapters {

  private static Logger logger = LoggerFactory.getLogger(ZaifAdapters.class);

  public ZaifAdapters() {}

  public static OrderBook adaptOrderBook(ZaifFullBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), Order.OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), Order.OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(
      ZaifFullBookTier[] levels, Order.OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>();

    if (levels != null) {
      for (ZaifFullBookTier tier : levels) {
        allLevels.add(
            new LimitOrder(orderType, tier.getVolume(), currencyPair, "", null, tier.getPrice()));
      }
    }

    return allLevels;
  }

  public static ExchangeMetaData adaptMetadata(List<ZaifMarket> markets) {
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (ZaifMarket zaifMarket : markets) {
      pairMeta.put(zaifMarket.getName(), new CurrencyPairMetaData(null, null, null, null));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }

  public static List<Wallet> adaptWallet(ZaifAccountInfo response) {
    List<Wallet> balances = new ArrayList<Wallet>(3);

    balances.add(new Wallet("JPY", new Balance(new Currency("JPY"), response.getDeposit().getJpy())));
    balances.add(new Wallet("BTC", new Balance(new Currency("BTC"), response.getDeposit().getBtc())));
    balances.add(new Wallet("MONA", new Balance(new Currency("MONA"), response.getDeposit().getMona())));

    return balances;
  }

  public static OpenOrders adaptOrders(ZaifTrades[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(activeOrders.length);

    for (ZaifTrades order : activeOrders) {
      Order.OrderType orderType = order.getType().equalsIgnoreCase("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
      CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
      Date timestamp = convertBigDecimalTimestampToDate(order.getTimestamp());
      limitOrders
              .add(new LimitOrder(orderType, order.getAmount(), currencyPair, order.getOrderId(), timestamp, order.getPrice()));
    }

    return new OpenOrders(limitOrders);
  }

  public static Trade adaptTrade(ZaifTrade trade, CurrencyPair currencyPair) {

    Order.OrderType orderType = trade.getType().equals("buy") ? Order.OrderType.BID : Order.OrderType.ASK;
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp() * 1000L); // Zaif uses Unix timestamps
    final String tradeId = String.valueOf(trade.getTradeId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(ZaifTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(trades.length);
    long lastTradeId = 0;
    for (ZaifTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(ZaifTicker zaifTicker, CurrencyPair currencyPair) {

    BigDecimal last = zaifTicker.getLast_price();
    BigDecimal bid = zaifTicker.getBid();
    BigDecimal ask = zaifTicker.getAsk();
    BigDecimal high = zaifTicker.getHigh();
    BigDecimal low = zaifTicker.getLow();
    BigDecimal volume = zaifTicker.getVolume();
    BigDecimal vwap = zaifTicker.getVwap();

//    Date timestamp = DateUtils.fromMillisUtc((long) (zaifTicker.getTimestamp() * 1000L));
    Date timestamp = new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
            .build();
  }

  public static UserTrades adaptTradeHistory(ZaifTrades[] trades, String symbol) {

    List<UserTrade> pastTrades = new ArrayList<UserTrade>(trades.length);
    CurrencyPair currencyPair = adaptCurrencyPair(symbol);

    for (ZaifTrades trade : trades) {
//      OrderType orderType = trade.getType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
      Order.OrderType orderType = trade.getYourAction().equalsIgnoreCase("bid") ? Order.OrderType.BID : Order.OrderType.ASK;
      Date timestamp = convertBigDecimalTimestampToDate(trade.getTimestamp());
      final BigDecimal fee = trade.getFee() == null ? null : trade.getFee().negate();
      pastTrades.add(new UserTrade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getOrderId(), trade.getOrderId(),
              fee, currencyPair.counter));
    }

    return new UserTrades(pastTrades, Trades.TradeSortType.SortByTimestamp);
  }

  public static CurrencyPair adaptCurrencyPair(String zaifSymbol) {

    String tradableIdentifier = zaifSymbol.substring(0, 3).toUpperCase();
    String transactionCurrency = zaifSymbol.substring(4).toUpperCase();
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return (pair.base.getSymbol() + "_" + pair.counter.getSymbol()).toLowerCase();
  }

  private static Date convertBigDecimalTimestampToDate(BigDecimal timestamp) {

    BigDecimal timestampInMillis = timestamp.multiply(new BigDecimal("1000"));
    return new Date(timestampInMillis.longValue());
  }

}
