package org.knowm.xchange.zaif.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifTicker {

  private final BigDecimal vwap;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal last;
  private final BigDecimal volume;
  private final float timestamp;

  /**
   * @param vwap
   * @param bid
   * @param ask
   * @param low
   * @param high
   * @param last
   * @param timestamp
   * @param volume
   */
  public ZaifTicker(@JsonProperty("vwap") BigDecimal vwap, @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask,
                    @JsonProperty("low") BigDecimal low, @JsonProperty("high") BigDecimal high, @JsonProperty("last") BigDecimal last,
                    @JsonProperty("timestamp") float timestamp, @JsonProperty("volume") BigDecimal volume) {

    this.vwap = vwap;
    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.high = high;
    this.low = low;
    this.timestamp = timestamp;
  }

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLast_price() {

    return last;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public float getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "ZaifTicker [vwap=" + vwap + ", bid=" + bid + ", ask=" + ask + ", low=" + low + ", high=" + high + ", last=" + last + ", timestamp="
        + timestamp + "]";
  }

}
