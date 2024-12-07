package com.mobile2.p3210_a3.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.mobile2.p3210_a3.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;

    EditText emailEntry;
    EditText passEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        emailEntry = findViewById(R.id.emailEditText);
        passEntry = findViewById(R.id.passwordEditText);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEntered = binding.emailEditText.getText().toString().trim();
                String passwordEntered = binding.passwordEditText.getText().toString().trim();

                // Adding this check because I kept getting crashes when entering nothing for sign in
                if(emailEntered.isEmpty() || passwordEntered.isEmpty()) {
                    if(emailEntered.isEmpty()){
                        binding.emailEditText.setError("Email required!");
                    }
                    if(passwordEntered.isEmpty()){
                        binding.passwordEditText.setError("Password required!");
                    }
                    Toast.makeText(Register.this, "Error: Missing Fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerUser(emailEntered, passwordEntered);
            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoBackToLogin();
            }
        });

    }

    private void registerUser(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "registerUser Pass." + user.getUid(), Toast.LENGTH_SHORT).show();

                            Intent intentObj = new Intent(getApplicationContext(), Login.class);
                            startActivity(intentObj);
                            finish();
                        } else{
                            String errorMessage = "";
                            if (task.getException() != null) {
                                errorMessage = task.getException().getMessage();
                            }

                            Toast.makeText(Register.this, "Failed." + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GoBackToLogin(){
        startActivity(new Intent(this, Login.class));
    }
}