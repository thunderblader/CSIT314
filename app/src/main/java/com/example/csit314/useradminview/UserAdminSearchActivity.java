package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.prescribe.Prescription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class UserAdminSearchActivity extends AppCompatActivity {

    private EditText SearchUID;
    private EditText SearchUserGrp;
    private EditText TextName;
    private EditText TextNumber;
    private EditText TextUserGroup;

    private Button SearchSearchButtonUID;
    private Button SearchSearchButtonUserGrp;
    private Button backButton;
    private Button updateButton;

    String UID_Name, UserGrp_Name;
    String txt_Name, txt_Number, txt_UserGroup;

    Firebase fb = new Firebase(UserAdminSearchActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search);

        SearchUID = findViewById(R.id.AddSearchTextUID);
        SearchUserGrp = findViewById(R.id.AddSearchTextUserGrp);
        TextName = findViewById(R.id.textViewUserAdminSearchName);
        TextNumber = findViewById(R.id.textViewUserAdminSearchNumber);
        TextUserGroup = findViewById(R.id.textViewUserAdminSearchUserGrp);

        SearchSearchButtonUID = findViewById(R.id.SearchSearchButtonUID);
        SearchSearchButtonUserGrp = findViewById(R.id.SearchSearchButtonUserGrp);
        backButton = findViewById(R.id.SearchBackButton);
        updateButton = findViewById(R.id.SearchUpdateButton);

        fb.signIn("theemail1234567@gmail.com", "123456");

        SearchSearchButtonUID.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UID_Name = SearchUID.getText().toString();

                if(UID_Name.isEmpty()){
                    SearchUID.setError("UID is required");
                    SearchUID.requestFocus();
                    return;
                }

                txt_Name = fb.searchUser("theemail1234567@gmail.com").get("name");
                txt_Number = fb.searchUser("theemail1234567@gmail.com").get("number");
                txt_UserGroup = fb.searchUser("theemail1234567@gmail.com").get("user_type");

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
                UserGrp_Name = SearchUserGrp.getText().toString();

                if(UserGrp_Name.isEmpty()){
                    SearchUID.setError("User Group is required");
                    SearchUID.requestFocus();
                    return;
                }

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
                fb.updateUser(UID_Name, txt_Number, txt_Name, txt_UserGroup);
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