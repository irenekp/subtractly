package com.example.mainpage_subapp.ui.home;
/*
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mainpage_subapp.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;
        TextView textPrice;
        TextView textTimeleft;
        TextView textBilling;
        TextView textShared;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            this.textTimeleft = (TextView) itemView.findViewById(R.id.textTimeleft);
            this.textBilling = (TextView) itemView.findViewById(R.id.textBilling);
            this.textShared = (TextView) itemView.findViewById(R.id.textShared);
        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(HomeFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
        TextView textPrice = holder.textPrice;
        TextView textTimeLeft = holder.textTimeleft;
        TextView textBilling = holder.textBilling;
        TextView textShared = holder.textShared;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(dataSet.get(listPosition).getVersion());
        imageView.setImageResource(dataSet.get(listPosition).getImage());
        textPrice.setText(dataSet.get(listPosition).getPrice());
        String timeStr = dataSet.get(listPosition).getTimeLeft() + " days left";
        String billStr = dataSet.get(listPosition).getBilling() + " day billing cycle";
        String shareStr = "Shared with " + dataSet.get(listPosition).getShared();
        textTimeLeft.setText(timeStr);
        //checking if we need to change colour for less than 7 days
        if(Integer.parseInt(dataSet.get(listPosition).getTimeLeft()) < 7){
            textTimeLeft.setTextColor(Color.parseColor("#f44336"));
        }
        textBilling.setText(billStr);
        textShared.setText(shareStr);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

 */

