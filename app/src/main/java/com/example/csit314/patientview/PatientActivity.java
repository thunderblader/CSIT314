package com.example.csit314.patientview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
    }

    public void onPastPrescriptionClick(View view) {
        //Intent i = new Intent(this, PrescriptionActivity.class);
        //startActivity(i);
        int page = 2;
        Intent intent = new Intent(this,PrescriptionActivity.class);
        intent.putExtra("One", page);// One is your argument
        startActivity(intent);
    }

    public void onNewPrescriptionClick(View view) {
        int page = 2;
        Intent intent = new Intent(this,PrescriptionActivity.class);
        intent.putExtra("Two", page);// One is your argument
        startActivity(intent);
    }
}
