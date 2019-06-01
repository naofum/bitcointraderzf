package org.knowm.xchange.zaif.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.dto.ZaifResponse;

import java.util.Map;

/**
 * @author Naofumi Fukue
 */
public class ZaifTradesResponse extends ZaifResponse<Map<String, ZaifTrades>> {

  /**
   * Constructor
   *
   * @param success
   * @param result
   * @param error
   */
  public ZaifTradesResponse(@JsonProperty("success") String success, @JsonProperty("return") Map<String, ZaifTrades> result,
                            @JsonProperty("error") String error) {

    super(success, result, error);
  }

}
