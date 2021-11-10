package com.example.csit314.useradminview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class UserAdminSearchList extends AppCompatActivity {

    private ArrayList<UserAdminHelper> alist;
    private RecyclerView recyclerView;

    private Button Searchbutton;
    private Button BackButton;

    private EditText TextUserGroup;

    private String search;

    Firebase fb = new Firebase(UserAdminSearchList.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_admin_search_list);
        fb.signIn("theemail1234567@gmail.com", "123456");

        BackButton = findViewById(R.id.UserAdminSearchListBackButton);
        Searchbutton = findViewById(R.id.UserAdminSearchListSearchButton);

        TextUserGroup = findViewById(R.id.UserAdminSearchListSearchText);

        recyclerView = findViewById(R.id.UserAdminRecyclerView);

        Searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alist = new ArrayList<>();
                search = TextUserGroup.getText().toString();

                if(search.isEmpty()){
                    TextUserGroup.setError("Cannot be empty");
                    TextUserGroup.requestFocus();
                    return;
                }
                if(fb.searchUser_type(search) == null){
                    TextUserGroup.setError("user cannot be found");
                    TextUserGroup.requestFocus();
                    return;
                }

                alist = collectUser(fb.searchUser_type(search));

                setAdapter();
            }
        });

        BackButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAdminSearchList.this, UserAdminSearchActivity.class));
                finish();
            }
        }));

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
        for (Map.Entry<String, Object> entry: p.entrySet())
        {
            Map singleUser = (Map) entry.getValue();
            name = (String) singleUser.get("name");
            number = (String) singleUser.get("number");

            collectUserAlist.add(new UserAdminHelper(name, number));
        }
        return collectUserAlist;
    }
}