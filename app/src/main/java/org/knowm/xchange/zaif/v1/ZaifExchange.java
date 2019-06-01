package org.knowm.xchange.zaif.v1;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.zaif.v1.service.ZaifAccountService;
import org.knowm.xchange.zaif.v1.service.ZaifMarketDataService;
import org.knowm.xchange.zaif.v1.service.ZaifTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class ZaifExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new ZaifMarketDataService(this);
    this.accountService = new ZaifAccountService(this);
    this.tradeService = new ZaifTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.zaif.com/");
    exchangeSpecification.setHost("api.zaif.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Zaif");
    exchangeSpecification.setExchangeDescription("Zaif is a bitcoin, mona exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
