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
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;
import com.example.csit314.databinding.ActivityLoginBinding;
import com.example.csit314.email.SendMail;
import com.example.csit314.patientview.Patient;
import com.example.csit314.patientview.PatientActivity;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.prescribe.pastPrescriptionRecyclerViewAdapter;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class PharmacyViewPrescriptionActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private List<Prescription> listPatientPrescription = new ArrayList<>();
    private List<Patient> listPatient;
    PharmacyRecyclerViewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String password;
    private String email;
    public PharmacyViewPrescriptionActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pharmacy_view_prescription);
        populate();
        password =(String) getIntent().getStringExtra("password");
        email =(String) getIntent().getStringExtra("email");
        myrecyclerview = (RecyclerView)findViewById(R.id.pharmacy_view_recyclerview);
        getWindow().getDecorView().findViewById(R.id.pharmacy_view_recyclerview).invalidate();
        myrecyclerview.destroyDrawingCache();
        myrecyclerview.setVisibility(myrecyclerview.INVISIBLE);
        myrecyclerview.setVisibility(myrecyclerview.VISIBLE);
        myrecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new PharmacyRecyclerViewAdapter(listPatientPrescription,listPatient,email,password);
        myrecyclerview.setLayoutManager(mLayoutManager);
        EditText editText = findViewById(R.id.pharmacy_view_edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }

        });
        myrecyclerview.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }
    private void filter(String text) {
        ArrayList<Prescription> filteredList = new ArrayList<>();
        ArrayList<Patient> filteredPatientList = new ArrayList<>();
        try {
            for (Patient p : listPatient){
                for (Prescription pres : p.getAlist()) {
                    if (p.getName().toLowerCase().contains(text.toLowerCase())
                            || p.getEmail().toLowerCase().contains(text.toLowerCase())
                            || p.getNumber().toLowerCase().contains(text.toLowerCase())
                            || pres.getpName().toLowerCase().contains(text.toLowerCase())
                            || pres.getpStatus().toLowerCase().contains(text.toLowerCase())
                            || pres.getpDate().toLowerCase().contains(text.toLowerCase())
                            || pres.getpAmount().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(pres);
                        filteredPatientList.add(p);
                    }
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        recyclerAdapter.filterList(filteredList,filteredPatientList);
    }
    private void populate() {
        listPatientPrescription = new ArrayList<Prescription>();
        listPatient = new ArrayList<Patient>();
        ArrayList<Prescription> allPrescriptionAList = new ArrayList<Prescription>();
        listPatient = (ArrayList<Patient>) getIntent().getSerializableExtra("PatientArrayList");
        for (Patient p : listPatient) {
            for (Prescription pres : p.getAlist()) {
                listPatientPrescription.add(pres);
            }
        }
    }
}
