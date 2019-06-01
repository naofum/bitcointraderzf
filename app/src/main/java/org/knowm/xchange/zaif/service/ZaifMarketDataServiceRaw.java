package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTicker;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTrade;

public class ZaifMarketDataServiceRaw extends ZaifBaseService {

  protected ZaifMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public ZaifFullBook getZaifFullBook(CurrencyPair currencyPair) throws IOException {
    try {
      return this.zaif.getDepth(
          currencyPair.base.toString().toLowerCase(),
          currencyPair.counter.toString().toLowerCase());
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<ZaifMarket> getAllMarkets() {
    try {
      return this.zaif.getCurrencyPairs();
//      List<ZaifMarket> list = new ArrayList<ZaifMarket>();
//      CurrencyPair pair = new CurrencyPair(Currency.BTC, Currency.JPY);
//      ZaifMarket zaifMarket = new ZaifMarket();
//      zaifMarket.setName(pair);
//      list.add(zaifMarket);
//      return list;
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean checkProductExists(CurrencyPair currencyPair) {

    boolean currencyPairSupported = false;
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && cp.counter
              .getCurrencyCode()
              .equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        currencyPairSupported = true;
        break;
      }
    }

    return currencyPairSupported;
  }

  public ZaifTrade[] getZaifTrades(String pair, long sinceTimestamp) throws IOException {

    try {
      ZaifTrade[] zaifTrades = zaif.getTrades(pair);
      return zaifTrades;
    } catch (ZaifException e) {
      throw new ExchangeException("Zaif returned an error: " + e.getMessage());
    }
  }

  public ZaifTicker getZaifTicker(String pair) throws IOException {

    try {
      ZaifTicker zaifTicker = zaif.getTicker(pair);
      return zaifTicker;
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
