//$URL$
//$Id$
package de.dev.eth0.bitcointrader.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import de.dev.eth0.bitcointrader.BitcoinTraderApplication;
import de.dev.eth0.bitcointrader.Constants;
import com.github.naofum.bitcointraderzf.R;
import de.schildbach.wallet.integration.android.BitcoinIntegration;

/**
 * @author Alexander Muthmann
 */
public class AboutActivity extends PreferenceActivity {

  private static final String KEY_ABOUT_VERSION = "about_version";
  private static final String KEY_ABOUT_AUTHOR = "about_author";
  private static final String KEY_ABOUT_AUTHOR_TWITTER = "about_author_twitter";
  private static final String KEY_ABOUT_AUTHOR_LICENSE = "about_license";
  private static final String KEY_ABOUT_CREDITS_BITCOINWALLET = "about_credits_bitcoinwallet";
  private static final String KEY_ABOUT_CREDITS_XCHANGE = "about_credits_xchange";
  private static final String KEY_ABOUT_CREDITS_ZXING = "about_credits_zxing";
  private static final String KEY_ABOUT_CREDITS_GRAPHVIEW = "about_credits_graphview";
  private static final String KEY_ABOUT_DONATE = "about_donate";

  private AppCompatDelegate mDelegate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.about);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    findPreference(KEY_ABOUT_VERSION).setSummary(((BitcoinTraderApplication)getApplication()).applicationVersionName());
    findPreference(KEY_ABOUT_CREDITS_BITCOINWALLET).setSummary(Constants.CREDITS_BITCOINWALLET_URL);
    findPreference(KEY_ABOUT_CREDITS_XCHANGE).setSummary(Constants.CREDITS_XCHANGE_URL);
    findPreference(KEY_ABOUT_CREDITS_ZXING).setSummary(Constants.CREDITS_ZXING_URL);
    findPreference(KEY_ABOUT_CREDITS_GRAPHVIEW).setSummary(Constants.CREDITS_GRAPHVIEW_URL);
//    findPreference(KEY_ABOUT_DONATE).setSummary(Constants.DONATION_ADDRESS);

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

  @Override
  public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
    String key = preference.getKey();
    if (KEY_ABOUT_AUTHOR_TWITTER.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.AUTHOR_TWITTER_URL)));
      finish();
    }
    else if (KEY_ABOUT_AUTHOR.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.AUTHOR_URL)));
      finish();
    }
    else if (KEY_ABOUT_AUTHOR_LICENSE.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.AUTHOR_SOURCE_URL)));
      finish();
    }
    else if (KEY_ABOUT_CREDITS_BITCOINWALLET.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_BITCOINWALLET_URL)));
      finish();
    }
    else if (KEY_ABOUT_CREDITS_XCHANGE.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_XCHANGE_URL)));
      finish();
    }
    else if (KEY_ABOUT_CREDITS_GRAPHVIEW.equals(key)) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.CREDITS_GRAPHVIEW_URL)));
      finish();
    }
    else if (KEY_ABOUT_DONATE.equals(key)) {
      BitcoinIntegration.request(this, Constants.DONATION_ADDRESS);
      finish();
    }
    return false;
  }

  public ActionBar getSupportActionBar() {
    return getDelegate().getSupportActionBar();
  }

  public void invalidateOptionsMenu() {
    getDelegate().invalidateOptionsMenu();
  }

  private AppCompatDelegate getDelegate() {
    if (mDelegate == null) {
      mDelegate = AppCompatDelegate.create(this, null);
    }
    return mDelegate;
  }
}
