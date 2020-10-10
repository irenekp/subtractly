package com.example.mainpage_subapp.ui.home;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}