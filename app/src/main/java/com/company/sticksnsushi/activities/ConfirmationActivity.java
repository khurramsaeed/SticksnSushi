package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 27/11/2017.
 */

public class ConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null){
            toolbar.setTitle("Bestilling modtaget");
            toolbar.setNavigationIcon(R.drawable.arrow_left);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        TextView txtSummary = (TextView) findViewById(R.id.txtSuccesSummary);
        txtSummary.setText("Kommer til at indeholde information om bestilling");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        return true;
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
            //Override animation
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        return super.onOptionsItemSelected(item);
    }
}
