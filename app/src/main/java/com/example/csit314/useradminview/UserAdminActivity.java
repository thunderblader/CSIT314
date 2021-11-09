package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csit314.R;
<<<<<<< HEAD
=======
import com.example.csit314.ui.login.theLoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
>>>>>>> 7b83aa6d342411910a354b93355eda45e6c4f7eb

public class UserAdminActivity extends AppCompatActivity {

    private Button addButton;
    private Button logoutButton;
    private Button searchButton;
<<<<<<< HEAD
    private Button changePasswordButton;
=======
    private Button logout_Button;
>>>>>>> 7b83aa6d342411910a354b93355eda45e6c4f7eb

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin);

        addButton = findViewById(R.id.UserAdminAddButton);
        logoutButton = findViewById(R.id.UserAdminLogoutButton);
        searchButton = findViewById(R.id.UserAdminSearchButton);
<<<<<<< HEAD
        changePasswordButton = findViewById(R.id.UserAdminChangePasswordButton);
=======
        logout_Button = findViewById(R.id.UserAdminLogoutButton);
>>>>>>> 7b83aa6d342411910a354b93355eda45e6c4f7eb


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

        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminActivity.this, theLoginActivity.class));
                finish();
            }
        });
    }
}