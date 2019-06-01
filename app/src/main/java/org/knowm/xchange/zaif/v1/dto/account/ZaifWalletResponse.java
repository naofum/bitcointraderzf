package org.knowm.xchange.zaif.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.knowm.xchange.zaif.v1.dto.ZaifResponse;

import java.util.Map;

/**
 * @author Naofumi Fukue
 */
public class ZaifWalletResponse extends ZaifResponse<Map<String, ZaifWallet>> {

  /**
   * Constructor
   *
   * @param success
   * @param result
   * @param error
   */
  public ZaifWalletResponse(@JsonProperty("success") String success, @JsonProperty("return") Map<String, ZaifWallet> result,
                            @JsonProperty("error") String error) {

    super(success, result, error);
  }

    /**
     * @author Naofumi Fukue
     */
    public static class ZaifWalletResponseBak extends ZaifResponse<Map<String, ZaifWallet>> {

      /**
       * Constructor
       *
       * @param success
       * @param result
       * @param error
       */
      public ZaifWalletResponseBak(@JsonProperty("success") String success, @JsonProperty("return") Map<String, ZaifWallet> result,
                                   @JsonProperty("error") String error) {

        super(success, result, error);
      }

    }
}
