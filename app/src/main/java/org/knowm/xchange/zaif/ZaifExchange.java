package org.knowm.xchange.zaif;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.knowm.xchange.zaif.service.ZaifAccountService;
import org.knowm.xchange.zaif.service.ZaifMarketDataService;
import org.knowm.xchange.zaif.service.ZaifTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class ZaifExchange extends BaseExchange implements Exchange {

//  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new ZaifMarketDataService(this);
    this.accountService = new ZaifAccountService(this);
    this.tradeService = new ZaifTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return this.nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.zaif.jp/");
    exchangeSpecification.setHost("api.zaif.jp");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Zaif");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchangeMetaData = ((ZaifMarketDataService) marketDataService).getMetadata();
  }
}
