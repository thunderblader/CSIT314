package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdminAddActivity extends AppCompatActivity {

    Firebase fb = new Firebase(UserAdminAddActivity.this);

    private EditText TextName;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextNumber;
    private EditText TextUserGroup;

    private Button addButton;
    private Button backButton;

    String Name, Email, Password, Number, UserGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_add);

        TextName = findViewById(R.id.AddNameText);
        TextEmail = findViewById(R.id.AddEmailText);
        TextPassword = findViewById(R.id.AddPasswordText);
        TextNumber = findViewById(R.id.AddNumberText);
        TextUserGroup = findViewById(R.id.AddUserGroupText);

        addButton = findViewById(R.id.AddAddButton);
        backButton = findViewById(R.id.AddBackButton);

        //fb.signIn("theemail1234567@gmail.com", "123456");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = TextName.getText().toString();
                Email = TextEmail.getText().toString();
                Password = TextPassword.getText().toString();
                Number = TextNumber.getText().toString();
                UserGroup = TextUserGroup.getText().toString();

                if(Name.isEmpty()){
                    TextName.setError("Name is required");
                    TextName.requestFocus();
                    return;
                }
                if(Email.isEmpty()){
                    TextEmail.setError("Email is required");
                    TextEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    TextEmail.setError("Please enter a valid email");
                    TextEmail.requestFocus();
                    return;
                }
                if(Password.isEmpty()){
                    TextPassword.setError("Password is required");
                    TextPassword.requestFocus();
                    return;
                }
                if(Password.length()<6){
                    TextPassword.setError("Please enter > 6 characters");
                    TextPassword.requestFocus();
                    return;
                }
                if(Number.isEmpty()){
                    TextNumber.setError("Number is required");
                    TextNumber.requestFocus();
                    return;
                }
                if(UserGroup.isEmpty()){
                    TextUserGroup.setError("User group is required");
                    TextUserGroup.requestFocus();
                    return;
                }
                else{
                    fb.createAccount(Email, Password, Name, Number, UserGroup);
                }
            }
        });

        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminAddActivity.this, UserAdminActivity.class));
                finish();
            }
        }));
    }
}