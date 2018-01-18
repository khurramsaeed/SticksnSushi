package com.company.sticksnsushi.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener{

    private App app = App.getInstance();
    private FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
    private Button seeMenu, signUp;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_welcome);

        View relativeLayout = (View) findViewById(R.id.activity_welcome_relativeLayout);
        View linearLayout = (View) findViewById(R.id.activity_welcome_linearLayout);
        // For modifying layout parameters
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();

        if (Build.VERSION.SDK_INT < 22) {
            //Resolves issues with background rendering high quality bitmaps
            relativeLayout.setBackgroundColor(app.res.getColor(R.color.colorSecondary));
        }

        if (!isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.setMargins(0, 0, 0, 0);
        } else if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.setMargins(0, params.getMarginStart()*8, 0, 0);
        }

        seeMenu = (Button) findViewById(R.id.seeMenuButton);
        seeMenu.setOnClickListener(this);

        signUp = (Button) findViewById(R.id.signUpButton);
        signUp.setOnClickListener(this);

        loginButton = (TextView) findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(this);

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
    }

    @Override
    public void onResume() {
        super.onResume();
        currentUser = app.firebaseAuth.getCurrentUser();
        updateScreen();
    }
}
