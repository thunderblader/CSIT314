package com.example.csit314.useradminview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csit314.R;


import java.util.ArrayList;

public class UserAdminRecyclerAdapter extends RecyclerView.Adapter<UserAdminRecyclerAdapter.MyViewHolder> {

    private ArrayList<UserAdminHelper> alist;

    public UserAdminRecyclerAdapter(ArrayList<UserAdminHelper> alist) {
        this.alist = alist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameText;
        private TextView numberText;
        private TextView userGrpText;

        public MyViewHolder(final View view) {
            super(view);
            nameText = view.findViewById(R.id.UserAdminSearchAdapterName);
            numberText = view.findViewById(R.id.UserAdminSearchAdapterNumber);
            userGrpText = view.findViewById(R.id.UserAdminSearchAdapterUserGrp);
        }
    }

    @NonNull
    @Override
    public UserAdminRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_admin_search_list_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdminRecyclerAdapter.MyViewHolder holder, int position) {
        String name = alist.get(position).getName();
        String number = alist.get(position).getNumber();
        String userGrp = alist.get(position).getUser_group();
        holder.nameText.setText(name);
        holder.numberText.setText(number);
        holder.userGrpText.setText(userGrp);
    }

    @Override
    public int getItemCount() {
        return alist.size();
    }
}
