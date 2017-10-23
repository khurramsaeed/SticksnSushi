package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.sticksnsushi.R;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_welcome);

        Button b = (Button) findViewById(R.id.overview_btn);
        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, OverviewActivity.class);
        startActivity(i);
    }
}
