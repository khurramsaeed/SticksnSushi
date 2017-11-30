package com.company.sticksnsushi.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.company.sticksnsushi.R;
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
    }
}
