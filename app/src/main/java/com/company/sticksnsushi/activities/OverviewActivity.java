package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 23/10/2017.
 */

public class OverviewActivity extends BaseActivity implements View.OnClickListener {
    View target;
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_overview);

        target = (View) findViewById(R.id.target_activity_overview);
        target.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == target) {
            Intent intent =  new Intent(this, MenuOverviewActivity.class);
            startActivity(intent);
        }
    }
}
