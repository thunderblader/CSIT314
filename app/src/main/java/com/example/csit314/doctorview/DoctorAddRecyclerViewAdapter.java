package com.example.csit314.doctorview;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;
import com.example.csit314.patientview.Patient;
import com.example.csit314.prescribe.Prescription;

import java.util.ArrayList;
import java.util.List;


public class DoctorAddRecyclerViewAdapter extends RecyclerView.Adapter<DoctorAddRecyclerViewAdapter.MyViewHolder> {

    private List<Patient> patientData;
    private String password;
    private String email;
    private ArrayList<String> medications;
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout item_prescription;
        private TextView tv_patient_name;
        private TextView tv_patient_email;
        private TextView tv_patient_number;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_prescription = (LinearLayout)itemView.findViewById(R.id.doctor_add_item_id);
            tv_patient_name = (TextView)itemView.findViewById(R.id.doctor_add_patient_name);
            tv_patient_email = (TextView)itemView.findViewById(R.id.doctor_add_patient_email);
            tv_patient_number = (TextView)itemView.findViewById(R.id.doctor_add_patient_number);
        }
    }
    public DoctorAddRecyclerViewAdapter(List<Patient> patientData, String email, String password, ArrayList<String> medications)
    {
        this.patientData=patientData;
        this.password = password;
        this.email = email;
        this.medications = medications;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_add_list,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item_prescription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Intent intent;
                String patient_name = "";
                String patient_email = "";
                String patient_number = "";
                intent = new Intent(parent.getContext(), DoctorAddPrescriptionActivity.class);

                patient_name = patientData.get(vHolder.getAdapterPosition()).getName();
                patient_email = patientData.get(vHolder.getAdapterPosition()).getEmail();
                patient_number = patientData.get(vHolder.getAdapterPosition()).getNumber();

                intent.putExtra("patientname",patient_name);
                intent.putExtra("patientemail",patient_email);
                intent.putExtra("patientnumber",patient_number);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putStringArrayListExtra("medications",medications);
                ((Activity)parent.getContext()).finish();
                parent.getContext().startActivity(intent);
            }
        });

       return vHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull DoctorAddRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_patient_name.setText(patientData.get(position).getName());
        holder.tv_patient_email.setText(patientData.get(position).getEmail());
        holder.tv_patient_number.setText(patientData.get(position).getNumber());
    }
    @Override
    public int getItemCount() {
        return patientData.size();
    }

    public void filterList(ArrayList<Patient> filteredList)
    {
        patientData = filteredList;
        notifyDataSetChanged();
    }
}
