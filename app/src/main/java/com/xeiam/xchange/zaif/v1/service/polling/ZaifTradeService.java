package com.xeiam.xchange.zaif.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.zaif.v1.ZaifAdapters;
import com.xeiam.xchange.zaif.v1.dto.account.ZaifWallet;
import com.xeiam.xchange.zaif.v1.dto.trade.ZaifOrderStatus;
import com.xeiam.xchange.zaif.v1.dto.trade.ZaifTrades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.utils.DateUtils;

public class ZaifTradeService extends ZaifTradeServiceRaw implements PollingTradeService {

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
    public UserTrades getTradeHistory(Object... arguments) throws
        ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotYetImplementedForExchangeException();
    }

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

//    @Override
//    public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException,
//            IOException {
//        throw new NotYetImplementedForExchangeException();
//    }
    public ZaifWallet[] getDepositHistory(TradeHistoryParams params) throws IOException {

        final String symbol;
        if (params instanceof TradeHistoryParamCurrencyPair && ((TradeHistoryParamCurrencyPair) params).getCurrencyPair() != null) {
            symbol = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().counterSymbol.toLowerCase();
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
            symbol = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair().counterSymbol.toLowerCase();
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
