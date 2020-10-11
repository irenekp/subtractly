package com.example.mainpage_subapp.ui.home;

import android.app.AlertDialog;
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
import android.widget.ImageView;
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
        ImageView image= (ImageView)findViewById(R.id.sub_logo);
        image.setImageResource(R.drawable.netflix);
        //CATEGORY
        String category="Category: Entertainment";
        TextView tv_category=findViewById(R.id.category);
        tv_category.setText(category);
        //PLAN_PRICE
        String plan_price="Family Plan|₹ 800";
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void showCustomDialog() {
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