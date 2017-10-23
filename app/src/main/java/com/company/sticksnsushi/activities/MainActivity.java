package com.company.sticksnsushi.activities;

import android.os.Bundle;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class MainActivity extends BaseAuthActivity {
    @Override
    protected void onSticksnSushiCreate(Bundle savedState) {

        setContentView(R.layout.activity_main);

        toolbar.setTitle("hej");

    }
}
