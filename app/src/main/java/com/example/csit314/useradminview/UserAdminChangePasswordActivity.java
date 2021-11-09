package com.example.csit314.useradminview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.csit314.R;
import com.example.csit314.data.Firebase;


public class UserAdminChangePasswordActivity extends AppCompatActivity {

    private Button backButton, updateButton;
    private EditText TextPassword;

    String NewPassword;

    Firebase fb = new Firebase(UserAdminChangePasswordActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_changepassword);

        TextPassword = findViewById(R.id.UserAdminSearchEmailText);

        backButton = findViewById(R.id.PasswordBackButton);
        updateButton = findViewById(R.id.PasswordUpdateButton);

        fb.signIn("theemail1234567@gmail.com", "123456");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                NewPassword = TextPassword.getText().toString();
                if(NewPassword.isEmpty())
                {
                    Toast.makeText(UserAdminChangePasswordActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (NewPassword.length() < 6)
                {
                    Toast.makeText(UserAdminChangePasswordActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                fb.change_password(NewPassword);
            }
        });

        backButton.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminChangePasswordActivity.this, UserAdminActivity.class));
                finish();
            }
        }));
    }
}