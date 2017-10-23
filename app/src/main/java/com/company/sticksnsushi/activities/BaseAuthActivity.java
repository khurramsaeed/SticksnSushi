package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;

public abstract class BaseAuthActivity extends BaseActivity {
    @Override
    protected final void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        if (!application.getAuth().getUser().isLoggedIn()) {
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
            return;
        }
        onSticksnSushiCreate(savedState);
    }
    protected abstract void onSticksnSushiCreate(Bundle savedState);
}
