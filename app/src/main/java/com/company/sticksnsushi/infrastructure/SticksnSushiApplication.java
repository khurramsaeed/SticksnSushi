package com.company.sticksnsushi.infrastructure;

import android.app.Application;
import android.util.Log;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class SticksnSushiApplication extends Application {
    private static final String TAG = "SticksnSushiApplication";
    private Auth auth;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: Applicated Started");
        super.onCreate();
        auth = new Auth(this);
    }

    public Auth getAuth() {
        return auth;
    }
}
