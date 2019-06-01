//$URL$
//$Id$
package de.dev.eth0.bitcointrader.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.github.naofum.bitcointraderzf.R;
/**
 *
 * @author Alexander Muthmann
 */
public final class WalletHistoryActivity extends AbstractBitcoinTraderActivity {

  public static final String INTENT_EXTRA_TYPE = "type";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.wallet_history_activity);
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
