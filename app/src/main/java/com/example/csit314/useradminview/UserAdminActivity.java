package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csit314.R;

import com.example.csit314.ui.login.theLoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class UserAdminActivity extends AppCompatActivity {

    private Button addButton;
    private Button searchButton;

    private Button changePasswordButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        addButton = findViewById(R.id.UserAdminAddButton);
        searchButton = findViewById(R.id.UserAdminSearchButton);

        changePasswordButton = findViewById(R.id.UserAdminChangePasswordButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminActivity.this, UserAdminAddActivity.class));
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminActivity.this, UserAdminSearchActivity.class));
                finish();
            }
        });
    }
}