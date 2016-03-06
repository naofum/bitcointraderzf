package com.xeiam.xchange.zaif.v1;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.zaif.v1.dto.ZaifException;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifDepth;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifTicker;
import com.xeiam.xchange.zaif.v1.dto.marketdata.ZaifTrade;

@Path("api/1")
@Produces(MediaType.APPLICATION_JSON)
public interface Zaif {

  @GET
  @Path("last_price/{currency_pair}")
  ZaifTicker getLastPrice(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

  @GET
  @Path("ticker/{currency_pair}")
  ZaifTicker getTicker(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

  @GET
  @Path("depth/{currency_pair}")
  ZaifDepth getBook(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

  @GET
  @Path("trades/{currency_pair}")
  ZaifTrade[] getTrades(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

}
