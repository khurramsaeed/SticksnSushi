package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.company.sticksnsushi.R;

/**
 * Created by Swagam on 08/01/2018.
 */

public class CartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Bestilling");
            toolbar.setNavigationIcon(R.drawable.ic_backspace);
        }
    }
}
