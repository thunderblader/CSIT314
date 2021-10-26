package com.example.csit314.data;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.csit314.useradminview.UserAdminAddActivity;
import com.example.csit314.useradminview.UserAdminAddHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.Executor;

public class Firebase {

    private FirebaseUser current_User;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference user_ref;
    private ValueEventListener postListener;
    private DataSnapshot dataSnapshot;

    private String the_name;
    private String the_number;
    private String the_userType;

    private boolean signed_in = false;
    private boolean firebase_ready = false;

    private CountDownTimer firebase_timer;

    private Activity activityReference;

    public FirebaseUser getCurrent_User() { return current_User; }
    public FirebaseAuth getmAuth() { return mAuth; }
    public FirebaseDatabase getDatabase() { return database; }

    public String getUID() { return mAuth.getUid(); }

    public String getThe_number() { return the_number; }
    public String getThe_name() { return the_name; }
    public String getThe_userType() { return the_userType; }

    public Firebase(Activity currentActivity)
    {
        activityReference = currentActivity;
        run_firebase();
    }

    private void start_firebase()
    {
        firebase_timer = new CountDownTimer(3000,1000)
        {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish()
            {
                if(!firebase_ready)
                    run_firebase();
            }
        };
        firebase_timer.start();
    }

    private void run_firebase()
    {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mAuth.getCurrentUser() != null)
        {
            complete_signin();
            fetchData();
        }
        if(mAuth == null || database == null || mDatabase == null || user_ref == null)
            start_firebase();
        else
            firebase_ready = true;
    }

    public void createAccount(String name, String email, String password, String number, String user_group)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activityReference, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            complete_signin();
                            setData(number, name, user_group);
                        }
                    }
                });
    }

    public void signIn(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( activityReference, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        complete_signin();
                        fetchData();
                    }
                }
            });
    }

    private void complete_signin()
    {
        signed_in = true;
        user_ref = mDatabase.child("User_Group").child(getUID());
        current_User = mAuth.getCurrentUser();
    }

    private void setData(String number, String name, String user_group)
    {
        the_number = number;
        the_name = name;
        the_userType = user_group;
        user_ref.child("number").setValue(number);
        user_ref.child("name").setValue(name);
        user_ref.child("user_group").setValue(user_group);
    }

    private void fetchData()
    {
        the_number = user_ref.child("number").get().toString();
        the_name = user_ref.child("name").get().toString();
        the_userType = user_ref.child("user_group").get().toString();
    }

    public void signout() { mAuth.signOut(); }

    //============================================================================
    //below this line is for testing purposes ONLY
    //reading from database requires firebase user to not be null
    private void writetoDatabase()
    {
        mDatabase.setValue("user1");
    }

    private String readfromDatabase()   //we need to read the database to get a snapshot of the data
    {
        String data = mDatabase.get().toString();
        if (data != null)
            return data;
        else
            return null;
    }

    private void createUser(String name)
    {
        //logout();
        //createAccount("thisEmail@gmail.com", "123456");
        mDatabase.setValue("user111");
    }

    private String searchUser(String email)
    {
       // UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        return "";
    }

    private String[] readPrecription(String name)    //name -> data (date,drug,drug,drug...)
    {
        //StringTokenizer token = new StringTokenizer("", ",");
        if(dataSnapshot.child(name).getChildrenCount() == 0)
            return null;
        int i = 0;
        String[] theData = new String[(int)dataSnapshot.child(name).getChildrenCount()];
        mDatabase.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren())
                {
                    String thisData = dataSnap.getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return theData;
    }

    private String generateRandomstring()
    {
        Random generator = new Random();
        StringBuilder randomString = new StringBuilder();
        char tempChar;
        for (int i = 0; i < 16; i++)
        {
            tempChar = (char)(generator.nextInt(96) + 32);
            randomString.append(tempChar);
        }
        return randomString.toString();
        //String str = new Random().
    }
}
