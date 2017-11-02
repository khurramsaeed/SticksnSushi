package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.PopupMenuCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.Item1;
import com.company.sticksnsushi.fragments.Cart;
import com.company.sticksnsushi.fragments.Allergener;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO: 01/11/2017 Her skal indsættes cart
        getMenuInflater().inflate(R.menu.toolbar_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;

        }

        //PopupMenuCompat popupMenuCompat = new PopupMenuCompat();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedItem(id);

        return true;
    }

    /**
     * Handles logic of changning fragment while selecting Item from NavDrawer
     * @param id
     */
    private void displaySelectedItem(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.item_1: fragment = new Item1();
                break;
            case R.id.item_2: fragment = new Cart();
                break;
            case R.id.item_3: fragment = new Allergener();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_content, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void goToWelcome(View view) { startActivity(new Intent(this, WelcomeActivity.class));
    }

    public void cartPopUp(MenuItem item) {
        Toast.makeText(this, "CartPopUp", Toast.LENGTH_SHORT).show();
    }

    public void intentMenu(View view) { startActivity(new Intent(this, MenuOverviewActivity.class));    }
}
