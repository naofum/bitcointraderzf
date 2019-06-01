package org.knowm.xchange.zaif.v1;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.zaif.v1.dto.ZaifException;
import org.knowm.xchange.zaif.v1.dto.account.ZaifAccountInfoResponse;
import org.knowm.xchange.zaif.v1.dto.account.ZaifWalletResponse;
import org.knowm.xchange.zaif.v1.dto.account.ZaifWithdrawalResponse;
import org.knowm.xchange.zaif.v1.dto.trade.ZaifOrderStatusResponse;
import org.knowm.xchange.zaif.v1.dto.trade.ZaifTradesResponse;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("tapi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface ZaifAuthenticated extends Zaif {

  @POST
  ZaifOrderStatusResponse newOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency_pair") String currencyPair, @FormParam("action") String action,
      @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price) throws IOException, ZaifException;

  @POST
  ZaifAccountInfoResponse balances(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException, ZaifException;

  @POST
  ZaifOrderStatusResponse cancelOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") String orderId) throws IOException, ZaifException;

  @POST
  ZaifTradesResponse activeOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws IOException, ZaifException;

  @POST
  ZaifTradesResponse pastTrades(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("since") Long from, @FormParam("end") Long to, @FormParam("currency_pair") String symbol) throws IOException, ZaifException;

  @POST
  ZaifWithdrawalResponse withdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency, @FormParam("address") String address, @FormParam("amount") BigDecimal amount,
      @FormParam("opt_fee") BigDecimal optFee) throws IOException, ZaifException;

  @POST
  ZaifWalletResponse depositHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("since") Long from, @FormParam("end") Long to, @FormParam("currency") String currency) throws IOException, ZaifException;

  @POST
  ZaifWalletResponse withdrawHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("method") String method, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("since") Long from, @FormParam("end") Long to, @FormParam("currency") String currency) throws IOException, ZaifException;

}
