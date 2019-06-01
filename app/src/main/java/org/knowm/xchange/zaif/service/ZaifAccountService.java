package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.knowm.xchange.zaif.ZaifAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;

public class ZaifAccountService extends ZaifAccountServiceRaw implements AccountService {

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
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    //We have to convert XEIAM currencies to Zaif currencies: can be “btc”, “mona”.
    if (address == null) {
      throw new IllegalArgumentException("Amount cannot be null");
    }

    getWithdrawFunds(currency.getCurrencyCode().toLowerCase(), amount, address);
    return "success";
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    //TODO
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    //TODO
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    //TODO
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
