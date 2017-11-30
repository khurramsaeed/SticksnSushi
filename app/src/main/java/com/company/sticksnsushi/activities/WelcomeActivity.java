package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.company.sticksnsushi.R;

public class WelcomeActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_welcome);
    }

    public void startOverviewActivity(View view) { startActivity(new Intent(this, NavDrawerActivity.class)); }
    public void startLoginActivity(View view) { startActivity(new Intent(this, CheckoutActivity.class));  }
}
