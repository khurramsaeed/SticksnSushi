package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

public abstract class BaseActivity extends AppCompatActivity {
    protected SticksnSushiApplication application;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        application = (SticksnSushiApplication) getApplication();
    }
}
