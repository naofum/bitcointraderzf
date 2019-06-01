//$URL$
//$Id$
package de.dev.eth0.bitcointrader.ui.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

import de.dev.eth0.bitcointrader.BitcoinTraderApplication;
import de.dev.eth0.bitcointrader.Constants;
import com.github.naofum.bitcointraderzf.R;

import de.dev.eth0.bitcointrader.service.ExchangeService;
import de.dev.eth0.bitcointrader.ui.BitcoinTraderActivity;
import de.dev.eth0.bitcointrader.ui.StartScreenActivity;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

/**
 * @author Alexander Muthmann
 */
public class AccountInfoWidgetProvider extends AbstractWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    BitcoinTraderApplication application = (BitcoinTraderApplication)context.getApplicationContext();
    ExchangeService exchangeService = application.getExchangeService();

    updateWidgets(context, appWidgetManager, appWidgetIds, exchangeService);
  }

  public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, ExchangeService exchangeService) {
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.account_info_widget_content);
    views.setOnClickPendingIntent(R.id.account_info_widget_content,
            PendingIntent.getActivity(context, 0, new Intent(context, StartScreenActivity.class), 0));
    if (exchangeService != null && exchangeService.getAccountInfo() != null) {
      views.setOnClickPendingIntent(R.id.account_info_widget_content,
              PendingIntent.getActivity(context, 0, new Intent(context, BitcoinTraderActivity.class), 0));
//      AccountInfo accountInfo = MtGoxAdapters.adaptAccountInfo(exchangeService.getAccountInfo());
      AccountInfo accountInfo = exchangeService.getAccountInfo();

      BigMoney btc = BigMoney.of(Constants.BTC, accountInfo.getWallet("BTC").getBalance(Currency.BTC).getAvailable());
      BigMoney usd = BigMoney.of(CurrencyUnit.of("JPY"), accountInfo.getWallet("JPY").getBalance(Currency.JPY).getAvailable());

      if (btc != null && usd != null) {

        views.setTextViewText(R.id.account_info_widget_btc, formatCurrency(btc, Constants.PRECISION_BITCOIN));
        views.setTextViewText(R.id.account_info_widget_balance, formatCurrency(usd, Constants.PRECISION_CURRENCY));
      }
    }
    for (int appWidgetId : appWidgetIds) {
      appWidgetManager.updateAppWidget(appWidgetId, views);
    }
  }
}
