package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.zaif.ZaifAdapters;
import org.knowm.xchange.zaif.ZaifUtils;
import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.zaif.dto.account.ZaifWallet;
import org.knowm.xchange.zaif.dto.account.ZaifWalletResponse;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.trade.ZaifOrderStatus;
import org.knowm.xchange.zaif.dto.trade.ZaifOrderStatusResponse;
import org.knowm.xchange.zaif.dto.trade.ZaifTrades;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.zaif.dto.trade.ZaifTradesResponse;

public class ZaifTradeServiceRaw extends ZaifBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ZaifTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public ZaifTrades[] getZaifOpenOrders() throws IOException {

    try {
      ZaifTradesResponse activeOrders = zaifauth.activeOrders(apiKey, signatureCreator,
              "active_orders", exchange.getNonceFactory());
      if (activeOrders.getError() != null) throw new ZaifException(activeOrders.getError());
//      return activeOrders.getResult();
      List<ZaifTrades> trades = new ArrayList<ZaifTrades>();
      for (String key : activeOrders.getResult().keySet()) {
        ZaifTrades t = activeOrders.getResult().get(key);
        t.setOrderId(key);
        trades.add(t);
      }
      return trades.toArray(new ZaifTrades[trades.size()]);
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifOrderStatus placeZaifMarketOrder(MarketOrder marketOrder) throws IOException {

    String pair = ZaifUtils.toPairString(marketOrder.getCurrencyPair());
    String type = marketOrder.getType().equals(Order.OrderType.BID) ? "bid" : "ask";

    cancelActiveOrder(type);
    BigDecimal price = getMarketPrice(marketOrder);
    if (marketOrder.getCurrencyPair().counter.getSymbol().equals("JPY")) {
      price = price.setScale(0, BigDecimal.ROUND_DOWN);
    }

    try {
      ZaifOrderStatusResponse newOrder = zaifauth.newOrder(apiKey, signatureCreator, "trade",
              exchange.getNonceFactory(), pair, type, marketOrder.getOriginalAmount(), price);
      if (newOrder.getError() != null) throw new ZaifException(newOrder.getError());
      return newOrder.getResult();
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifOrderStatus placeZaifLimitOrder(LimitOrder limitOrder) throws IOException {

    String pair = ZaifUtils.toPairString(limitOrder.getCurrencyPair());
    String type = limitOrder.getType().equals(Order.OrderType.BID) ? "bid" : "ask";
    BigDecimal limitPrice = limitOrder.getLimitPrice();
    if (limitOrder.getCurrencyPair().counter.getSymbol().equals("JPY")) {
      limitPrice = BigDecimal.valueOf(limitPrice.longValue(), 0);
    }

    try {
      ZaifOrderStatusResponse newOrder = zaifauth.newOrder(apiKey, signatureCreator, "trade",
              exchange.getNonceFactory(), pair, type, limitOrder.getOriginalAmount(), limitPrice);
      if (newOrder.getError() != null) throw new ZaifException(newOrder.getError());
      return newOrder.getResult();
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean cancelZaifOrder(String orderId) throws IOException {

    try {
      ZaifOrderStatusResponse cancelOrders = zaifauth.cancelOrders(apiKey, signatureCreator,
              "cancel_order", exchange.getNonceFactory(), orderId);
      if (cancelOrders.getError() != null) throw new ZaifException(cancelOrders.getError());
      return true;
    } catch (ZaifException e) {
//      if (e.getMessage().equals("Order could not be cancelled.")) {
//        return false;
//      } else {
        throw new ExchangeException(e.getMessage());
//      }
    }
  }

  public ZaifTrades[] getZaifTradeHistory(String symbol, Long from, Long to) throws IOException {

    try {
      ZaifTradesResponse trades = zaifauth.pastTrades(apiKey, signatureCreator,
              "trade_history", exchange.getNonceFactory(), from, to, symbol);
//      return trades.getResult();
      if (trades.getError() != null) throw new ZaifException(trades.getError());
      List<ZaifTrades> tr = new ArrayList<ZaifTrades>();
      for (String key : trades.getResult().keySet()) {
        ZaifTrades t = trades.getResult().get(key);
        t.setOrderId(key);
        tr.add(t);
      }
      return tr.toArray(new ZaifTrades[tr.size()]);
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifWallet[] getZaifDepositHistory(String currency, Long from, Long to) throws IOException {

    try {
      ZaifWalletResponse trades = zaifauth.depositHistory(apiKey, signatureCreator,
              "deposit_history", exchange.getNonceFactory(), from, to, currency);
      if (trades.getError() != null) throw new ZaifException(trades.getError());
      List<ZaifWallet> tr = new ArrayList<ZaifWallet>();
      for (String key : trades.getResult().keySet()) {
        ZaifWallet t = trades.getResult().get(key);
        t.setId(key);
        tr.add(t);
      }
      return tr.toArray(new ZaifWallet[tr.size()]);
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifWallet[] getZaifWithdrawHistory(String symbol, Long from, Long to) throws IOException {

    try {
      ZaifWalletResponse trades = zaifauth.depositHistory(apiKey, signatureCreator,
              "withdraw_history", exchange.getNonceFactory(), from, to, symbol);
      if (trades.getError() != null) throw new ZaifException(trades.getError());
      List<ZaifWallet> tr = new ArrayList<ZaifWallet>();
      for (String key : trades.getResult().keySet()) {
        ZaifWallet t = trades.getResult().get(key);
        t.setId(key);
        tr.add(t);
      }
      return tr.toArray(new ZaifWallet[tr.size()]);
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean cancelActiveOrder(String type) throws IOException {
    ZaifTrades[] trades = getZaifOpenOrders();
    for (ZaifTrades trade : trades) {
      if (trade.getYourAction().equals(type)) {
        return cancelZaifOrder(trade.getOrderId());
      }
    }
    return false;
  }

  public BigDecimal getMarketPrice(MarketOrder marketOrder) throws IOException {
    ZaifFullBook zaifDepth = zaif.getDepth(marketOrder.getCurrencyPair().base.getCurrencyCode(),
            marketOrder.getCurrencyPair().counter.getCurrencyCode());
    BigDecimal amount = BigDecimal.ZERO;
    BigDecimal price = BigDecimal.ZERO;
    if (marketOrder.getType().equals(Order.OrderType.BID)) {
      OrderBook orderBook = ZaifAdapters.adaptOrderBook(zaifDepth, marketOrder.getCurrencyPair());
      List<LimitOrder> orders = orderBook.getAsks();
      Collections.sort(orders);
      for (int i = 0; i < orders.size(); i++) {
        LimitOrder order = orders.get(i);
        price = order.getLimitPrice();
        amount = amount.add(order.getLimitPrice().multiply(order.getOriginalAmount()));
        if (amount.compareTo(price.multiply(marketOrder.getOriginalAmount())) > 0) {
          price = price.add(BigDecimal.valueOf(5));
          break;
        }
      }
    } else {
      OrderBook orderBook = ZaifAdapters.adaptOrderBook(zaifDepth, marketOrder.getCurrencyPair());
      List<LimitOrder> orders = orderBook.getBids();
      Collections.sort(orders);
      for (int i = 0; i < orders.size(); i++) {
        LimitOrder order = orders.get(i);
        price = order.getLimitPrice();
        amount = amount.add(order.getLimitPrice().multiply(order.getOriginalAmount()));
        if (amount.compareTo(price.multiply(marketOrder.getOriginalAmount())) > 0) {
          price = price.subtract(BigDecimal.valueOf(5));
          break;
        }
      }
    }
    return price;
  }

}
