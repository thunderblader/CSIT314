package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserAdminRegisterActivity extends AppCompatActivity {

<<<<<<< HEAD
    Firebase firebase;
=======
    Firebase firebase = new Firebase(this);
>>>>>>> 4e3084cd5eb96760738c349c8f16361781b7f5a6

    private EditText TextEmail;
    private EditText TextPassword;

    private Button registerButton;
    private Button backButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_register);

        TextEmail = findViewById(R.id.RegisterEmail);
        TextPassword = findViewById(R.id.RegisterPassword);

        registerButton = findViewById(R.id.RegisterRegisterButton);
        backButton = findViewById(R.id.RegisterBackButton);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_Email = TextEmail.getText().toString();
                String txt_Password = TextPassword.getText().toString();

                if(TextUtils.isEmpty(txt_Email) || TextUtils.isEmpty(txt_Password)){
                    Toast.makeText(UserAdminRegisterActivity.this, "All credentials must be entered",
                            Toast.LENGTH_SHORT).show();
                }
                else if (txt_Password.length()<6){
                    Toast.makeText(UserAdminRegisterActivity.this, "password too short", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(txt_Email, txt_Password);
                }
            }
        });

        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminRegisterActivity.this, UserAdminActivity.class));
                finish();
            }
        }));
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(UserAdminRegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserAdminRegisterActivity.this, "User create Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(UserAdminRegisterActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}