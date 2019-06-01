package org.knowm.xchange.zaif.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.dto.ZaifResponse;

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
