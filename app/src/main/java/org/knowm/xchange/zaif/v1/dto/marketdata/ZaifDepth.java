package org.knowm.xchange.zaif.v1.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifDepth {

  private final List<List<BigDecimal>> asks;
  private final List<List<BigDecimal>> bids;

  /**
   * Constructor
   * 
   * @param asks
   * @param bids
   */
  public ZaifDepth(@JsonProperty("asks") List<List<BigDecimal>> asks, @JsonProperty("bids") List<List<BigDecimal>> bids) {

    this.asks = asks;
    this.bids = bids;
  }

  public List<List<BigDecimal>> getAsks() {

    return asks;
  }

  public List<List<BigDecimal>> getBids() {

    return bids;
  }

  @Override
  public String toString() {

    return "ZaifDepth [asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
