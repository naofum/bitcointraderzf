package com.xeiam.xchange.zaif.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.zaif.v1.ZaifAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class ZaifAccountService extends ZaifAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public ZaifAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo("Zaif.jp", BigDecimal.ZERO, ZaifAdapters.adaptWallet(getZaifAccountInfo()));
  }

    /**   
   * Withdrawal suppport   
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException 
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
    //We have to convert XEIAM currencies to Zaif currencies: can be “btc”, “mona”.
    if (address == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }

    getWithdrawFunds(currency.toLowerCase(), amount, address);
    return "success";
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
