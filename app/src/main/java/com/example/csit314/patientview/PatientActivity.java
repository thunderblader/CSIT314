package com.example.csit314.patientview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

import com.example.csit314.R;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.ui.login.ChangePasswordActivity;
import com.example.csit314.ui.login.theLoginActivity;
import com.example.csit314.useradminview.UserAdminActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PatientActivity extends AppCompatActivity {

    Bundle stringArrayList = getIntent().getExtras();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
    }

    public void onPastPrescriptionClick(View view) {
        int page = 2;
        Intent intent = new Intent(this,PrescriptionActivity.class);
        intent.putExtra("One", page);// One is your argument
        ArrayList<String> key = stringArrayList.getStringArrayList("key");
        ArrayList<String> value = stringArrayList.getStringArrayList("value");
        intent.putStringArrayListExtra("key",key);
        intent.putStringArrayListExtra("value",value);
        startActivity(intent);
    }

    public void onNewPrescriptionClick(View view) {
        int page = 2;
        Intent intent = new Intent(this,PrescriptionActivity.class);
        intent.putExtra("Two", page);// One is your argument
        ArrayList<String> key = stringArrayList.getStringArrayList("key");
        ArrayList<String> value = stringArrayList.getStringArrayList("value");
        intent.putStringArrayListExtra("key",key);
        intent.putStringArrayListExtra("value",value);
        startActivity(intent);
    }

    public void emailOnClick(View view) {
        String mEmail = "khoowh1996@gmail.com";
        String mSubject = "test new email dev314sim@";
        String mMessage = "Test Message";

        SendMail sendMail = new SendMail(this,mEmail,mSubject,mMessage);
        sendMail.execute();
    }
    public void changePasswordOnClick(View view)
    {
        Intent i = new Intent( this, ChangePasswordActivity.class);
        startActivity(i);
    }
    public void logoutOnClick(View view)
    {
        Intent i = new Intent( this, theLoginActivity.class);
        startActivity(i);
    }
}
