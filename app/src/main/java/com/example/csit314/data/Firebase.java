package com.example.csit314.data;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

public class Firebase {

    private FirebaseUser current_User;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, user_ref, update_ref;
    private DataSnapshot dataSnapshotReference;

    private String the_name, the_number, the_userType, the_email;

    private boolean signed_in, firebase_ready, database_ready = false;
    private CountDownTimer firebase_timer;

    private Activity activityReference;

    private Map<String, String> the_map;

    public String getThe_number() { return the_number; }
    public String getThe_name() { return the_name; }
    public String getThe_userType() { return the_userType; }
    public String getThe_userData(String user_email, String data_name) { return dataSnapshotReference.child("User_Group").child(user_email).child(data_name).getValue().toString(); }
    public Boolean is_database_ready()
    {
        return database_ready;
    }
    public Firebase()
    {

    }

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
            firebase_ready = true;
        if(!firebase_ready)
            start_firebase();
    }

    private void complete_signin(String email)
    {
        signed_in = true;
        the_email = email;
        user_ref = mDatabase.child("User_Group").child(convert_email(the_email));
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
                            complete_signin(email);
                            setData(number, name, user_type);

                            fetch_database(mDatabase);
                        }
                        else
                        {
                            Toast.makeText(activityReference.getApplicationContext(), "Email has been taken", Toast.LENGTH_SHORT).show();
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
                        complete_signin(email);
                        fetch_database(mDatabase);
                    }
                    else
                    {
                        Toast.makeText(activityReference.getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void signout() { mAuth.signOut(); }

    private void setData(String number, String name, String user_type)
    {

        mDatabase.child(user_type).child(convert_email(the_email)).child("name").setValue(name);
        mDatabase.child(user_type).child(convert_email(the_email)).child("number").setValue(number);

        mDatabase.child(user_type).child(the_email).setValue(name);
        mDatabase.child(user_type).child(the_email).setValue(number);
       // user_ref.child("prescription").setValue("null");
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
                the_map = (Map) dataSnapshot.child("User_Group").child(convert_email(the_email)).getValue();
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
        the_number = dataSnapshotReference.child("User_Group").child(convert_email(the_email)).child("number").getValue().toString();
        the_name = dataSnapshotReference.child("User_Group").child(convert_email(the_email)).child("name").getValue().toString();
        the_userType = dataSnapshotReference.child("User_Group").child(convert_email(the_email)).child("user_type").getValue().toString();

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

    public void change_password(String password)
    {
        FirebaseUser user = mAuth.getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                    Toast.makeText(activityReference.getApplicationContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(activityReference.getApplicationContext(), "Password not changed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Map<String, String> searchUser(String user_email)
    {
        Map<String, String> the_user = (Map) dataSnapshotReference.child("User_Group").child(convert_email(user_email)).getValue();
        return the_user;
    }
    public Map<String, Object> searchUserGroup()
    {
        Map<String, Object> the_user = (Map) dataSnapshotReference.child("User_Group").getValue();
        return the_user;
    }
    public void updateUser(String user_email, String number, String name, String user_type)
    {
        update_ref = mDatabase.child("User_Group").child(convert_email(user_email));
        update_ref.child("number").setValue(number);
        update_ref.child("name").setValue(name);
        update_ref.child("user_type").setValue(user_type);
        update_ref = mDatabase.child(user_type).child(convert_email(user_email));
        update_ref.child("number").setValue(number);
        update_ref.child("name").setValue(name);
    }

    public Map<String, Object> searchUser_type(String user_type)
    {
        Map<String, Object> the_usertype = (Map) dataSnapshotReference.child(user_type).getValue();
        return the_usertype;
    }

    public Map<String, String> get_prescription(String the_prescription)
    {
        Map<String, String> prescription = (Map) dataSnapshotReference.child("medication").child(the_prescription).getValue();
        return prescription;
    }

    public Map<String, String> get_pastprescription(String user_email)
    {
        Map<String, String> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(convert_email(user_email)).child("prescription").getValue();
        //Map<String, String> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(the_email).getValue();
        return my_prescription;
    }
    public Map<String, Object> get_pastprescription2(String user_email)
    {
        Map<String, Object> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(convert_email(user_email)).child("prescription").getValue();
        //Map<String, String> my_prescription = (Map) dataSnapshotReference.child("User_Group").child(the_email).getValue();
        return my_prescription;
    }
    public void add_prescription(String patient_email, String data) { mDatabase.child("User_Group").child(the_email).child("prescription").child(get_time()).setValue(data); }

    public void edit_prescription(String patient_email, String data, String the_time) { mDatabase.child("User_Group").child(the_email).child("prescription").child(the_time).setValue(data); }
    public void edit_prescription2(String patient_email, String data, String prescriptionID) {
        mDatabase.child("User_Group").child(convert_email(patient_email)).child("prescription").child(prescriptionID).child("status").setValue(data);
    }
    private String convert_email(String email) { return email.replace('.', '_'); }

    public String get_time()
    {
        SimpleDateFormat the_format = new SimpleDateFormat("dd-MM-yyyy");
        String time_format = the_format.format(Calendar.getInstance().getTime());
        return time_format;
    }
}
