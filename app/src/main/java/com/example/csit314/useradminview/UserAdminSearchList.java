package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAdminSearchList extends AppCompatActivity {

    private static String TAG = "";

    private ListView mListView;
    private List<String> keys = new ArrayList<>();

    DatabaseReference reference;
    FirebaseDatabase database;

    Firebase firebase = new Firebase(UserAdminSearchList.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search_list);

        mListView = (ListView) findViewById(R.id.ListView);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                search(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void search(DataSnapshot snapshot) {
        for(DataSnapshot ds : snapshot.getChildren()){

            UserAdminAddHelper helper = new UserAdminAddHelper();

            TAG = ds.getValue().toString();

            keys.add(TAG);

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, keys);
            mListView.setAdapter(adapter);
        }
    }


}