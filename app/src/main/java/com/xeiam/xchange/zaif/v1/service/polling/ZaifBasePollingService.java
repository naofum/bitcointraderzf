package com.xeiam.xchange.zaif.v1.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.zaif.v1.Zaif;
import com.xeiam.xchange.zaif.v1.ZaifAuthenticated;
import com.xeiam.xchange.zaif.v1.service.ZaifHmacPostBodyDigest;
import com.xeiam.xchange.zaif.v1.service.ZaifPayloadDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class ZaifBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final Zaif zaif;
  protected final ZaifAuthenticated zaifauth;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public ZaifBasePollingService(Exchange exchange) {

    super(exchange);

    this.zaif = RestProxyFactory.createProxy(Zaif.class, exchange.getExchangeSpecification().getSslUri());
    this.zaifauth = RestProxyFactory.createProxy(ZaifAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = ZaifHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new ZaifPayloadDigest();
  }

}
