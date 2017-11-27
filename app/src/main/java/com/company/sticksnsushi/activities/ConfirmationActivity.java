package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 27/11/2017.
 */

public class ConfirmationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null){
            toolbar.setTitle("Bestilling modtaget");
        }
        TextView txtSummary = (TextView) findViewById(R.id.txtSuccesSummary);
        txtSummary.setText("Kommer til at indeholde information om bestilling");
    }
}
