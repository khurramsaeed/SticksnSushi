package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    App app = App.getInstance();
    FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();

    Button seeMenu, signUp, checkout;
    TextView loginButton;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_welcome);

        seeMenu = (Button) findViewById(R.id.seeMenuButton);
        seeMenu.setOnClickListener(this);

        signUp = (Button) findViewById(R.id.signUpButton);
        signUp.setOnClickListener(this);

        loginButton = (TextView) findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(this);

        checkout = (Button) findViewById(R.id.seeCheckoutButton);
        checkout.setOnClickListener(this);
     }

    public void updateScreen(){
        if(currentUser != null) {
            loginButton.setVisibility(TextView.GONE);
            signUp.setVisibility(Button.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == seeMenu){
                Intent menuIntent = new Intent(this, NavDrawerActivity.class);
                startActivity(menuIntent);
            }

        if (view == signUp){
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        }

        if(view == loginButton){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        if (view == checkout){
            Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
            startActivity(checkoutIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        currentUser = app.firebaseAuth.getCurrentUser();
        updateScreen();
    }
}
