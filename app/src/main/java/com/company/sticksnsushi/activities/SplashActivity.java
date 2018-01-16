package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 1500;
    App app = App.getInstance();
    FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run(){
                    if(currentUser != null) {
                        System.out.println("Bruger logget ind - SPLASH: " + currentUser);
                        Intent menuInent = new Intent(SplashActivity.this, MenuOverviewActivity.class);
                        startActivity(menuInent);
                        finish();
                    }
                    else{
                        Intent welcomeInent = new Intent(SplashActivity.this, WelcomeActivity.class);
                        startActivity(welcomeInent);
                        finish();
                    }
                }
            },SPLASH_TIME_OUT);
        }
    }

