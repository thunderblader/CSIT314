package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csit314.R;
import com.example.csit314.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdminAddActivity extends AppCompatActivity {

    LoginActivity loginActivity;

    private EditText TextName;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextNumber;
    private EditText TextUserGroup;

    private Button addButton;
    private Button backButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String txt_Name, txt_Email, txt_Password, txt_Number, txt_UserGroup;

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_Name = TextName.getText().toString();
                txt_Email = TextEmail.getText().toString();
                txt_Password = TextPassword.getText().toString();
                txt_Number = TextNumber.getText().toString();
                txt_UserGroup = TextUserGroup.getText().toString();

                if(TextUtils.isEmpty(txt_Name) || TextUtils.isEmpty(txt_Email) ||
                    TextUtils.isEmpty(txt_Password) || TextUtils.isEmpty(txt_Number) || 
                        TextUtils.isEmpty(txt_UserGroup)){
                    Toast.makeText(UserAdminAddActivity.this, "All credentials must be entered", 
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    loginActivity.createAccount(txt_Email, txt_Password);

                    rootNode = FirebaseDatabase.getInstance();
                    //reference = rootNode.getReference("");

                    UserAdminAddHelper userAdminAddHelper = new UserAdminAddHelper(txt_Name, txt_Email, txt_Password, txt_Number, txt_UserGroup);

                    reference.child(txt_UserGroup).child(txt_Email).setValue(userAdminAddHelper);
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