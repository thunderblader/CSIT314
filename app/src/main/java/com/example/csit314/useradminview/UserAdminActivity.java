package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csit314.R;

import com.example.csit314.ui.login.ChangePasswordActivity;
import com.example.csit314.ui.login.theLoginActivity;


public class UserAdminActivity extends AppCompatActivity {

    private Button addButton;
    private Button searchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        addButton = findViewById(R.id.UserAdminAddButton);
        searchButton = findViewById(R.id.UserAdminSearchButton);



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
    public void changePasswordOnClick(View view)
    {
        Intent i = new Intent( this, ChangePasswordActivity.class);
        startActivity(i);
    }

    public void logoutOnClick(View view)
    {
        Intent i = new Intent( this, theLoginActivity.class);
        startActivity(i);
    }
}