package com.example.csit314.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.useradminview.UserAdminActivity;


public class ChangePasswordActivity extends AppCompatActivity {

    private Button backButton, updateButton;
    private EditText TextPassword;

    Firebase fb = new Firebase(ChangePasswordActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        TextPassword = findViewById(R.id.UserAdminSearchEmailText);

        backButton = findViewById(R.id.PasswordBackButton);
        updateButton = findViewById(R.id.PasswordUpdateButton);

        fb.signIn("theemail1234567@gmail.com", "123456");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(check_password(TextPassword.getText().toString()))
                    fb.change_password(TextPassword.getText().toString());
            }
        });

        backButton.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this, theLoginActivity.class));
                finish();
            }
        }));
    }

    public Boolean check_password(String the_password)
    {
        if(the_password.isEmpty())
        {
            Toast.makeText(ChangePasswordActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (the_password.length() < 6)
        {
            Toast.makeText(ChangePasswordActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}