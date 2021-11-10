package com.example.csit314.prescribe.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.PrescriptionActivity;
import com.example.csit314.prescribe.pastPrescriptionRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class PastPrescriptionFragment extends Fragment{


    View v;
    private List<Prescription> listPrescription;
    pastPrescriptionRecyclerViewAdapter recyclerAdapter;
    public PastPrescriptionFragment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment1_layout,container,false);
        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.fragment1_recyclerview);
        EditText editText = v.findViewById(R.id.edittext);
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

        recyclerAdapter = new pastPrescriptionRecyclerViewAdapter(getActivity(),listPrescription);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(recyclerAdapter);
        return v;
    }
    private void filter(String text)
    {
        ArrayList<Prescription> filteredList = new ArrayList<>();

        for(Prescription p : listPrescription)
        {
            if(p.getpName().toLowerCase().contains(text.toLowerCase())
                    || p.getpStatus().toLowerCase().contains(text.toLowerCase())
                    || p.getpDate().toLowerCase().contains(text.toLowerCase())
                    || p.getpAmount().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(p);
            }
        }
        recyclerAdapter.filterList(filteredList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPrescription = new ArrayList<Prescription>();
        ArrayList<Prescription> allPrescriptionAList = new ArrayList<Prescription>();
        allPrescriptionAList = getArguments().getParcelableArrayList("PrescriptionArrayList");
        for(Prescription p : allPrescriptionAList)
        {
            if(p.getpStatus().toLowerCase().equals("completed"))
                listPrescription.add(p);

        }

    }
}
