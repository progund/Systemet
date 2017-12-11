package se.juneday.systemet.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NetworkChecker extends BroadcastReceiver {

    private static final String LOG_TAG = NetworkChecker.class.getSimpleName();
    private Context context;
    private boolean connected;

    private NetworkCheckerListener listener;
    private static NetworkChecker instance;

    public interface NetworkCheckerListener{
      public void networkChange(boolean home, NetworkInfo info);
    };

    public void setNetworkCheckerListener  (NetworkCheckerListener listener){
      Log.d(LOG_TAG, "setNetworkCheckerListener  ");
      this.listener = listener;
    }

    public static synchronized NetworkChecker getInstance(Context c) {
      if (instance==null) {
        instance = new NetworkChecker(c);
      }
      return instance;
    }

    private NetworkChecker(Context context) {
      super();
      this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      Log.d(LOG_TAG, "onReceive ....");
      String action = intent.getAction();
      ConnectivityManager cm =
          (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
      connected= activeNetwork != null &&
          activeNetwork.isConnectedOrConnecting();
      if (listener!=null) {
        listener.networkChange(connected, activeNetwork);
      }
    }

}

