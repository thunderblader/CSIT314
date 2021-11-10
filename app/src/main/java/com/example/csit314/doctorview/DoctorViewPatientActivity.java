package com.example.csit314.doctorview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;
import com.example.csit314.patientview.Patient;
import com.example.csit314.prescribe.Prescription;

import java.util.ArrayList;
import java.util.List;

public class DoctorViewPatientActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private List<Patient> listPatient;
    DoctorAddRecyclerViewAdapter recyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String password;
    private String email;
    private ArrayList<String> medications;

    public DoctorViewPatientActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_view_patient);
        populate();
        password =(String) getIntent().getStringExtra("password");
        email =(String) getIntent().getStringExtra("email");
        medications =(ArrayList<String>) getIntent().getStringArrayListExtra("medications");
        medications = (ArrayList<String>) getIntent().getExtras().getStringArrayList("medications");
        myrecyclerview = (RecyclerView)findViewById(R.id.doctor_view_patient_recyclerview);
        getWindow().getDecorView().findViewById(R.id.doctor_view_patient_recyclerview).invalidate();
        myrecyclerview.destroyDrawingCache();
        myrecyclerview.setVisibility(myrecyclerview.INVISIBLE);
        myrecyclerview.setVisibility(myrecyclerview.VISIBLE);
        myrecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new DoctorAddRecyclerViewAdapter(listPatient,email,password,medications);
        myrecyclerview.setLayoutManager(mLayoutManager);
        EditText editText = findViewById(R.id.doctor_view_patient_edittext);
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
        ArrayList<Patient> filteredList = new ArrayList<>();

        for (Patient p : listPatient) {
            if (p.getName().toLowerCase().contains(text.toLowerCase())
                    || p.getNumber().toLowerCase().contains(text.toLowerCase())
                    || p.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(p);
            }
        }
        recyclerAdapter.filterList(filteredList);
    }
    private void populate() {
        listPatient = new ArrayList<Patient>();
        listPatient = (ArrayList<Patient>) getIntent().getSerializableExtra("DoctorArrayList");
    }
}
