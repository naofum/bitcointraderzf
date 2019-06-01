package org.knowm.xchange.zaif.v1.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.zaif.v1.dto.ZaifException;
import org.knowm.xchange.zaif.v1.dto.account.ZaifAccountInfo;
import org.knowm.xchange.zaif.v1.dto.account.ZaifAccountInfoResponse;
import org.knowm.xchange.zaif.v1.dto.account.ZaifWithdrawal;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.zaif.v1.dto.account.ZaifWithdrawalResponse;

public class ZaifAccountServiceRaw extends ZaifBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ZaifAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public ZaifAccountInfo getZaifAccountInfo() throws IOException {

    try {
      ZaifAccountInfoResponse balances = zaifauth.balances(apiKey, signatureCreator,
          "get_info", exchange.getNonceFactory());
      if (balances.getError() != null) throw new ZaifException(balances.getError());
      return balances.getResult();
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public ZaifWithdrawal getWithdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
           
    try {
      ZaifWithdrawalResponse withdraw = zaifauth.withdraw(apiKey, signatureCreator,
              "withdraw", exchange.getNonceFactory(), currency, address, amount, BigDecimal.ZERO);
      if (withdraw.getError() != null) throw new ZaifException(withdraw.getError());
        return withdraw.getResult();
    } catch (ZaifException e) {
        throw new ExchangeException(e.getMessage());
    }
  }
}
