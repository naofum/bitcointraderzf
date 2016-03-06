package com.xeiam.xchange.zaif.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.zaif.v1.dto.ZaifResponse;
import com.xeiam.xchange.zaif.v1.dto.account.ZaifWithdrawal;

/**
 * @author Naofumi Fukue
 */
public class ZaifOrderStatusResponse extends ZaifResponse<ZaifOrderStatus> {

  /**
   * Constructor
   *
   * @param success
   * @param result
   * @param error
   */
  public ZaifOrderStatusResponse(@JsonProperty("success") String success, @JsonProperty("return") ZaifOrderStatus result,
                                 @JsonProperty("error") String error) {

    super(success, result, error);
  }

}
