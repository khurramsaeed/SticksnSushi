package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.Views.SideBarNav;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    protected SideBarNav sideBarNav;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);

        if(toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_view_headline_black_24dp);

        }

    }

}
