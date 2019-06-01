package org.knowm.xchange.zaif.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.zaif.Zaif;
import org.knowm.xchange.zaif.ZaifAuthenticated;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class ZaifBaseService extends BaseExchangeService implements BaseService {

  protected final Zaif zaif;
  protected final String apiKey;
  protected final ZaifAuthenticated zaifauth;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected ZaifBaseService(Exchange exchange) {
    super(exchange);
    this.zaif =
        RestProxyFactory.createProxy(
            Zaif.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.zaifauth = RestProxyFactory.createProxy(ZaifAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = ZaifHmacPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.payloadCreator = new ZaifPayloadDigest();
  }
}
