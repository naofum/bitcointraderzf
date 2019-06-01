package org.knowm.xchange.zaif.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.v1.dto.ZaifResponse;

/**
 * @author Naofumi Fukue
 */
public class ZaifWithdrawalResponse extends ZaifResponse<ZaifWithdrawal> {

  /**
   * Constructor
   *
   * @param success
   * @param result
   * @param error
   */
  public ZaifWithdrawalResponse(@JsonProperty("success") String success, @JsonProperty("return") ZaifWithdrawal result,
                                @JsonProperty("error") String error) {

    super(success, result, error);
  }

}
