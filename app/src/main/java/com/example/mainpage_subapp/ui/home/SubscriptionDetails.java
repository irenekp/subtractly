package com.example.mainpage_subapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.example.mainpage_subapp.MainActivity;
import com.example.mainpage_subapp.firebasedata.MembersFB;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mainpage_subapp.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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

                    //delete subscription
                    TextView deleteSub = findViewById(R.id.deleteSub);
                    deleteSub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            subscription.deleteSubscription(subscription, subArr);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //DISPLAYING ALL MEMBERS VIA FIREBASE
        DatabaseReference memberRef = database.getReference("subscriptions");
        memberRef.keepSynced(true);

        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> membernames = new ArrayList<String>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    SubscriptionFB subscriptionFB = ds.getValue(SubscriptionFB.class);

                    //waiting for db ref to reach the right id
                    if (subArr.equals(subscriptionFB.id)) {

                        int share = subscriptionFB.price;

                        //defining the places we'll be saving these guys in
                        LinearLayout scr = findViewById(R.id.presentmembers);
                        LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                        View inflatedLayout1 = inflater.inflate(R.layout.entering_members, null, false);
                        EditText name = inflatedLayout1.findViewById(R.id.enterName);


                        //first, creating a list of all membernames
                        for (int i = 0; i < ds.child("members").getChildrenCount(); i++) {
                            if (subscriptionFB.members != null) {
                                membernames.add(subscriptionFB.members.get(i).member_name);
                            }
                        }

                        for (int x = 0; x < membernames.size(); x++) {
                            LayoutInflater inflater1 = LayoutInflater.from(SubscriptionDetails.this);
                            View inflatedLayout2 = inflater1.inflate(R.layout.member_entry, null, false);

                            TextView subName = (TextView) inflatedLayout2.findViewById(R.id.memberThingy);
                            TextView memshare = (TextView) inflatedLayout2.findViewById(R.id.membershare);
                            int mems = share / membernames.size();
                            String memberShare = Integer.toString(mems);
                            memshare.setText("₹" + memberShare);
                            subName.setText(membernames.get(x));
                            scr.addView(inflatedLayout2);
                        }

                        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_member);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                addMyMember();

                            }

                            private void addMyMember() {
                                LinearLayout scr = findViewById(R.id.presentmembers);
                                LayoutInflater inflater = LayoutInflater.from(SubscriptionDetails.this);
                                View inflatedLayout1 = inflater.inflate(R.layout.entering_members, null, false);
                                EditText name=inflatedLayout1.findViewById(R.id.enterName);
                                Button addMem=inflatedLayout1.findViewById(R.id.addMem);

                                addMem.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String memName = name.getText().toString();
                                        View inflatedLayout2 = inflater.inflate(R.layout.member_entry, null, false);
                                        TextView myName = inflatedLayout2.findViewById(R.id.memberThingy);
                                        myName.setText(memName);
                                        membernames.add(memName);
                                        //insert members into db for this particular subscriptionFB
                                        List<MembersFB> newListOfMembers = new ArrayList<MembersFB>();
                                        for (int x = 0; x < membernames.size(); x++) {
                                            newListOfMembers.add(new MembersFB(membernames.get(x)));
                                        }
                                        SubscriptionFB overwriteDB = new SubscriptionFB();
                                        overwriteDB.insertSubscription(subscriptionFB.id, subscriptionFB.name, subscriptionFB.plan, subscriptionFB.price, (subscriptionFB.shared+1), subscriptionFB.billing_cycle, subscriptionFB.icon, subscriptionFB.start_date, newListOfMembers);
                                        scr.removeAllViewsInLayout();
                                    }
                                });
                                scr.addView(inflatedLayout1);
                            }


                        });










                    }

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Something went wrong!");
            }
        });


    }
}