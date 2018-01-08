package com.company.sticksnsushi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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

                Toast.makeText(this, "CartPopUp", Toast.LENGTH_LONG).show();

                View menuItemView = findViewById(R.id.cartPopUp);

                PopupWindow popupwindow_obj = popupDisplay();

                popupwindow_obj.showAsDropDown(menuItemView, -40, 18); // where u want show on view click event popupwindow.showAsDropDown(view, x, y);

        }

        return super.onOptionsItemSelected(item);
    }



    public PopupWindow popupDisplay(){

        final PopupWindow popupWindow = new PopupWindow(this);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.popup_cart, null);

        popupWindow.setFocusable(true);

        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        return popupWindow;
    }


}
