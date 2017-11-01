package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;

import com.company.sticksnsushi.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null){
            toolbar.setLogo(R.drawable.logo_text);
            toolbar.setTitle(Html.fromHtml("<font color='#cecece'></font>"));
            setSupportActionBar(toolbar);
            //Doesnt work:
            toolbar.setNavigationIcon(R.drawable.menu);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}
