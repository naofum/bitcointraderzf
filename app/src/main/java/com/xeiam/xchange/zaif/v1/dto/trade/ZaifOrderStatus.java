package com.xeiam.xchange.zaif.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.zaif.v1.dto.ZaifValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Zaif withdrawal response mapping class
 * @author Naofumi Fukue
 */
public class ZaifOrderStatus {

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("funds")
    private ZaifValue funds;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The order id
     */
    @JsonProperty("order_id")
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId The order id
     */
    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return The status
     */
    @JsonProperty("funds")
    public ZaifValue getFundss() {
        return funds;
    }

    /**
     *
     * @param funds The status
     */
    @JsonProperty("funds")
    public void setFunds(ZaifValue funds) {
        this.funds = funds;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}