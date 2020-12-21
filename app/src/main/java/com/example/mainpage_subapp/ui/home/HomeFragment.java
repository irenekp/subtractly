package com.example.mainpage_subapp.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private LinearLayout homeView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeView = root.findViewById(R.id.home_view);


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        FloatingActionButton add=getView().findViewById(R.id.addActivity);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getActivity(),addSubActivity.class);
                getActivity().startActivity(in);
            }
        });
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            AsyncGetSubs data = new AsyncGetSubs();
            data.execute();
        }
    }


    public class AsyncGetSubs extends AsyncTask<Void, View, Void> {


        @Override
        protected Void doInBackground(Void... voids) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("subscriptions");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            LayoutInflater li = LayoutInflater.from(getContext());
                            View cv = li.inflate(R.layout.cards_layout, null);

                            SubscriptionFB subscription = ds.getValue(SubscriptionFB.class);
                            String subId = subscription.id;

                            TextView name = (TextView) cv.findViewById(R.id.textViewName);
                            name.setText(subscription.name);

                            TextView price = (TextView) cv.findViewById(R.id.textPrice);
                            price.setText("₹" + subscription.price);

                            TextView shared = (TextView) cv.findViewById(R.id.textShared);
                            shared.setText("Shared by " + subscription.shared);

                            TextView cycle = (TextView) cv.findViewById(R.id.textBilling);
                            cycle.setText(subscription.billing_cycle + " day billing cycle");

                            ImageView icon;
                            icon = (ImageView) cv.findViewById(R.id.imgicon);
                            icon.setImageResource(subscription.icon);

                            ////calendar logic/////////

                            TextView daysLeft;
                            daysLeft = (TextView) cv.findViewById(R.id.textDaysLeft);
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

                            Date d1 = today.getTime();
                            Date d2 = c.getTime();
                            long diff = d2.getTime() - d1.getTime();
                            int days = (int) (diff / (1000 * 60 * 60 * 24));

                            String sdaysleft = Integer.toString(days);

                            daysLeft.setText(sdaysleft + " days left");

                            TextView plan = (TextView) cv.findViewById(R.id.textPlan);
                            plan.setText(subscription.plan);
                            //////////////////////////////

                            cv.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), SubscriptionDetails.class);
                                    intent.putExtra("subArr", subId);
                                    intent.putExtra("sdaysleft", sdaysleft);
                                    startActivity(intent);
                                }
                            });

                            publishProgress(cv);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

            return null;
        }

        @Override
        protected void onProgressUpdate(View... v) {
            homeView.addView(v[0]);
        }


    }
}
