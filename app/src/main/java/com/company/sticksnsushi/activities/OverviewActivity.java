package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.company.sticksnsushi.R;
//REMOVE THIS ACTIVITY - INCLUDING XML FILE
// TODO: 27/11/2017 DELETE THIS ACTIVITY
@Deprecated
public class OverviewActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_overview);

    }

    public void intentMenu(View view) { startActivity(new Intent(this, MenuOverviewActivity.class));    }
}
