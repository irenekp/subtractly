package com.example.mainpage_subapp.ui.dates;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;
import com.example.mainpage_subapp.ui.home.SubscriptionDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DateFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("subscriptions");
        List<Calendar> calendars = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    SubscriptionFB subscription = ds.getValue(SubscriptionFB.class);

                    System.out.println(subscription.start_date);

                    Calendar calendar = Calendar.getInstance();

                    //calculating enddate and storing in a string
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
                    int day, month, year;
                    day = c.get(Calendar.DATE);
                    month = c.get(Calendar.MONTH);
                    year = c.get(Calendar.YEAR);
                    System.out.println(subscription.name + ", " + day + ", " + month + ", " + year);

                    calendar.set(year, month, day);
                    events.add(new EventDay(calendar, getResources().getDrawable(subscription.icon)));
                    calendars.add(calendar);

                    /////////

                    CalendarView calendarView = getActivity().findViewById(R.id.calendarView);
                    calendars.add(calendar);
                    calendarView.setHighlightedDays(calendars);
                    calendarView.setEvents(events);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}