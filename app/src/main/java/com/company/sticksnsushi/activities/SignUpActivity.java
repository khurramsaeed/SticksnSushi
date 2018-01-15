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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// The following code originates mainly from: https://www.simplifiedcoding.net/android-firebase-tutorial-1/

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword, editTextUsersFullName;
    private Button buttonSignup;
    private TextView linkLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private App app = App.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing firebase auth object
        firebaseAuth = app.firebaseAuth;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //initializing views
        editTextEmail = findViewById(R.id.editTextSignUpEmail);
        editTextPassword = findViewById(R.id.editTextSignUpPassword);
        editTextUsersFullName = findViewById(R.id.editTextSignUpName);

        linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(this);

        buttonSignup = findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

    }

    private void createUser(){
        String userId = firebaseAuth.getCurrentUser().getUid();
        String userEmail = firebaseAuth.getCurrentUser().getEmail();
        String displayName = editTextUsersFullName.getText().toString().trim();
        app.getAuth().getUser().setHasPassword(true);
        app.getAuth().getUser().setLoggedIn(true);

        app.getAuth().getUser().setDetails(userId, displayName, userEmail);

        databaseReference.child("users").setValue(app.getAuth().getUser());
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String usersFullName = editTextUsersFullName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();

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
                            createUser();


                            //userFullName = App.getInstance().getAuth().getUser().setDisplayName(userName);

                            //display some message here
                            app.longToastMessage("Konto oprettet");
                            Intent intentMenuOverview = new Intent(SignUpActivity.this, MenuOverviewActivity.class);
                            startActivity(intentMenuOverview);
                            finish();
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                app.longToastMessage("Du har allerede en konto");
                            }
                            else {
                                app.longToastMessage(task.getException().getMessage());
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
           if (!app.network.isOnline()) {
               app.shortToastMessage("Venligst forbinde enheden med nettet!");
               return;
           }
            //calling register method on click
            registerUser();
        }
    }

}

