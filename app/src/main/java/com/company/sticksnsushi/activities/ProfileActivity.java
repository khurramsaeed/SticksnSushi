package com.company.sticksnsushi.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.UserInfoFragment;
import com.company.sticksnsushi.fragments.FavoritesFragment;

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
            case R.id.editInfo: fragment = new UserInfoFragment();
                break;
            case R.id.editFav: fragment = new FavoritesFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_content, fragment).commit();
        }
    }
}
