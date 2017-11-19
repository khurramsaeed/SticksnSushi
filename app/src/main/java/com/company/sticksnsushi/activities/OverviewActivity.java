package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.company.sticksnsushi.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Khurram Saeed Malik on 23/10/2017.
 */

public class OverviewActivity extends BaseActivity {
    // For debugging purposes
    private static final String TAG = "OverviewActivity";

    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_overview);
    }

    public void intentMenu(View view) { startActivity(new Intent(this, MenuOverviewActivity.class));    }
}
