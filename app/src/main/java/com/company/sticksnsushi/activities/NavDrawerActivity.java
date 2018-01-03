package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.PreviousOrdersFragment;
import com.company.sticksnsushi.fragments.TakeAwayFragment;
import com.company.sticksnsushi.fragments.CartFragment;
import com.company.sticksnsushi.fragments.AllergiesFragment;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class NavDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "NavDrawerActivity";

    private static final String BUNDLE_STATE = "BUNDLE_STATE";
    // Conceptuel two-states
    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;

    private int currentState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);

        //Special toolbar for this activity
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Takeaway");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Screen rotation: Editing fields fix
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: STATE_VIEWING");

            // Default set to: Udforsk menu
            displaySelectedItem(R.id.item_takeaway);

            changeState(STATE_VIEWING);
        } else {
            Log.d(TAG, "onCreate: BUNDLE_STATE");


            changeState(savedInstanceState.getInt(BUNDLE_STATE));
        }
    }

    /**
     * This method is called when you rotate screen
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Saved state is reloaded here
        outState.putInt(BUNDLE_STATE, currentState);
    }

    private void changeState(int state) {
        if (state == currentState)
            return;

        currentState = state;

        if (state == STATE_VIEWING) {


        } else if (state == STATE_EDITING) {

        } else
            throw new IllegalArgumentException("Invalid state: " + state);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedItem(id);

        return true;
    }

    /**
     * Handles logic of changning fragment while selecting Item from NavDrawer
     *
     * @param id
     */
    private void displaySelectedItem(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.item_takeaway:
                fragment = new TakeAwayFragment();
                break;
            case R.id.item_cart:
                fragment = new CartFragment();
                break;
            case R.id.item_allergies:
                fragment = new AllergiesFragment();
                break;

            case R.id.item_previous_orders:
                fragment = new PreviousOrdersFragment();
                break;

            case R.id.item_profile:
                startActivity(new Intent(this, ProfileActivity.class));
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_content, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

}