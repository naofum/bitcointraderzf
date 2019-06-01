package org.knowm.xchange.zaif.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.zaif.v1.Zaif;
import org.knowm.xchange.zaif.v1.ZaifAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class ZaifBaseService extends BaseExchangeService implements BaseService {

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
  public ZaifBaseService(Exchange exchange) {

    super(exchange);

    this.zaif = RestProxyFactory.createProxy(Zaif.class, exchange.getExchangeSpecification().getSslUri());
    this.zaifauth = RestProxyFactory.createProxy(ZaifAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = ZaifHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new ZaifPayloadDigest();
  }

}
