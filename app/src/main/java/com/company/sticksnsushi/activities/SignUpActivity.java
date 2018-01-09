package com.company.sticksnsushi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.company.sticksnsushi.infrastructure.SticksnSushiApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

// The following code originates mainly from: https://www.simplifiedcoding.net/android-firebase-tutorial-1/

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignup;
    private TextView linkLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    String userFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);
        // Back button on Toolbar
        if (getSupportActionBar() != null){
            toolbar.setTitle("Opret profil");
            toolbar.setNavigationIcon(R.drawable.ic_backspace);
        }

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextSignUpPassword);

        linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(this);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

    }

    private void registerUser(){

        //getting email and password from edit texts
        final String userName = editTextEmail.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Skriv venligst din email",Toast.LENGTH_LONG).show();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Indtast venligst en gyldig email");
            editTextEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Skriv venligst dit password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registrerer. Vent venligst...");
        progressDialog.show();


        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            SticksnSushiApplication.getInstance().getAuth().getUser().setEmail(email);
                            //SticksnSushiApplication.getInstance().getAuth().getUser().set
                            //userFullName = SticksnSushiApplication.getInstance().getAuth().getUser().setDisplayName(userName);
                            //display some message here
                            Toast.makeText(SignUpActivity.this,"Konto oprettet",Toast.LENGTH_LONG).show();
                            Intent intentMenuOverview = new Intent(SignUpActivity.this, MenuOverviewActivity.class);
                            startActivity(intentMenuOverview);
                            finish();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "Du har allerede en konto", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
//                            //display some message here
//                            Toast.makeText(SignUpActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
       if(view == linkLogin) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
           finish();

        }
        else if(view == buttonSignup){
            //calling register method on click
            registerUser();
        }
    }

}

