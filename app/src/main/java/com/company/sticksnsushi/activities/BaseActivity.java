package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

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

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);

        if(toolbar != null){
            // toolbar.setLogo(R.drawable.logo_text);
            // toolbar.setTitle(Html.fromHtml("<font color='#cecece'></font>"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.menu);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO: 01/11/2017 Her skal indsættes cart

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_pop_up, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {

            case R.id.cartPopUp:

                Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();

                View menuItemView = findViewById(R.id.cartPopUp);


                PopupMenu popupMenu = new PopupMenu(this, menuItemView);

                popupMenu.inflate(R.menu.cart_pop_up);

                popupMenu.show();

                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
