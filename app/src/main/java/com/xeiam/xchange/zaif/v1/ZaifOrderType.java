package com.xeiam.xchange.zaif.v1;

public enum ZaifOrderType {

  LIMIT("limit");

  private String value;

  ZaifOrderType(String value) {

    this.value = value;
  }

  public String getValue() {

    return value;
  }

  @Override
  public String toString() {

    return this.getValue();
  }
}
