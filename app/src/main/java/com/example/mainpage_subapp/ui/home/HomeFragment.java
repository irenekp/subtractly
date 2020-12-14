package com.example.mainpage_subapp.ui.home;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.roomdata.Subscription;
import com.example.mainpage_subapp.roomdata.SubscriptionDatabase;
import com.example.mainpage_subapp.roomdata.SubscriptionGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {

    private LinearLayout homeView;
    private ProgressDialog p;

    private List<Subscription> dataSet;
    private List<SubscriptionGroup> trial;

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
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage("Getting subscriptions");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {




            //appending data from dataset into the various textviews/imageviews;
            SubscriptionDatabase subDb = SubscriptionDatabase.getInstance(getActivity());
            dataSet = subDb.subscriptionDao().getAllSubscriptions();


            for (int i = 0; i < dataSet.size(); i++) {

                //init layout inflater and use cv to find all our views
                LayoutInflater li = LayoutInflater.from(getContext());
                View cv = li.inflate(R.layout.cards_layout, null);

                TextView name, shared, price, cycle, plan;
                ImageView icon;

                //creating a string array of data to pass to the next intent
                String sname, sshared, sprice, scycle, splan, sicon;
                sname = dataSet.get(i).getName();
                sshared = "Shared by " + dataSet.get(i).getShared();
                sprice = "₹" + dataSet.get(i).getPrice();
                scycle = dataSet.get(i).getBilling_cycle() + " day billing cycle";
                splan = dataSet.get(i).getPlan() + " plan";
                sicon = ""+dataSet.get(i).getIcon();

                final String[] subArr = {sname, sshared, sprice, scycle, splan, sicon};

                name = (TextView) cv.findViewById(R.id.textViewName);
                name.setText(sname);

                shared = (TextView) cv.findViewById(R.id.textShared);
                shared.setText(sshared);

                price = (TextView) cv.findViewById(R.id.textPrice);
                price.setText(sprice);

                cycle = (TextView) cv.findViewById(R.id.textBilling);
                cycle.setText(scycle);

                plan = (TextView) cv.findViewById(R.id.textPlan);
                plan.setText(splan);

                icon = (ImageView) cv.findViewById(R.id.imgicon);
                icon.setImageResource(Integer.parseInt(sicon));


                cv.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), SubscriptionDetails.class);
                        intent.putExtra("subArr", subArr);
                        startActivity(intent);
                    }
                });

                publishProgress(cv);

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(View... v) {
            p.dismiss();
            homeView.addView(v[0]);
        }


    }
}
