package com.example.csit314.pharmacyview;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.csit314.R;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.Patient;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.ui.login.ChangePasswordActivity;
import com.example.csit314.ui.login.theLoginActivity;
import com.example.csit314.useradminview.UserAdminActivity;
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
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;


public class PharmacyActivity extends AppCompatActivity {
    private String user_Email;
    private String user_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        user_Password =(String) getIntent().getStringExtra("password");
        user_Email =(String) getIntent().getStringExtra("email");
    }

    public void onViewPrescriptionClick(View view) {
        Intent intent = new Intent(this,PharmacyViewPrescriptionActivity.class);
        ArrayList<Patient> PatientAlist = (ArrayList<Patient>) getIntent().getSerializableExtra("PatientArrayList");
        intent.putExtra("PatientArrayList",PatientAlist);
        intent.putExtra("email",user_Email);
        intent.putExtra("password",user_Password);
        startActivity(intent);
    }
/*
    public void onUpdatePrescriptionClick(View view) {
        Intent intent = new Intent(this,PharmacyUpdatePrescriptionActivity.class);
        ArrayList<Patient> PatientAlist = (ArrayList<Patient>) getIntent().getSerializableExtra("PatientArrayList");
        intent.putExtra("PatientArrayList",PatientAlist);
        intent.putExtra("email",user_Email);
        intent.putExtra("password",user_Password);
        startActivity(intent);
    }*/

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



