package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobile2.p3210_a3.R;
import com.mobile2.p3210_a3.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;

    Button goToRegistrationBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        goToRegistrationBtn = findViewById(R.id.goToRegistrationButton);
        loginBtn = findViewById(R.id.loginButton);

        binding.goToRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), Register.class);
                startActivity(intentObj);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEntered = binding.emailEditText.getText().toString().trim();
                String passwordEntered = binding.passEditText.getText().toString().trim();

                // Adding this check because I kept getting crashed when entering nothing for sign in
                if(emailEntered.isEmpty() || passwordEntered.isEmpty()) {
                    if(emailEntered.isEmpty()){
                        binding.emailEditText.setError("Email required!");
                    }
                    if(passwordEntered.isEmpty()){
                        binding.passEditText.setError("Password required!");
                    }
                    Toast.makeText(Login.this, "Error: Missing Fields!", Toast.LENGTH_SHORT).show();
                    return;
                }


                signIn(emailEntered, passwordEntered);
            }
        });
    }

    private void signIn(String email, String password){

        if(email == null || password == null){
            Toast.makeText(Login.this, "Error: Both email and password required!", Toast.LENGTH_SHORT).show();

        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("tag", "signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Authentication passed.", Toast.LENGTH_SHORT).show();
                            Intent intentObj = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intentObj);
                            finish();
                        } else {
                            // if login fails tell us
                            String errorMessage = "";
                            if (task.getException() != null) {
                                errorMessage = task.getException().getMessage();
                            }

                            Log.i("tag", "signInWithEmail:failed");
                            Toast.makeText(Login.this, "Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}