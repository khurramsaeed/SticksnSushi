package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            final FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run(){
                    if(currentUser != null) {
                        System.out.println("Bruger logget ind - SPLASH: " + currentUser);
                        Intent menuInent = new Intent(SplashActivity.this, NavDrawerActivity.class);
                        startActivity(menuInent);
                        finish();
                    }
                    else{
                        Intent loginInent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(loginInent);
                        finish();
                    }
                }
            },SPLASH_TIME_OUT);
        }
    }

