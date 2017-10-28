package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 26/10/2017.
 */

public class MenuOverviewActivity extends BaseActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_menuoverview);

        viewPager = (ViewPager) findViewById(R.id.menuoverview_viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(this));


    }

}
