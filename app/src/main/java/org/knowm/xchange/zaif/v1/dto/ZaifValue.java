package org.knowm.xchange.zaif.v1.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a Value from ZaifFunds
 */
public final class ZaifValue {

  private final BigDecimal jpy;
  private final BigDecimal btc;
  private final BigDecimal mona;

  /**
   * Constructor
   * 
   * @param jpy
   * @param btc
   * @param mona
   */
  public ZaifValue(@JsonProperty("jpy") BigDecimal jpy, @JsonProperty("btc") BigDecimal btc,
                   @JsonProperty("mona") BigDecimal mona) {

    this.jpy = jpy;
    this.btc = btc;
    this.mona = mona;
  }

  public BigDecimal getJpy() {

    return jpy;
  }

  public BigDecimal getBtc() {

    return btc;
  }

  public BigDecimal getMona() {

    return mona;
  }

  @Override
  public String toString() {

    return "ZaifValue [jpy=" + jpy + ", btc=" + btc + ", mona=" + mona + "]";
  }

}
