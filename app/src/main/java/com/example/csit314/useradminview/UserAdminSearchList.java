package com.example.csit314.useradminview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.prescribe.Prescription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAdminSearchList extends AppCompatActivity {

    private ArrayList<UserAdminHelper> alist;
    private RecyclerView recyclerView;

    private Button button;

    Firebase fb = new Firebase(UserAdminSearchList.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search_list);
        fb.signIn("theemail1234567@gmail.com", "123456");

        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.UserAdminRecyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alist = new ArrayList<>();
                alist = collectUser(fb.searchUser2("theemail1234567@gmail.com"));
                //String name = fb.searchUser("theemail1234567@gmail.com").get("name");
                //String number = fb.searchUser("theemail1234567@gmail.com").get("number");
                // user_type = fb.searchUser("theemail1234567@gmail.com").get("user_type");
                //alist.add(new UserAdminHelper(name, number, user_type));

                setAdapter();
            }
        });

        //setUserInfo();
    }

    private void setAdapter() {
        UserAdminRecyclerAdapter adapter = new UserAdminRecyclerAdapter(alist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    public ArrayList<UserAdminHelper> collectUser(Map<String,Object> p)
    {
        ArrayList<UserAdminHelper> collectUserAlist = new ArrayList<>();

        String name;
        String number;
        String user_group;
        for (Map.Entry<String, Object> entry: p.entrySet())
        {
            Map singleUser = (Map) entry.getValue();
            user_group = (String) singleUser.get("user_type");
            name = (String) singleUser.get("name");
            number = (String) singleUser.get("number");

            collectUserAlist.add(new UserAdminHelper(name, number, user_group));
        }
        return collectUserAlist;
    }
}