package com.example.csit314.doctorview;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;
import com.example.csit314.patientview.Patient;
import com.example.csit314.prescribe.Prescription;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DoctorViewPrescriptionActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private List<Prescription> listPatientPrescription = new ArrayList<>();
    private List<Patient> listPatient;
    DoctorRecyclerViewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String password;
    private String email;
    private ArrayList<String> medications;

    public DoctorViewPrescriptionActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_view_prescription);
        populate();
        password =(String) getIntent().getStringExtra("password");
        email =(String) getIntent().getStringExtra("email");
        medications =(ArrayList<String>) getIntent().getStringArrayListExtra("medications");
        medications = (ArrayList<String>) getIntent().getExtras().getStringArrayList("medications");
        myrecyclerview = (RecyclerView)findViewById(R.id.doctor_view_recyclerview);
        getWindow().getDecorView().findViewById(R.id.doctor_view_recyclerview).invalidate();
        myrecyclerview.destroyDrawingCache();
        myrecyclerview.setVisibility(myrecyclerview.INVISIBLE);
        myrecyclerview.setVisibility(myrecyclerview.VISIBLE);
        myrecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new DoctorRecyclerViewAdapter(listPatientPrescription,listPatient,email,password,medications);
        myrecyclerview.setLayoutManager(mLayoutManager);
        EditText editText = findViewById(R.id.doctor_view_edittext);
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
                for (Prescription pres : listPatientPrescription) {
                    if (pres.getpName().toLowerCase().contains(text.toLowerCase())
                            || pres.getpStatus().toLowerCase().contains(text.toLowerCase())
                            || pres.getpDate().toLowerCase().contains(text.toLowerCase())
                            || pres.getpAmount().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(pres);
                    }
                }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        recyclerAdapter.filterList(filteredList);
    }
    private void populate() {
        listPatientPrescription = new ArrayList<Prescription>();
        listPatient = new ArrayList<Patient>();
        ArrayList<Prescription> allPrescriptionAList = new ArrayList<Prescription>();
        listPatient = (ArrayList<Patient>) getIntent().getSerializableExtra("DoctorArrayList");
        for (Patient p : listPatient) {
            for (Prescription pres : p.getAlist()) {
                listPatientPrescription.add(pres);
            }
        }
    }
}
