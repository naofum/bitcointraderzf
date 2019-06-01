package org.knowm.xchange.zaif.v1.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.v1.dto.ZaifValue;

/**
 * @author Naofumi Fukue
 */
public class ZaifAccountInfo {

  private final ZaifValue funds;
  private final ZaifValue deposit;
  private final ZaifValue rights;
  private final BigDecimal tradeCount;
  private final BigDecimal openOrders;
  private final BigDecimal serverTime;

  /**
   * Constructor
   * 
   * @param funds
   * @param deposit
   * @param rights
   * @param tradeCount
   * @param openOrders
   * @param serverTime
   */
  public ZaifAccountInfo(@JsonProperty("funds") ZaifValue funds, @JsonProperty("deposit") ZaifValue deposit,
                         @JsonProperty("rights") ZaifValue rights, @JsonProperty("trade_count") BigDecimal tradeCount,
                         @JsonProperty("open_orders") BigDecimal openOrders, @JsonProperty("server_time") BigDecimal serverTime) {

    this.funds = funds;
    this.deposit = deposit;
    this.rights = rights;
    this.tradeCount = tradeCount;
    this.openOrders = openOrders;
    this.serverTime = serverTime;
  }

  public ZaifValue getFunds() {

    return funds;
  }

  public ZaifValue getDeposit() {

    return deposit;
  }

  public ZaifValue getRights() {

    return rights;
  }

  public BigDecimal getTradeCount() {

    return tradeCount;
  }

  public BigDecimal getOpenOrders() {

    return openOrders;
  }

  public BigDecimal getServerTime() {

    return serverTime;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("ZaifAccountInfo [funds=");
    builder.append(funds);
    builder.append(", deposit=");
    builder.append(deposit);
    builder.append(", rights=");
    builder.append(rights);
    builder.append(", tradeCount=");
    builder.append(tradeCount);
    builder.append(", openOrders=");
    builder.append(openOrders);
    builder.append(", serverTime=");
    builder.append(serverTime);
    builder.append("]");
    return builder.toString();
  }
}
