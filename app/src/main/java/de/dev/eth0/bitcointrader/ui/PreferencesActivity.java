//$URL$
//$Id$
package de.dev.eth0.bitcointrader.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import de.dev.eth0.bitcointrader.Constants;
import com.github.naofum.bitcointraderzf.R;
/**
 * @author Alexander Muthmann
 */
public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
  private static final String KEY_DELETE_ACCOUNT = "delete_account";

  private AppCompatDelegate mDelegate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    addPreferencesFromResource(R.xml.preferences);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

    SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
    sp.registerOnSharedPreferenceChangeListener(this);

    // if api key has not been set yet, add the summary, otherwise the current value
    String mtgoxApiKey = sp.getString(Constants.PREFS_KEY_ZAIF_APIKEY, null);
    if (TextUtils.isEmpty(mtgoxApiKey)) {
      findPreference(Constants.PREFS_KEY_ZAIF_APIKEY).setSummary(R.string.preferences_mtgox_api_key_summary);
    }
    else {
      findPreference(Constants.PREFS_KEY_ZAIF_APIKEY).setSummary(mtgoxApiKey);
    }

    ListPreference listPreference = (ListPreference)findPreference(Constants.PREFS_KEY_GENERAL_UPDATE);
    if (listPreference.getValue() == null) {
      listPreference.setValueIndex(0);
    }
    listPreference.setSummary(listPreference.getEntry());
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

  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    // Update summary for api key changed
    if (TextUtils.equals(key, Constants.PREFS_KEY_ZAIF_APIKEY)) {
      Preference pref = findPreference(key);
      EditTextPreference etp = (EditTextPreference)pref;
      if (TextUtils.isEmpty(etp.getText())) {
        pref.setSummary(R.string.preferences_mtgox_api_key_summary);
      }
      else {
        pref.setSummary(etp.getText());
      }
    }
    if (TextUtils.equals(key, Constants.PREFS_KEY_GENERAL_UPDATE)) {
      Preference pref = findPreference(key);
      ListPreference lp = (ListPreference)pref;
      pref.setSummary(lp.getEntry());
    }
  }

  @Override
  public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
    String key = preference.getKey();
    if (KEY_DELETE_ACCOUNT.equals(key)) {
      Editor editor = preference.getEditor();
      editor.putString(Constants.PREFS_KEY_ZAIF_APIKEY, null);
      editor.putString(Constants.PREFS_KEY_ZAIF_SECRETKEY, null);
      editor.putBoolean(Constants.PREFS_KEY_DEMO, false);
      editor.commit();
      Toast.makeText(this, R.string.preferences_delete_account_title, Toast.LENGTH_LONG).show();
      startActivity(new Intent(this, StartScreenActivity.class));
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
