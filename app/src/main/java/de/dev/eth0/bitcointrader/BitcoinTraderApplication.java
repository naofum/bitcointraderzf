//$URL$
//$Id$
package de.dev.eth0.bitcointrader;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import de.dev.eth0.bitcointrader.service.ExchangeService;
import de.dev.eth0.bitcointrader.util.CrashReporter;

/**
 * @author Alexander Muthmann
 */
public class BitcoinTraderApplication extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {

  private PendingIntent updateServiceActionIntent;
  private Intent exchangeServiceIntent;
  private static final String TAG = BitcoinTraderApplication.class.getSimpleName();
  private boolean serviceBound = false;
  private ExchangeService exchangeService;
  private Cache cache;
  private ServiceConnection serviceConnection = new ServiceConnection() {
    public void onServiceConnected(ComponentName name, IBinder binder) {
      exchangeService = ((ExchangeService.LocalBinder) binder).getService();
      createDataFromPreferences(PreferenceManager.getDefaultSharedPreferences(BitcoinTraderApplication.this));
      BitcoinTraderApplication.this.sendBroadcast(new Intent(Constants.UPDATE_SERVICE_ACTION));
    }

    public void onServiceDisconnected(ComponentName name) {
      exchangeService = null;
    }
  };

//  @Override
//  protected void attachBaseContext(Context base) {
//    super.attachBaseContext(base);
//    MultiDex.install(this);
//  }

  @Override
  public void onCreate() {
    CrashReporter.init(getCacheDir());
    Log.d(TAG, ".onCreate()");
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    prefs.registerOnSharedPreferenceChangeListener(this);
    cache = new Cache();
    updateServiceActionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constants.UPDATE_SERVICE_ACTION), 0);
    exchangeServiceIntent = new Intent(this, ExchangeService.class);
    super.onCreate();
  }

  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    Log.d(TAG, ".onSharedPreferenceChanged(" + key + ")");
    if (Constants.PREFS_KEY_GENERAL_UPDATE.equals(key)) {
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      alarmManager.cancel(updateServiceActionIntent);
      createAutoUpdater(sharedPreferences);
    }
  }

  public String applicationVersionName() {
    try {
      return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
    } catch (NameNotFoundException x) {
      return "unknown";
    }
  }

  public int applicationVersionCode() {
    try {
      return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
    } catch (NameNotFoundException x) {
      return 0;
    }
  }

  private void createDataFromPreferences(SharedPreferences prefs) {
    createAutoUpdater(prefs);
  }

  private void createAutoUpdater(SharedPreferences prefs) {
    // set auto update if enabled
    String autoUpdateInt = prefs.getString(Constants.PREFS_KEY_GENERAL_UPDATE, "0");
    int autoUpdateInterval = Integer.parseInt(autoUpdateInt);
    if (autoUpdateInterval > 0) {
      AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, autoUpdateInterval * 60 * 1000, updateServiceActionIntent);
    }
  }

  public Cache getCache() {
    return cache;
  }

  public void startExchangeService() {
    serviceBound = true;
    this.bindService(exchangeServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API26
      startForegroundService(exchangeServiceIntent);
    } else {
      startService(exchangeServiceIntent);
    }
  }

  public void stopExchangeService() {
    serviceBound = false;
    if (exchangeService != null) {
      this.unbindService(serviceConnection);
      exchangeService = null;
    }
    stopService(exchangeServiceIntent);
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(updateServiceActionIntent);
  }

  public ExchangeService getExchangeService() {
    if (!serviceBound) {
      startExchangeService();
    }
    return exchangeService;
  }

  public String getCurrency() {
    return PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREFS_KEY_CURRENCY, "JPY");
  }
}
