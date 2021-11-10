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
        import com.example.csit314.prescribe.Prescription;
import com.example.csit314.prescribe.newPrescriptionRecyclerViewAdapter;
import com.example.csit314.prescribe.pastPrescriptionRecyclerViewAdapter;

        import java.util.ArrayList;
        import java.util.List;
        import androidx.annotation.NonNull;

public class NewPrescriptionFragment extends Fragment {


    View v;
    private RecyclerView myrecyclerview;
    private List<Prescription> listPrescription;
    newPrescriptionRecyclerViewAdapter recyclerAdapter;
    public NewPrescriptionFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment2_layout, container, false);
        RecyclerView recyclerview = (RecyclerView) v.findViewById(R.id.fragment2_recyclerview);
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
        recyclerAdapter = new newPrescriptionRecyclerViewAdapter(getActivity(), listPrescription);
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
            if(p.getpName().toLowerCase().contains(text.toLowerCase()))
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
            if(p.getpStatus().toLowerCase().equals("in progress") )
                listPrescription.add(p);
        }

    }
}