package org.knowm.xchange.zaif.v1.service;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class ZaifPayloadDigest implements ParamsDigest {

  @Override
  public synchronized String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    return postBody;
  }
}