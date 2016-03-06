package com.xeiam.xchange.zaif.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.zaif.v1.dto.ZaifResponse;
import com.xeiam.xchange.zaif.v1.dto.account.ZaifAccountInfo;

/**
 * @author Naofumi Fukue
 */
public class ZaifAccountInfoResponse extends ZaifResponse<ZaifAccountInfo> {

  /**
   * Constructor
   * 
   * @param success
   * @param result
   * @param error
   */
  public ZaifAccountInfoResponse(@JsonProperty("success") String success, @JsonProperty("return") ZaifAccountInfo result,
                                 @JsonProperty("error") String error) {

    super(success, result, error);
  }

}
