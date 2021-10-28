package com.example.csit314.data;

import android.app.Activity;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;

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

import java.util.Map;
import java.util.Random;

public class Firebase {

    private FirebaseUser current_User;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference user_ref;
    private ValueEventListener postListener;
    private DataSnapshot dataSnapshot;
    private DataSnapshot dataSnapshotReference;

    private String the_name;
    private String the_number;
    private String the_userType;

    private boolean signed_in = false;
    private boolean firebase_ready = false;
    private boolean database_ready = false;

    private CountDownTimer firebase_timer;

    private Activity activityReference;

    private Map<String, String> the_map;

    //public FirebaseUser getCurrent_User() { return current_User; }
    //public FirebaseAuth getmAuth() { return mAuth; }
    //public FirebaseDatabase getDatabase() { return database; }

    public DatabaseReference getDatabase_ref() { return user_ref; }

    public String getUID() { return mAuth.getUid(); }
    public String getThe_number() { return the_number; }
    public String getThe_name() { return the_name; }
    public String getThe_userType() { return the_userType; }
    public String getThe_userData(String data_name) { return dataSnapshotReference.child("User_Group").child(getUID()).child(data_name).getValue().toString(); }

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
                if(!firebase_ready) //when firebase has finished initialising
                    run_firebase();
            }
        };
        firebase_timer.start();
    }

    private void run_firebase()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(mAuth != null && mDatabase != null)
        {
            //if(mAuth.getCurrentUser() != null)  //incase the user is still signed in
            //    complete_signin();
            firebase_ready = true;
        }
        if(!firebase_ready)
            start_firebase();
    }

    private void complete_signin()
    {
        signed_in = true;
        user_ref = mDatabase.child("User_Group").child(getUID());
        current_User = mAuth.getCurrentUser();
    }

    public void createAccount(String email, String password, String name, String number, String user_type)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activityReference, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            complete_signin();
                            setData(number, name, user_type);

                            fetch_database(mDatabase);
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
                        fetch_database(mDatabase);
                    }
                }
            });
    }

    public void signout() { mAuth.signOut(); }

    private void setData(String number, String name, String user_type)
    {
        user_ref.child("prescription").setValue("null");
        user_ref.child("number").setValue(number);
        user_ref.child("name").setValue(name);
        user_ref.child("user_type").setValue(user_type);
    }

    public void fetch_database(DatabaseReference the_reference)
    {
        ValueEventListener postListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                dataSnapshotReference = dataSnapshot;
                the_map = (Map) dataSnapshot.child("User_Group").child(getUID()).getValue();
                fetchData();
                database_ready = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        the_reference.addValueEventListener(postListener);
    }

    private void fetchData()
    {
        the_number = dataSnapshotReference.child("User_Group").child(getUID()).child("number").getValue().toString();
        the_name = dataSnapshotReference.child("User_Group").child(getUID()).child("name").getValue().toString();
        the_userType = dataSnapshotReference.child("User_Group").child(getUID()).child("user_type").getValue().toString();
    }

    public Map<String, String> get_pastprescription(String user_id)
    {
        //Map<String, String> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(user_id).child("prescription").getValue();
        Map<String, String> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(getUID()).getValue();
        return my_prescription;
    }

    public String generateRandomstring(int length)
    {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random generator = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++)
            randomString.append(chars.charAt(generator.nextInt(chars.length())));
        return randomString.toString();
    }

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
}
