package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.sticksnsushi.R;

/**
 * Created by Khurram Saeed Malik on 09/10/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Button button;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.welcome_act_btn);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == button) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }
}
