package org.knowm.xchange.zaif;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTicker;
import org.knowm.xchange.zaif.dto.marketdata.ZaifTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Zaif {

  @GET
  @Path("api/1/depth/{baseCurrency}_{targetCurrency}")
  ZaifFullBook getDepth(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws ZaifException, IOException;

  @GET
  @Path("api/1/currency_pairs/all")
  List<ZaifMarket> getCurrencyPairs();

  @GET
  @Path("api/1/trades/{currency_pair}")
  ZaifTrade[] getTrades(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

  @GET
  @Path("api/1/ticker/{currency_pair}")
  ZaifTicker getTicker(@PathParam("currency_pair") String symbol) throws IOException, ZaifException;

}
