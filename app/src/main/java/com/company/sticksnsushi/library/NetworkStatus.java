package com.company.sticksnsushi.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.company.sticksnsushi.infrastructure.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j on 06-03-14.
 * This class is borrowed from: VUC-app
 */
public class NetworkStatus extends BroadcastReceiver {
  public enum Status {
    WIFI, MOBILE, NONE
  }

  App app = App.getInstance();

  public Status status;
  public List<Runnable> observer = new ArrayList<Runnable>();

  public boolean isOnline() {
    return status != Status.NONE;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    NetworkInfo networkInfo = app.connectivityManager.getActiveNetworkInfo();

    Status newStatus;

    if (networkInfo == null || !networkInfo.isConnected()) {
      newStatus = Status.NONE;
    } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      newStatus = Status.WIFI;
    } else {
      newStatus = Status.MOBILE;
    }

    if (status != newStatus) {
      status = newStatus;
      Log.d("NetworkStatus\n" + intent + "\n" + networkInfo, "Stats");
      for (Runnable o : new ArrayList<Runnable>(observer)) o.run();
    }
  }
}