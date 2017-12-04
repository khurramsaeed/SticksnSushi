package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.fragments.CheckoutTimeFragment;
import com.company.sticksnsushi.fragments.PaymentFragment;
import com.company.sticksnsushi.fragments.InformationFragment;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;

public class CheckoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null) {
            toolbar.setTitle("Bestilling");
            toolbar.setNavigationIcon(null);
        }

        // Default: Fragment
        selectNavItemFragment(new CheckoutTimeFragment());

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
     * selectNavItem methods simple switch case which correspond to the given MenuItem id
     * I return my bottm navigationview fragments here
     * @param item
     */
    private void selectNavItem(MenuItem item) {
                switch (item.getItemId()) {
            case R.id.menu_chosen_time: selectNavItemFragment(new CheckoutTimeFragment());
                break;
            case R.id.menu_information: selectNavItemFragment(new InformationFragment());
                break;
            case R.id.menu_payment: selectNavItemFragment(new PaymentFragment());
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
        ft.replace(R.id.activity_checkout_frame, fragment);
        ft.commit();
    }
}
