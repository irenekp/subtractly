package com.example.mainpage_subapp.ui.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Query;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.roomdata.Subscription;
import com.example.mainpage_subapp.roomdata.SubscriptionDatabase;
import com.example.mainpage_subapp.roomdata.SubscriptionGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private LinearLayout homeView;



    private List<Subscription> dataSet;
    private List<SubscriptionGroup> memberList;
    private List<String> membersOfSub;

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


            SubscriptionDatabase subDb = SubscriptionDatabase.getInstance(getActivity());

            //appending data from dataset into the various textviews/imageviews;
            dataSet = subDb.subscriptionDao().getAllSubscriptions();
            memberList = subDb.subscriptionDao().getUsername();

            for (int i = 0; i < dataSet.size(); i++) {



                //init layout inflater and use cv to find all our views
                LayoutInflater li = LayoutInflater.from(getContext());
                View cv = li.inflate(R.layout.cards_layout, null);

                membersOfSub = subDb.subscriptionDao().getSubUsers(dataSet.get(i).getId());
                String[] memArr = membersOfSub.toArray(new String[0]);

                for(int x = 0; x<membersOfSub.size(); x++){
                    System.out.println(membersOfSub.get(x));
                }

                TextView name, shared, price, cycle, plan, daysLeft;
                ImageView icon;


                //creating a string array of data to pass to the next intent
                String sname, sshared, sprice, scycle, splan, sicon, sdaysleft;
                sname = dataSet.get(i).getName();
                sshared = "" + dataSet.get(i).getShared();
                sprice = "" + dataSet.get(i).getPrice();
                scycle = dataSet.get(i).getBilling_cycle() + "";
                splan = dataSet.get(i).getPlan() + " plan";
                sicon = "" + dataSet.get(i).getIcon();


                //DATE LOGIC///////////////////////
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                String startDate = dataSet.get(i).getStart_date();
                try {
                    //Setting the date to the given date
                    c.setTime(sdf.parse(startDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DAY_OF_MONTH, dataSet.get(i).getBilling_cycle());
                String endDate = sdf.format(c.getTime());

                Date d1 = today.getTime();
                Date d2 = c.getTime();
                long diff = d2.getTime() - d1.getTime();
                int days = (int) (diff / (1000 * 60 * 60 * 24));

                sdaysleft = "" + days;
                /////////////////////////////
                String sId = dataSet.get(i).getId();
                //MEMBERS AND SENDING MEMBER USERNAMES
                //we will be parsing the members List<SubscriptionGroup> object and creating an arraylist that we will then send
                //to the subdetails page via putextra

                final String[] subArr = {sname, sshared, sprice, scycle, splan, sicon, sdaysleft, startDate, sId};

                name = (TextView) cv.findViewById(R.id.textViewName);
                name.setText(sname);

                shared = (TextView) cv.findViewById(R.id.textShared);
                shared.setText("Shared by " + sshared);

                price = (TextView) cv.findViewById(R.id.textPrice);
                price.setText("₹" + sprice);

                cycle = (TextView) cv.findViewById(R.id.textBilling);
                cycle.setText(scycle + " day billing cycle");

                plan = (TextView) cv.findViewById(R.id.textPlan);
                plan.setText(splan);

                icon = (ImageView) cv.findViewById(R.id.imgicon);
                icon.setImageResource(Integer.parseInt(sicon));

                daysLeft = (TextView) cv.findViewById(R.id.textDaysLeft);
                daysLeft.setText(sdaysleft + " days left");


                cv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SubscriptionDetails.class);
                        intent.putExtra("subArr", subArr);
                        intent.putExtra("subMembers", memArr);
                        intent.putParcelableArrayListExtra("memberList", (ArrayList) memberList);
                        startActivity(intent);
                    }
                });

                publishProgress(cv);

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(View... v) {
            homeView.addView(v[0]);
        }


    }
}
