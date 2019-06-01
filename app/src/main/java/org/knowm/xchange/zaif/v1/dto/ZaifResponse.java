package org.knowm.xchange.zaif.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Naofumi Fukue
 */
public class ZaifResponse<V> {

  private final String success;
  private final V result;
  private final String error;

  /**
   * Constructor
   * 
   * @param success
   * @param result
   * @param error
   */
  public ZaifResponse(@JsonProperty("success") String success, @JsonProperty("return") V result, @JsonProperty("error") String error) {

    this.success = success;
    this.result = result;
    this.error = error;

  }

  public String getSuccess() {

    return success;
  }

  public V getResult() {

    return result;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("ZaifResponse{success=%s, result=%s, error=%s}", success, result, error);
  }

}
