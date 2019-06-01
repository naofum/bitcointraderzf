package org.knowm.xchange.zaif;

import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * A central place for shared Zaif properties
 */
public final class ZaifUtils {

  /**
   * private Constructor
   */
  private ZaifUtils() {

  }

  public static String toPairString(CurrencyPair currencyPair) {

    return currencyPair.base.getCurrencyCode().toLowerCase() + "_" + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

    /**
   * Can be  “bitcoin”, “mona” or “tether” or “wire”
   * @param currency
   * @return 
   */
  public static String convertToZaifWithdrawalType(String currency) {
      
      if(currency.toUpperCase().equals("BTC"))
          return "bitcoin";
      if(currency.toUpperCase().equals("MONA"))
          return "mona";
      if(currency.toUpperCase().equals("JPY") )
          return "wire";
      if(currency.toUpperCase().equals("TETHER"))
          return "tether";
      
          throw new ZaifException("Cannot determine withdrawal type.");
  }
}
