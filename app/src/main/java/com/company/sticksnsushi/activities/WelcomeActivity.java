package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.company.sticksnsushi.R;

public class WelcomeActivity extends BaseActivity {

    AnimationDrawable animationDrawable;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_welcome);
     }

    public void startOverviewActivity(View view) { startActivity(new Intent(this, NavDrawerActivity.class)); }
    public void startLoginActivity(View view) { startActivity(new Intent(this, LoginActivity.class));  }
    public void startSignUpActivity(View view) { startActivity(new Intent(this, SignUpActivity.class));  }
    public void startCheckoutActivity(View view) { startActivity(new Intent(this, CheckoutActivity.class));  }

}
