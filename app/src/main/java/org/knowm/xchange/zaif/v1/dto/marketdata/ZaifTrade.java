package org.knowm.xchange.zaif.v1.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifTrade {

  private final BigDecimal price;
  private final BigDecimal amount;
  private final long timestamp;
  private final long tradeId;
  private final String type;

  /**
   * Constructor
   * 
   * @param price
   * @param amount
   * @param timestamp
   * @param tradeId
   */
  public ZaifTrade(@JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount, @JsonProperty("date") long timestamp,
                   @JsonProperty("tid") long tradeId, @JsonProperty("trade_type") String type) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.tradeId = tradeId;
    this.type = type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public long getTradeId() {

    return tradeId;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ZaifTrades [price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", tradeId=");
    builder.append(tradeId);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }
}
