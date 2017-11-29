package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.activities.BaseActivity;
import com.company.sticksnsushi.fragments.AllergiesFragment;
import com.company.sticksnsushi.fragments.CartFragment;
import com.company.sticksnsushi.fragments.PreviousOrders;
import com.company.sticksnsushi.fragments.TakeAwayFragment;
import com.company.sticksnsushi.fragments.UserProfileFragment1;
import com.company.sticksnsushi.fragments.UserProfileFragment2;
import com.company.sticksnsushi.fragments.UserProfileFragment3;
import com.company.sticksnsushi.fragments.UserProfileFragment4;

/**
 * Created by Swagam on 20/11/2017.
 */



public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_profile);

        displaySelectedItem(2);
    }

    private void displaySelectedItem(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.editInfo: fragment = new UserProfileFragment1();
                break;
            case R.id.editFav: fragment = new UserProfileFragment2();
                break;
            case R.id.editAllergies: fragment = new UserProfileFragment3();
                break;

            case R.id.editRestaurant: fragment = new UserProfileFragment4();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_content, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
