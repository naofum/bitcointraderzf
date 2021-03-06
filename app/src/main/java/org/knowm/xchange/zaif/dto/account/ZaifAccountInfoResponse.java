package org.knowm.xchange.zaif.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.dto.ZaifResponse;

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
