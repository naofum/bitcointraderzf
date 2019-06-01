package org.knowm.xchange.zaif.service;

import java.math.BigInteger;

import javax.crypto.Mac;

import org.knowm.xchange.service.BaseParamsDigest;

import si.mazi.rescu.RestInvocation;

public class ZaifHmacPostBodyDigest extends BaseParamsDigest {

  /**
   * Constructor
   * 
   * @param secretKey
   * @throws IllegalArgumentException if key is invalid (cannot be base-64-decoded or the decoded key is invalid).
   */
  private ZaifHmacPostBodyDigest(String secretKey) {

    super(secretKey, HMAC_SHA_512);
  }

  public static ZaifHmacPostBodyDigest createInstance(String secretKey) {

    return secretKey == null ? null : new ZaifHmacPostBodyDigest(secretKey);
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    Mac mac = getMac();
    mac.update(postBody.getBytes());

    return String.format("%096x", new BigInteger(1, mac.doFinal()));
  }
}
