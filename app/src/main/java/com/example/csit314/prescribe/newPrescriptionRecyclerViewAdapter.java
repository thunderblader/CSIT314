package com.example.csit314.prescribe;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;

import java.util.List;


public class newPrescriptionRecyclerViewAdapter extends RecyclerView.Adapter<newPrescriptionRecyclerViewAdapter.MyViewHolder> {

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
            tv_name = (TextView)itemView.findViewById(R.id.new_prescription_name);
            tv_date = (TextView)itemView.findViewById(R.id.new_prescription_date);
            tv_amount = (TextView)itemView.findViewById(R.id.new_prescription_amount);
            tv_status = (TextView)itemView.findViewById(R.id.new_prescription_status);
        }
    }
    public newPrescriptionRecyclerViewAdapter(Context mContext, List<Prescription> mData)
    {
        this.mContext=mContext;
        this.mData=mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.new_prescription_list,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull newPrescriptionRecyclerViewAdapter.MyViewHolder holder, int position) {
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
