package com.company.sticksnsushi.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.company.sticksnsushi.infrastructure.BadgeDrawable;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Button clickbtn;
    private MenuItem item;

    SticksnSushiApplication app = SticksnSushiApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        toolbar = (Toolbar) findViewById(R.id.include_toolbar);

        if (toolbar != null) {
            // toolbar.setLogo(R.drawable.logo_text);
            // toolbar.setTitle(Html.fromHtml("<font color='#cecece'></font>"));
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.menu);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_pop_up, menu);

        item = menu.findItem(R.id.cartPopUp);
        Check();
        return true;
    }

    /**
     * Checks for boolean value becomes true
     * Then enables button input
     */

    int temp = app.getCart().getItems().size();
    private void Check() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (temp != app.getCart().getItems().size()) {
                    LayerDrawable icon = (LayerDrawable) item.getIcon();
                    setBadgeCount(getApplicationContext(), icon, "" + app.getCart().getItems().size());

                } else {
                    Check();
                }
            }
        }, 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            case R.id.cartPopUp:
                View menuItemView = findViewById(R.id.cartPopUp);
                PopupWindow popupwindow_obj = popupDisplay();
                popupwindow_obj.showAsDropDown(menuItemView, -40, 18);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public PopupWindow popupDisplay() {

        final PopupWindow popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_cart, null);

        popupWindow.setFocusable(true);

        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);

        clickbtn = view.findViewById(R.id.button1);
        clickbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        return popupWindow;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


}
