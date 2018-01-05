package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.company.sticksnsushi.R;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_welcome);
    }

    public void startOverviewActivity(View view) { startActivity(new Intent(this, NavDrawerActivity.class)); }
    public void startLoginActivity(View view) { startActivity(new Intent(this, CheckoutActivity.class));  }
    public void startSignUpActivity(View view) { startActivity(new Intent(this, LoginActivity.class));  }
}
