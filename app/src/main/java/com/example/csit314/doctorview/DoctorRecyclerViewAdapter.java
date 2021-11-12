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
import com.example.csit314.pharmacyview.PharmacyUpdatePrescriptionActivity;
import com.example.csit314.prescribe.Prescription;

import java.util.ArrayList;
import java.util.List;


public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter<DoctorRecyclerViewAdapter.MyViewHolder> {

    private List<Patient> patientData;
    private List<Prescription> prescriptionData;
    private String password;
    private String email;
    private ArrayList<String> medications;
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout item_prescription;
        private TextView tv_patient_name;
        private TextView tv_patient_email;
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_amount;
        private TextView tv_status;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_prescription = (LinearLayout)itemView.findViewById(R.id.doctor_view_item_id);
            tv_patient_name = (TextView)itemView.findViewById(R.id.doctor_view_patient_name);
            tv_patient_email = (TextView)itemView.findViewById(R.id.doctor_view_patient_email);
            tv_name = (TextView)itemView.findViewById(R.id.doctor_view_prescription_name);
            tv_date = (TextView)itemView.findViewById(R.id.doctor_view_prescription_date);
            tv_amount = (TextView)itemView.findViewById(R.id.doctor_view_prescription_amount);
            tv_status = (TextView)itemView.findViewById(R.id.doctor_view_prescription_status);
        }
    }
    public DoctorRecyclerViewAdapter(List<Prescription> prescriptionData, List<Patient> patientData, String email, String password,ArrayList<String> medications)
    {
        this.prescriptionData=prescriptionData;
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
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_view_list,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        vHolder.item_prescription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Toast.makeText(parent.getContext(), "Test Click"+String.valueOf(vHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                final Intent intent;
                String patient_name = "";
                String patient_email = "";
                String patient_number = "";
                intent = new Intent(parent.getContext(), DoctorUpdatePrescriptionActivity.class);
                intent.putExtra("prescriptionname",prescriptionData.get(vHolder.getAdapterPosition()).getpName());
                intent.putExtra("prescriptiondate",prescriptionData.get(vHolder.getAdapterPosition()).getpDate());
                intent.putExtra("prescriptionstatus",prescriptionData.get(vHolder.getAdapterPosition()).getpStatus());
                intent.putExtra("prescriptionamount",prescriptionData.get(vHolder.getAdapterPosition()).getpAmount());
                intent.putExtra("prescriptionid",prescriptionData.get(vHolder.getAdapterPosition()).getpID());
                for(Patient p : patientData)
                    for(Prescription pres : p.getAlist())
                    {
                        if(prescriptionData.get(vHolder.getAdapterPosition()).equals(pres))
                        {
                            patient_name = p.getName();
                            patient_email = p.getEmail();
                            patient_number = p.getNumber();
                        }
                    }
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
    public void onBindViewHolder(@NonNull DoctorRecyclerViewAdapter.MyViewHolder holder, int position) {

        for(Patient p : patientData)
            for(Prescription pres : p.getAlist())
            {
                if(prescriptionData.get(position).equals(pres))
                {
                    holder.tv_patient_name.setText(p.getName());
                    holder.tv_patient_email.setText(p.getEmail());
                }
            }
        holder.tv_name.setText(prescriptionData.get(position).getpName());
        holder.tv_date.setText(prescriptionData.get(position).getpDate());
        holder.tv_amount.setText(prescriptionData.get(position).getpAmount());
        holder.tv_status.setText(prescriptionData.get(position).getpStatus());
    }
    @Override
    public int getItemCount() {
        return prescriptionData.size();
    }

    public void filterList(ArrayList<Prescription> filteredList)
    {
        prescriptionData = filteredList;
        notifyDataSetChanged();
    }
    public void filterPatientList(ArrayList<Patient> filteredList)
    {
        patientData = filteredList;
        notifyDataSetChanged();
    }
}
