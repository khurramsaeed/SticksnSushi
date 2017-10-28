package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 23/10/2017.
 */

public class OverviewActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_overview);
    }

    public void intentMenu(View view) { startActivity(new Intent(this, MenuOverviewActivity.class));    }
}
