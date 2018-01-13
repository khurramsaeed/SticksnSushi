package com.company.sticksnsushi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.App;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener, Runnable {

    private TextView linkSignUp;
    private Button button_login;
    private EditText editTextEmail, editTextPassword;
    private ProgressDialog progressDialog;

    App app = App.getInstance();
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app.register(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = app.firebaseAuth;

        //initializing views
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_password);

        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(this);

        linkSignUp = findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(this);
    }

    private void userLogin(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            app.longToastMessage("Skriv venligst din email");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Indtast venligst en gyldig email");
            editTextEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            app.longToastMessage("Skriv venligst dit password");
            return;
        }
        if(password.length() < 6){
            app.longToastMessage("Skriv minimum 6 tegn");
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logger ind...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //User is logged in
                    app.getAuth().getUser().setLoggedIn(true);
                    app.getAuth().getUser().setEmail(email);

                    Intent intentMenuOverview = new Intent(LoginActivity.this, NavDrawerActivity.class);
                    startActivity(intentMenuOverview);
                    finish();
                }
                else{
                    app.longToastMessage(task.getException().getMessage());
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == linkSignUp) {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
            finish();

        }
        else if(view == button_login){
            if (!app.network.isOnline()) {
                app.shortToastMessage("Venligst forbinde enheden med nettet!");
                return;
            }
            userLogin();
        }
    }

    @Override
    protected void onDestroy() {
        app.unregister(this);
        super.onDestroy();
    }

    @Override
    public void run() {

    }
}
