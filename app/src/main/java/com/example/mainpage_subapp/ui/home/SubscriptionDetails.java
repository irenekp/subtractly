package com.example.mainpage_subapp.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.mainpage_subapp.firebasedata.MembersFB;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;
import com.example.mainpage_subapp.roomdata.Subscription;
import com.example.mainpage_subapp.roomdata.SubscriptionDatabase;
import com.example.mainpage_subapp.roomdata.SubscriptionGroup;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainpage_subapp.R;

import com.example.mainpage_subapp.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static android.content.ContentValues.TAG;

public class SubscriptionDetails extends AppCompatActivity {

    public LinearLayout scr;
    public LayoutInflater inflater;
    public ViewGroup container;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String subArr = getIntent().getStringExtra("subArr");
        String sdaysleft = getIntent().getStringExtra("sdaysleft");
        //String[] members = getIntent().getStringArrayExtra("subMembers");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("subscriptions");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    SubscriptionFB subscription = dataSnapshot.child(subArr).getValue(SubscriptionFB.class);

                    TextView tv_category = findViewById(R.id.category);
                    tv_category.setText(subscription.plan);

                    TextView tv_plan_price = findViewById(R.id.plan_price);
                    tv_plan_price.setText("₹" + subscription.price);

                    TextView tv_billingcycle = findViewById(R.id.billingcycle);
                    tv_billingcycle.setText(subscription.billing_cycle + " day billing cycle");

                    ImageView sublogo = (ImageView) findViewById(R.id.detailsLogo);
                    sublogo.setImageResource(subscription.icon);

                    TextView tv_daysleft = findViewById(R.id.daysleft);
                    tv_daysleft.setText(sdaysleft);

                    //DUE DATE
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    Calendar today = Calendar.getInstance();
                    today.set(Calendar.HOUR_OF_DAY, 0);
                    String startDate = subscription.start_date;
                    try {
                        //Setting the date to the given date
                        c.setTime(sdf.parse(startDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c.add(Calendar.DAY_OF_MONTH, subscription.billing_cycle);
                    String endDate = sdf.format(c.getTime());
                    TextView tv_duedate = findViewById(R.id.duedate);
                    tv_duedate.setText("Due: " + endDate);

                }
            }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });


        //DISPLAYING ALL MEMBERS VIA FIREBASE
        DatabaseReference memberRef = database.getReference(subArr);
        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    LinearLayout scr = findViewById(R.id.presentmembers);
                    LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                    View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
                    TextView subName = (TextView) inflatedLayout1.findViewById(R.id.memberThingy);
                    scr.addView(inflatedLayout1);

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*
        //SUB NAME
        String SubscriptionType =
        //CATEGORY
        String category = subArr[4];
        TextView tv_category = findViewById(R.id.category);
        tv_category.setText(category);
        //PLAN_PRICE
        String plan_price = subArr[2];
        TextView tv_plan_price = findViewById(R.id.plan_price);
        tv_plan_price.setText("₹" + plan_price);
        //DUE DATE
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        String startDate = subArr[7];
        try {
            //Setting the date to the given date
            c.setTime(sdf.parse(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(subArr[3]));
        String endDate = sdf.format(c.getTime());
        TextView tv_duedate = findViewById(R.id.duedate);
        tv_duedate.setText("Due: " + endDate);
        //BILLING CYCLE
        TextView tv_billingcycle = findViewById(R.id.billingcycle);
        tv_billingcycle.setText("" + subArr[3] + " day billing cycle");
        //LOGO
        ImageView sublogo = (ImageView) findViewById(R.id.detailsLogo);
        sublogo.setImageResource(Integer.parseInt(subArr[5]));
        //DAYS LEFT
        String daysleft = "" + subArr[6];
        TextView tv_daysleft = findViewById(R.id.daysleft);
        tv_daysleft.setText(daysleft);
        //NO OF MEMBERS
        String noofmembers = subArr[1];
        TextView tv_noofmembers = findViewById(R.id.noofmembers);
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

                        addMyMember(members, subArr);

                /*
                LinearLayout scr = findViewById(R.id.presentmembers);
                LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
                scr.addView(inflatedLayout1);


            }

        });


        for(int i = 0; i < members.length; i++){
            LinearLayout scr = findViewById(R.id.presentmembers);
            LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
            View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
            TextView subName = (TextView)inflatedLayout1.findViewById(R.id.memberThingy);
            subName.setText(members[i]);
            TextView memshare = (TextView)inflatedLayout1.findViewById(R.id.membershare);
            String share = ""+ (Integer.parseInt(subArr[2])/members.length);
            memshare.setText("₹"+share);
            scr.addView(inflatedLayout1);
        }

    }




public void addMyMember(String[] members, String[] subArr){

    LinearLayout scr = findViewById(R.id.presentmembers);
    LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
    View inflatedLayout1 = inflater.inflate(R.layout.entering_members, null, false);
    EditText name=inflatedLayout1.findViewById(R.id.enterName);
    Button addMem=inflatedLayout1.findViewById(R.id.addMem);
    addMem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String memName=name.getText().toString();
            View inflatedLayout2 = inflater.inflate(R.layout.member_entry, null, false);
            TextView myName=inflatedLayout2.findViewById(R.id.memberThingy);
            myName.setText(memName);
            List<String> memList = new ArrayList();
            Collections.addAll(memList, members);
            memList.add(memName);
            String[] updatedMembers = memList.toArray(new String[0]);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // Insert Data
                    SubscriptionDatabase.getInstance(getApplicationContext()).subscriptionDao().insertUser(new SubscriptionGroup(subArr[8], memName));

                }
            });

            AsyncTask.execute(new Runnable(){
                @Override
                public void run(){
                    List<String> x = SubscriptionDatabase.getInstance(getApplicationContext()).subscriptionDao().getSubUsers(subArr[8]);
                    for(int y = 0; y<x.size(); y++){
                        System.out.println(x.get(y));
                    }
                }
            });
            scr.removeAllViewsInLayout();
            refreshScreen(updatedMembers, subArr);
        }
    });
    scr.addView(inflatedLayout1);
}

public void refreshScreen(String[] members, String[] subArr){
for(int i = 0; i < members.length; i++){
        LinearLayout scr = findViewById(R.id.presentmembers);
        LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
        View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
        TextView subName = (TextView)inflatedLayout1.findViewById(R.id.memberThingy);
        subName.setText(members[i]);
        TextView memshare = (TextView)inflatedLayout1.findViewById(R.id.membershare);
        String share = ""+ (Integer.parseInt(subArr[2])/members.length);
        memshare.setText("₹"+share);
        scr.addView(inflatedLayout1);
    }
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
                        LinearLayout scr = findViewById(R.id.presentmembers);
                        LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                        View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
                        scr.addView(inflatedLayout1);
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
        //Switch sharetype = alertDialog.findViewById(R.id.sharetype);
        /*
        final TextView sharelabel = alertDialog.findViewById(R.id.EnterAmt_control);
       final EditText shareAmt = alertDialog.findViewById(R.id.shareAmt);
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

         */
                }
            }

