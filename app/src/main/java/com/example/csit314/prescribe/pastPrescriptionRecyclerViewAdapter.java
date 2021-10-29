package com.example.csit314.prescribe;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csit314.R;
import com.example.csit314.data.Firebase;
import com.example.csit314.useradminview.UserAdminSearchList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class pastPrescriptionRecyclerViewAdapter extends RecyclerView.Adapter<pastPrescriptionRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Prescription> mData;
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_name;
        private TextView tv_date;
        private TextView tv_amount;
        private TextView tv_status;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv_name = (TextView)itemView.findViewById(R.id.past_prescription_name);
            tv_date = (TextView)itemView.findViewById(R.id.past_prescription_date);
            tv_amount = (TextView)itemView.findViewById(R.id.past_prescription_amount);
            tv_status = (TextView)itemView.findViewById(R.id.past_prescription_status);
        }
    }
    public pastPrescriptionRecyclerViewAdapter(Context mContext, List<Prescription> mData)
    {
        this.mContext=mContext;
        this.mData=mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.past_prescription_list,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull pastPrescriptionRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getpName());
        holder.tv_date.setText(mData.get(position).getpDate());
        holder.tv_amount.setText(mData.get(position).getpAmount());
        holder.tv_status.setText(mData.get(position).getpStatus());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
