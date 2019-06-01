package org.knowm.xchange.zaif.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZaifTrades {

  private BigDecimal price;
  private BigDecimal amount;
  private BigDecimal timestamp;
  private String yourAction;
  private String type;
  private BigDecimal bonus;
  private String orderId;
  private BigDecimal fee;
  private String symbol;

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param timestamp
   * @param yourAction
   * @param type
   * @param bonus
   * @param orderId
   * @param fee
   * @param symbol
   */
  public ZaifTrades(@JsonProperty("price") final BigDecimal price, @JsonProperty("amount") final BigDecimal amount,
                    @JsonProperty("timestamp") final BigDecimal timestamp, @JsonProperty("your_action") final String yourAction, @JsonProperty("action") final String type,
                    @JsonProperty("bonus") final BigDecimal bonus, @JsonProperty("order_id") final String orderId,
                    @JsonProperty("fee") final BigDecimal fee, @JsonProperty("currency_pair") String symbol) {

    this.price = price;
    this.amount = amount;
    this.timestamp = timestamp;
    this.yourAction = yourAction;
    this.type = type;
    this.bonus = bonus;
    this.orderId = orderId;
    this.fee = fee;
    this.symbol = symbol;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public String getType() {

    return type;
  }

  public String getYourAction() {

    return yourAction;
  }

  public String getOrderId() {

    return orderId;
  }

  public void setOrderId(String orderId) {

    this.orderId = orderId;
  }

  public BigDecimal getBonus() {

    return bonus;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getSymbol() {

    return symbol;
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();
    builder.append("ZaifTrades [price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", yourAction=");
    builder.append(yourAction);
    builder.append(", type=");
    builder.append(type);
    builder.append(", bonus=");
    builder.append(bonus);
    builder.append(", orderId=");
    builder.append(orderId);
    builder.append(", fee=");
    builder.append(fee);
    builder.append(", symbol=");
    builder.append(symbol);
    builder.append("]");
    return builder.toString();
  }
}
