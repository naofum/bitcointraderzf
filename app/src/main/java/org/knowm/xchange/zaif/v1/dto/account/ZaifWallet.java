package org.knowm.xchange.zaif.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ZaifWallet {

  private String address;
  private BigDecimal amount;
  private BigDecimal timestamp;
  private String txid;
  private BigDecimal fee;
  private String id;

  /**
   * Constructor
   *
   * @param address
   * @param amount
   * @param timestamp
   * @param txid
   * @param fee
   * @param id
   */
  public ZaifWallet(@JsonProperty("address") String address, @JsonProperty("amount") BigDecimal amount,
                    @JsonProperty("timestamp") BigDecimal timestamp, @JsonProperty("txid") String txid,
                    @JsonProperty("fee") BigDecimal fee, @JsonProperty("id") String id) {

    this.address = address;
    this.amount = amount;
    this.timestamp = timestamp;
    this.txid = txid;
    this.fee = fee;
    this.id = id;
  }

  public String getAddress() {

    return address;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getTimestamp() {

    return timestamp;
  }

  public String getTxid() {

    return txid;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  @Override
  public String toString() {

    final StringBuilder builder = new StringBuilder();
    builder.append("ZaifWallet [address=");
    builder.append(address);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", timestamp=");
    builder.append(timestamp);
    builder.append(", txid=");
    builder.append(txid);
    builder.append(", fee=");
    builder.append(fee);
    builder.append(", id=");
    builder.append(id);
    builder.append("]");
    return builder.toString();
  }
}
