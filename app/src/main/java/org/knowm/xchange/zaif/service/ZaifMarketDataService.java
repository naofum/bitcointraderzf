package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.zaif.ZaifAdapters;
import org.knowm.xchange.zaif.ZaifUtils;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTrade;

public class ZaifMarketDataService extends ZaifMarketDataServiceRaw implements MarketDataService {

  public ZaifMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return ZaifAdapters.adaptOrderBook(this.getZaifFullBook(currencyPair), currencyPair);
  }

  public ExchangeMetaData getMetadata() throws IOException {
    List<ZaifMarket> markets = this.getAllMarkets();
    return ZaifAdapters.adaptMetadata(markets);
  }

  /**
   * @param currencyPair The CurrencyPair for which to query trades.
   * @param args One argument may be supplied which is the timestamp after which trades should be collected. Trades before this time are not reported.
   *        The argument may be of type java.util.Date or Number (milliseconds since Jan 1, 1970)
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    long lastTradeTime = 0;
    if (args != null && args.length == 1) {
      // parameter 1, if present, is the last trade timestamp
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        lastTradeTime = arg.longValue() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      } else if (args[0] instanceof Date) {
        Date arg = (Date) args[0];
        lastTradeTime = arg.getTime() / 1000; // divide by 1000 to convert to unix timestamp (seconds)
      } else {
        throw new IllegalArgumentException(
                "Extra argument #1, the last trade time, must be a Date or Long (millisecond timestamp) (was " + args[0].getClass() + ")");
      }
    }
    ZaifTrade[] trades = getZaifTrades(ZaifUtils.toPairString(currencyPair), lastTradeTime);

    return ZaifAdapters.adaptTrades(trades, currencyPair);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return ZaifAdapters.adaptTicker(getZaifTicker(ZaifUtils.toPairString(currencyPair)), currencyPair);
  }

}
