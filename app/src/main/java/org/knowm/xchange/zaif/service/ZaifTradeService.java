package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.zaif.ZaifAdapters;
import org.knowm.xchange.zaif.dto.account.ZaifWallet;
import org.knowm.xchange.zaif.dto.trade.ZaifOrderStatus;
import org.knowm.xchange.zaif.dto.trade.ZaifTrades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.DateUtils;

public class ZaifTradeService extends ZaifTradeServiceRaw implements TradeService {

    private static final OpenOrders noOpenOrders = new OpenOrders(new ArrayList<LimitOrder>());

    public ZaifTradeService(Exchange exchange) {

        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {

        ZaifTrades[] activeOrders = getZaifOpenOrders();

        if (activeOrders.length <= 0) {
            return noOpenOrders;
        } else {
            return ZaifAdapters.adaptOrders(activeOrders);
        }
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

        ZaifOrderStatus newOrder = placeZaifMarketOrder(marketOrder);

        return newOrder.getOrderId();
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

        ZaifOrderStatus newOrder = placeZaifLimitOrder(limitOrder);

        return newOrder.getOrderId();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {

        return cancelZaifOrder(orderId);
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) throws IOException {
        throw new NotAvailableFromExchangeException();
    }

//    @Override
//    public UserTrades getTradeHistory(Object... arguments) throws
//        ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
//        throw new NotYetImplementedForExchangeException();
//    }

    /**
     * @param params Implementation of {@link TradeHistoryParamCurrencyPair} is mandatory. Can optionally implement {@link TradeHistoryParamPaging} and
     *        {@link TradeHistoryParamsTimeSpan#getStartTime()}. All other TradeHistoryParams types will be ignored.
     */
    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

        final String symbol;
        if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
            symbol = ZaifAdapters.adaptCurrencyPair(((TradeHistoryParamCurrencyPair) params).getCurrencyPair());
        } else {
            // Exchange will return the errors below if CurrencyPair is not provided.
            // field not on method: "Key currency_pair was not present."
            // field supplied but blank: "Key currency_pair may not be the empty string"
            throw new ExchangeException("CurrencyPair must be supplied");
        }

        Long from = null;
        Long to = null;
        if (params instanceof TradeHistoryParamsTimeSpan) {
            TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
            from = DateUtils.toMillisNullSafe(p.getStartTime());
            to = DateUtils.toMillisNullSafe(p.getEndTime());
        }

        final ZaifTrades[] trades = getZaifTradeHistory(symbol, from, to);
        return ZaifAdapters.adaptTradeHistory(trades, symbol);
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {

        return new DefaultTradeHistoryParamsTimeSpan();
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        throw new NotAvailableFromExchangeException();
    }

    //    @Override
//    public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
//            IOException {
//        throw new NotYetImplementedForExchangeException();
//    }
    public ZaifWallet[] getDepositHistory(TradeHistoryParams params) throws IOException {

        final String symbol;
        if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
            symbol = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().counter.getSymbol().toLowerCase();
        } else {
            // Exchange will return the errors below if CurrencyPair is not provided.
            // field not on method: "Key currency_pair was not present."
            // field supplied but blank: "Key currency_pair may not be the empty string"
            throw new ExchangeException("CurrencyPair must be supplied");
        }

        Long from = null;
        Long to = null;
        if (params instanceof TradeHistoryParamsTimeSpan) {
            TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
            from = DateUtils.toMillisNullSafe(p.getStartTime());
            to = DateUtils.toMillisNullSafe(p.getEndTime());
        }

        ZaifWallet[] wallets = getZaifDepositHistory(symbol, from, to);
        return wallets;
    }

    public ZaifWallet[] getWithdrawHistory(TradeHistoryParams params) throws IOException {

        final String symbol;
        if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
            symbol = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().counter.getSymbol().toLowerCase();
        } else {
            // Exchange will return the errors below if CurrencyPair is not provided.
            // field not on method: "Key currency_pair was not present."
            // field supplied but blank: "Key currency_pair may not be the empty string"
            throw new ExchangeException("CurrencyPair must be supplied");
        }

        Long from = null;
        Long to = null;
        if (params instanceof TradeHistoryParamsTimeSpan) {
            TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
            from = DateUtils.toMillisNullSafe(p.getStartTime());
            to = DateUtils.toMillisNullSafe(p.getEndTime());
        }

        ZaifWallet[] wallets = getZaifWithdrawHistory(symbol, from, to);
        return wallets;
    }

}
