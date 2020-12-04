package com.example.mainpage_subapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mainpage_subapp.R;

public class SubscriptionDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);
        //SUB NAME
        String SubscriptionType="Netflix";
        //SUB IMAGE

        //CATEGORY
        String category="Category: Entertainment";
        TextView tv_category=findViewById(R.id.category);
        tv_category.setText(category);
        //PLAN_PRICE
        String plan_price="Family Plan | ₹800";
        TextView tv_plan_price=findViewById(R.id.plan_price);
        tv_plan_price.setText(plan_price);
        //DUE DATE
        String duedate="Due:09/10/2020";
        TextView tv_duedate=findViewById(R.id.duedate);
        tv_duedate.setText(duedate);
        //BILLING CYCLE
        String billingcycle="90 day billing cycle";
        TextView tv_billingcycle=findViewById(R.id.billingcycle);
        tv_billingcycle.setText(billingcycle);
        //Progress Bar
        int max=90;
        int progress=60;
        ProgressBar pb =findViewById(R.id.progressbar);
        pb.setMax(max);
        pb.setProgress(progress);
        //DAYS LEFT
        String daysleft=""+progress;
        TextView tv_daysleft=findViewById(R.id.daysleft);
        tv_daysleft.setText(daysleft);
        //NO OF MEMBERS
        String noofmembers="4/5";
        TextView tv_noofmembers=findViewById(R.id.noofmembers);
        tv_noofmembers.setText(noofmembers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(SubscriptionType);
        //ADD USER Functionality
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_member);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
                LinearLayout scr=findViewById(R.id.presentmembers);
                LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
                scr.addView(inflatedLayout1);
            }
        });
        LinearLayout scr=findViewById(R.id.presentmembers);
        LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
        View inflatedLayout = inflater.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout);

        LayoutInflater inflater1 = LayoutInflater.from(SubscriptionDetails.this);
        View inflatedLayout1 = inflater1.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout1);

        LayoutInflater inflater2 = LayoutInflater.from(SubscriptionDetails.this);
        View inflatedLayout2 = inflater2.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout2);

        LayoutInflater inflater3 = LayoutInflater.from(SubscriptionDetails.this);
        View inflatedLayout3 = inflater3.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout3);

    }
    @SuppressLint("ResourceAsColor")
    public void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.add_members_dialog, null))
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setTitle("Add Member");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Switch sharetype= alertDialog.findViewById(R.id.sharetype);
        final TextView sharelabel= alertDialog.findViewById(R.id.EnterAmt_control);
        final EditText shareAmt= alertDialog.findViewById(R.id.shareAmt);
        sharetype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    shareAmt.setVisibility(View.VISIBLE);
                    sharelabel.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    shareAmt.setVisibility(View.GONE);
                    sharelabel.setVisibility(View.GONE);
                }
            }
        });
    }
}

