package com.example.csit314.prescribe.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.annotation.NonNull;

public class PastPrescriptionFragment extends Fragment{


    View v;
    private RecyclerView myrecyclerview;
    private List<Prescription> listPrescription;
    public PastPrescriptionFragment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment1_layout,container,false);
        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.fragment1_recyclerview);
        pastPrescriptionRecyclerViewAdapter recyclerAdapter = new pastPrescriptionRecyclerViewAdapter(getActivity(),listPrescription);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(recyclerAdapter);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listPrescription = new ArrayList<Prescription>();
        listPrescription = getArguments().getParcelableArrayList("ArrayList");
        //listPrescription.add(new Prescription("name1","2","3","4"));

        //listPrescription.add(new Prescription("name5","6","7","8"));

        //listPrescription.add(new Prescription("name9","10","11","12"));

        //listPrescription.add(new Prescription("name13","14","15","16"));

        //listPrescription.add(new Prescription("name17","18","19","20"));

    }
}
