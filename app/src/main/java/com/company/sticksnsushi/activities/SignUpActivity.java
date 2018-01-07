package com.company.sticksnsushi.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.sticksnsushi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button createUser;
    private FirebaseAuth mAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.input_editText_signUp_email);
        editTextPassword = findViewById(R.id.input_editText_signUp_password);

        createUser = findViewById(R.id.btn_signUp);
        createUser.setOnClickListener(this);

    }

    private void registreUser() {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email er påkrævet");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Skriv venligts en gyldig email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password er påkrævet");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() <= 1) {
            editTextPassword.setError("Password skal bestå af minimum 1 tegn");
            editTextPassword.requestFocus();
            return;
        }


//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Bruger registreret", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Bruger registreret", Toast.LENGTH_LONG).show();
                        }
                        else if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == createUser) {
            System.out.println("Angivet email og password: " + email + " - " + password);
            registreUser();
        }
    }
}