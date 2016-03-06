package com.xeiam.xchange.zaif.v1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.zaif.v1.service.polling.ZaifAccountService;
import com.xeiam.xchange.zaif.v1.service.polling.ZaifMarketDataService;
import com.xeiam.xchange.zaif.v1.service.polling.ZaifTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class ZaifExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new ZaifMarketDataService(this);
    this.pollingAccountService = new ZaifAccountService(this);
    this.pollingTradeService = new ZaifTradeService(this);
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
