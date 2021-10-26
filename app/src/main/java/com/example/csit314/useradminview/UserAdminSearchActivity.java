package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csit314.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAdminSearchActivity extends AppCompatActivity {

    private EditText EditUID;
    private TextView TextName;

    private Button searchButton;
    private Button backButton;

    DatabaseReference reference;

    String UID_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search);

        EditUID = findViewById(R.id.AddSearchText);
        TextName = findViewById(R.id.textViewUserAdminSearchName);

        searchButton = findViewById(R.id.SearchSearchButton);
        backButton = findViewById(R.id.SearchBackButton);

        reference = FirebaseDatabase.getInstance().getReference();

        searchButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UID_Name = EditUID.getText().toString();

                if(UID_Name.isEmpty()){
                    EditUID.setError("UID is required");
                    EditUID.requestFocus();
                    return;
                }

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if(ds.child("txt_UserGroup").getValue().equals(UID_Name)){
                                TextName.setText("found");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }));

        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminSearchActivity.this, UserAdminActivity.class));
                finish();
            }
        }));
    }
}