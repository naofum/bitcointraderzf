package com.xeiam.xchange.zaif.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.zaif.v1.dto.account.ZaifAccountInfo;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifDepth;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifTicker;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifTrade;
import com.xeiam.xchange.zaif.v1.dto.trade.ZaifTrades;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.utils.DateUtils;

public final class ZaifAdapters {

  public static final Logger log = LoggerFactory.getLogger(ZaifAdapters.class);

  private ZaifAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> zaifSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (String symbol : zaifSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(String zaifSymbol) {

    String tradableIdentifier = zaifSymbol.substring(0, 3).toUpperCase();
    String transactionCurrency = zaifSymbol.substring(4).toUpperCase();
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return (pair.baseSymbol + "_" + pair.counterSymbol).toLowerCase();
  }

  public static OrderBook adaptOrderBook(ZaifDepth btceDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer = adaptOrders(btceDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(btceDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
  }

  public static OrdersContainer adaptOrders(List<List<BigDecimal>> zaifLevels, CurrencyPair currencyPair, OrderType orderType) {

    BigDecimal maxTimestamp = new BigDecimal(Long.MIN_VALUE);
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(zaifLevels.size());

    for (List<BigDecimal> zaifLevel : zaifLevels) {
      if (zaifLevel.size() > 1) {
        limitOrders.add(adaptOrder(zaifLevel.get(1), zaifLevel.get(0), currencyPair, orderType, new Date()));
      }
    }

    long maxTimestampInMillis = maxTimestamp.multiply(new BigDecimal(1000l)).longValue();
    return new OrdersContainer(maxTimestampInMillis, limitOrders);
  }

  public static class OrdersContainer {

    private final long timestamp;
    private final List<LimitOrder> limitOrders;

    /**
     * Constructor
     *
     * @param timestamp
     * @param limitOrders
     */
    public OrdersContainer(long timestamp, List<LimitOrder> limitOrders) {

      this.timestamp = timestamp;
      this.limitOrders = limitOrders;
    }

    public long getTimestamp() {

      return timestamp;
    }

    public List<LimitOrder> getLimitOrders() {

      return limitOrders;
    }
  }

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, Date timestamp) {

    return new LimitOrder(orderType, amount, currencyPair, "", timestamp, price);
  }

  public static Trade adaptTrade(ZaifTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp() * 1000L); // Zaif uses Unix timestamps
    final String tradeId = String.valueOf(trade.getTradeId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(ZaifTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(trades.length);
    long lastTradeId = 0;
    for (com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
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

  public static List<Wallet> adaptWallet(ZaifAccountInfo response) {

    List<Wallet> balances = new ArrayList<Wallet>(3);

    balances
            .add(new Wallet("JPY", response.getDeposit().getJpy(), response.getFunds().getJpy()));
    balances
            .add(new Wallet("BTC", response.getDeposit().getBtc(), response.getFunds().getBtc()));
    balances
            .add(new Wallet("MONA", response.getDeposit().getMona(), response.getFunds().getMona()));

    return balances;
  }

  public static OpenOrders adaptOrders(ZaifTrades[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(activeOrders.length);

    for (ZaifTrades order : activeOrders) {
      OrderType orderType = order.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
      Date timestamp = convertBigDecimalTimestampToDate(order.getTimestamp());
      limitOrders
          .add(new LimitOrder(orderType, order.getAmount(), currencyPair, order.getOrderId(), timestamp, order.getPrice()));
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(ZaifTrades[] trades, String symbol) {

    List<UserTrade> pastTrades = new ArrayList<UserTrade>(trades.length);
    CurrencyPair currencyPair = adaptCurrencyPair(symbol);

    for (ZaifTrades trade : trades) {
//      OrderType orderType = trade.getType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
      OrderType orderType = trade.getYourAction().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
      Date timestamp = convertBigDecimalTimestampToDate(trade.getTimestamp());
      final BigDecimal fee = trade.getFee() == null ? null : trade.getFee().negate();
      pastTrades.add(new UserTrade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getOrderId(), trade.getOrderId(),
          fee, currencyPair.counterSymbol));
    }

    return new UserTrades(pastTrades, TradeSortType.SortByTimestamp);
  }

  private static Date convertBigDecimalTimestampToDate(BigDecimal timestamp) {

    BigDecimal timestampInMillis = timestamp.multiply(new BigDecimal("1000"));
    return new Date(timestampInMillis.longValue());
  }
}
