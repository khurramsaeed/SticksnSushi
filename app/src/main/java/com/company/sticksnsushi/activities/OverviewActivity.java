package com.company.sticksnsushi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.CategoryItem;

import java.util.ArrayList;
import java.util.HashMap;

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
