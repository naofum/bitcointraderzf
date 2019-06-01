//$URL$
//$Id$
package de.dev.eth0.bitcointrader.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import com.github.naofum.bitcointraderzf.R;

/**
 * @author Alexander Muthmann
 */
public class TrailingStopLossActivity extends AbstractBitcoinTraderActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    setContentView(R.layout.trailing_stop_loss_activity);
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
