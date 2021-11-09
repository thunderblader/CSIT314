package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;

import java.util.ArrayList;
import java.util.Map;

public class UserAdminSearchActivity extends AppCompatActivity {

    private EditText SearchEmail;
    private EditText TextName;
    private EditText TextNumber;
    private EditText TextUserGroup;

    private Button SearchSearchButtonUID;
    private Button SearchSearchButtonUserGrp;
    private Button backButton;
    private Button updateButton;

    String Email, UserGrp_Name;
    String txt_Name, txt_Number, txt_UserGroup;

    Firebase fb = new Firebase(UserAdminSearchActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search);


        //SearchEmail = findViewById(R.id.AddSearchTextUID);

        //SearchUID = findViewById(R.id.NewPasswordTextUID);
        //SearchUserGrp = findViewById(R.id.AddSearchTextUserGrp);


        //SearchUID = findViewById(R.id.NewPasswordTextUID);
        //SearchUserGrp = findViewById(R.id.AddSearchTextUserGrp);

        TextName = findViewById(R.id.textViewUserAdminSearchName);
        TextNumber = findViewById(R.id.textViewUserAdminSearchNumber);
        TextUserGroup = findViewById(R.id.textViewUserAdminSearchUserGrp);

        SearchSearchButtonUID = findViewById(R.id.ChangepasswordButtonUID);
        SearchSearchButtonUserGrp = findViewById(R.id.SearchSearchButtonUserGrp);
        backButton = findViewById(R.id.PasswordBackButton);
        updateButton = findViewById(R.id.PasswordUpdateButton);

        fb.signIn("theemail1234567@gmail.com", "123456");

        SearchSearchButtonUID.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = SearchEmail.getText().toString();

                if(Email.isEmpty()){
                    SearchEmail.setError("UID is required");
                    SearchEmail.requestFocus();
                    return;
                }
                if(fb.searchUser(Email) == null){
                    SearchEmail.setError("user cannot be found");
                    SearchEmail.requestFocus();
                    return;
                }

                txt_Name = fb.searchUser(Email).get("name");
                txt_Number = fb.searchUser(Email).get("number");
                txt_UserGroup = fb.searchUser(Email).get("user_type");

                UserAdminHelper uah = new UserAdminHelper();
                uah.setName(txt_Name);
                uah.setNumber(txt_Number);
                uah.setUser_group(txt_UserGroup);

                TextName.setText(uah.getName());
                TextNumber.setText(uah.getNumber());
                TextUserGroup.setText(uah.getUser_group());
            }
        }));

        SearchSearchButtonUserGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserAdminSearchActivity.this, UserAdminSearchList.class));
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Name = TextName.getText().toString();
                txt_Number = TextNumber.getText().toString();
                txt_UserGroup = TextUserGroup.getText().toString();

                if(txt_Name.isEmpty()){
                    TextName.setError("Name is required");
                    TextName.requestFocus();
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
                fb.updateUser(Email, txt_Number, txt_Name, txt_UserGroup);
            }
        });

        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminSearchActivity.this, UserAdminActivity.class));
                finish();
            }
        }));
    }
    private void collectusers(Map<String, Object> users){
        ArrayList<UserAdminHelper> helper = new ArrayList<>();
        for(Map.Entry<String, Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            helper.add(new UserAdminHelper((String) singleUser.get("name"), (String) singleUser.get("number"), (String) singleUser.get("user_type")));
        }
    }
}