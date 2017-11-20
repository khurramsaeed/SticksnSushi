package com.company.sticksnsushi.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.FirstFragment;
import com.company.sticksnsushi.fragments.SecondFragment;
import com.company.sticksnsushi.fragments.ThirdFragment;

public class CheckoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Default: Fragment
        selectNavItemFragment(new FirstFragment());

        BottomNavigationView checkoutNav = (BottomNavigationView) findViewById(R.id.checkout_navigation);
        checkoutNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectNavItem(item);
                return true;
            }
        });
    }

    /**
     * Effects back button in current activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * selectNavItem methods simple switch case which correspond to the given MenuItem id
     * I return my bottm navigationview fragments here
     * @param item
     */
    private void selectNavItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_information: selectNavItemFragment(new FirstFragment());
                break;
            case R.id.menu_payment: selectNavItemFragment(new SecondFragment());
                break;
            case R.id.menu_confirm: selectNavItemFragment(new ThirdFragment());
                break;
        }
    }

    /**
     * selectNavItemFragment does fragment transaction while we can simply give it an argument of fragment
     * in this case it is optimal to have this method
     * @param fragment
     */
    private void selectNavItemFragment(Fragment fragment) {
        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();
        // XML files for animation are downloaded from internet
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.activity_main_frame, fragment);
        ft.commit();
    }
}
