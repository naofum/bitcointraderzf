package org.knowm.xchange.zaif.v1.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.zaif.v1.dto.ZaifException;
import org.knowm.xchange.zaif.v1.dto.marketdata.ZaifDepth;
import org.knowm.xchange.zaif.v1.dto.marketdata.ZaifTicker;
import org.knowm.xchange.zaif.v1.dto.marketdata.ZaifTrade;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * <p>
 * Implementation of the market data service for Zaif
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class ZaifMarketDataServiceRaw extends ZaifBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ZaifMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public ZaifTicker getZaifTicker(String pair) throws IOException {

    try {
      ZaifTicker zaifTicker = zaif.getTicker(pair);
      return zaifTicker;
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifDepth getZaifOrderBook(String pair, Integer limitBids, Integer limitAsks) throws IOException {

    try {
      ZaifDepth zaifDepth;
      zaifDepth = zaif.getBook(pair);
      return zaifDepth;
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifTrade[] getZaifTrades(String pair, long sinceTimestamp) throws IOException {

    try {
      ZaifTrade[] zaifTrades = zaif.getTrades(pair);
      return zaifTrades;
    } catch (ZaifException e) {
      throw new ExchangeException("Zaif returned an error: " + e.getMessage());
    }
  }

}
