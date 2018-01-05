package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.company.sticksnsushi.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    TextView linkSignUp;
    Button button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(this);

        linkSignUp = findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == linkSignUp) {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
    }
}
