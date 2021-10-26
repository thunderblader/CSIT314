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

    //Firebase firebase = new Firebase();



    private EditText TextName;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextNumber;
    private EditText TextUserGroup;

    private Button addButton;
    private Button backButton;

    DatabaseReference reference;
    FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_Name = TextName.getText().toString();
                txt_Email = TextEmail.getText().toString();
                txt_Password = TextPassword.getText().toString();
                txt_Number = TextNumber.getText().toString();
                txt_UserGroup = TextUserGroup.getText().toString();

                if(txt_Name.isEmpty()){
                    TextName.setError("Name is required");
                    TextName.requestFocus();
                    return;
                }
                if(txt_Email.isEmpty()){
                    TextEmail.setError("Email is required");
                    TextEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txt_Email).matches()){
                    TextEmail.setError("Please enter a valid email");
                    TextEmail.requestFocus();
                    return;
                }
                if(txt_Password.isEmpty()){
                    TextPassword.setError("Password is required");
                    TextPassword.requestFocus();
                    return;
                }
                if(txt_Password.length()<6){
                    TextPassword.setError("Please enter > 6 characters");
                    TextPassword.requestFocus();
                    return;
                }
                if(txt_Number.isEmpty()){
                    TextNumber.setError("Number is required");
                    TextNumber.requestFocus();
                    return;
                }
                if(txt_UserGroup.isEmpty()){
                    TextUserGroup.setError("User group is required");
                    TextUserGroup.requestFocus();
                    return;
                }
                else{
                    UserAdminAddHelper userAdminAddHelper = new UserAdminAddHelper(txt_Name, txt_Email, txt_Password, txt_Number, txt_UserGroup);
                    //registerUser(txt_Email, txt_Password);
                    //firebase.createAccount(txt_Name, txt_Email, txt_Password, txt_Number, txt_UserGroup);

                    //String uid = loginActivity.getUID();
                    reference.child(txt_UserGroup).setValue(userAdminAddHelper);
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

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(UserAdminAddActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserAdminAddActivity.this, "User create Successful", Toast.LENGTH_SHORT).show();
                    reference = FirebaseDatabase.getInstance().getReference();
                    UserAdminAddHelper userAdminAddHelper = new UserAdminAddHelper(txt_Name, txt_Email, txt_Password, txt_Number, txt_UserGroup);

                    reference.child(txt_UserGroup).child(auth.getCurrentUser().getUid()).setValue(userAdminAddHelper);
                }
                else{
                    Toast.makeText(UserAdminAddActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}