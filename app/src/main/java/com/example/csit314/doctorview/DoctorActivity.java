package com.example.csit314.doctorview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.csit314.R;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.Patient;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.PrescriptionActivity;
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

import java.util.ArrayList;
public class DoctorActivity extends AppCompatActivity {
    private String user_Email;
    private String user_Password;
    private ArrayList<String> medications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        user_Password =(String) getIntent().getStringExtra("password");
        user_Email =(String) getIntent().getStringExtra("email");
        medications = (ArrayList<String>) getIntent().getExtras().getStringArrayList("medications");
        medications = (ArrayList<String>) getIntent().getStringArrayListExtra("medications");
    }
    public void onAddPrescriptionClick(View view) {
        Intent intent = new Intent(this,DoctorViewPatientActivity.class);
        ArrayList<Patient> PatientAlist = (ArrayList<Patient>) getIntent().getSerializableExtra("DoctorArrayList");
        intent.putExtra("DoctorArrayList",PatientAlist);
        intent.putExtra("email",user_Email);
        intent.putExtra("password",user_Password);
        intent.putStringArrayListExtra("medications",medications);
        intent.putExtra("medications",medications);
        startActivity(intent);
    }

    public void onViewPrescriptionClick(View view) {
        Intent intent = new Intent(this,DoctorViewPrescriptionActivity.class);
        ArrayList<Patient> PatientAlist = (ArrayList<Patient>) getIntent().getSerializableExtra("DoctorArrayList");
        intent.putExtra("DoctorArrayList",PatientAlist);
        intent.putExtra("email",user_Email);
        intent.putExtra("password",user_Password);
        intent.putStringArrayListExtra("medications",medications);
        startActivity(intent);
    }
}
