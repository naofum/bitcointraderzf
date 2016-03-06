package com.xeiam.xchange.zaif.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class ZaifException extends RuntimeException {

  @JsonProperty("error")
  private String error;

  public ZaifException(@JsonProperty("error") String error) {

    super();
    this.error = error;
  }

  public String getError() {

    return error;
  }

}