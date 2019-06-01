package org.knowm.xchange.zaif.dto.account;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.zaif.dto.ZaifValue;

/**
 * Zaif withdrawal response mapping class
 * @author Naofumi Fukue
 */
public class ZaifWithdrawal {

    @JsonProperty("funds")
    private ZaifValue funds;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The status
     */
    @JsonProperty("funds")
    public ZaifValue getFunds() {
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