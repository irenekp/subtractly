package com.example.mainpage_subapp.firebasedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.roomdata.Subscription;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import static android.content.ContentValues.TAG;

@IgnoreExtraProperties
public class SubscriptionFB {

    public String id;

    public String name;

    public String plan;

    public int price;

    public int shared;

    public int billing_cycle;

    public int icon;

    public String start_date;


    public SubscriptionFB() {

    }

    public SubscriptionFB(String id, String name, String plan, int price, int shared, int billing_cycle, int icon, String start_date) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.price = price;
        this.shared = shared;
        this.billing_cycle = billing_cycle;
        this.icon = icon;
        this.start_date = start_date;
    }


        public void insertSubscription(String subId, String name, String plan, int price, int shared, int billing_cycle, int icon, String start_date) {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            SubscriptionFB subToInsert = new SubscriptionFB(subId, name, plan, price, shared, billing_cycle, icon, start_date);
            database.child("subscriptions").child(subId).setValue(subToInsert);
        }

        public void populateDB(){
            SubscriptionFB obj = new SubscriptionFB();
            obj.insertSubscription("NetFam30", "Netflix", "Family", 899, 4, 30, R.drawable.netflix, "12/12/20");
            obj.insertSubscription("SpotDuo90", "Spotify", "Duo", 399, 2, 90, R.drawable.spotify, "06/11/2020");
            obj.insertSubscription("PrimeFam365", "PrimeVideo", "Family", 999, 4, 365, R.drawable.primevideo, "25/06/2020");
            obj.insertSubscription("NetInd30", "Netflix", "Individual", 129, 1, 30, R.drawable.netflix, "17/12/2020");
            obj.insertSubscription("SpotFam30", "Spotify", "Family", 199, 4, 30, R.drawable.spotify, "7/12/2020");

        }
}



