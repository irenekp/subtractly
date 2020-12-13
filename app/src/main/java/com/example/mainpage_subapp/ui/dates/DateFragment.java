package com.example.mainpage_subapp.ui.dates;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.mainpage_subapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<EventDay> events = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020,Calendar.DECEMBER,14);
        //if multiple
        Drawable[] x=new Drawable[]{getResources().getDrawable(R.drawable.netflix),getResources().getDrawable(R.drawable.primevideo)};
        LayerDrawable y=new LayerDrawable(x);
        events.add(new EventDay(calendar,y));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2020,Calendar.DECEMBER,17);
        events.add(new EventDay(calendar2, R.drawable.primevideo));
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2020,Calendar.DECEMBER,14);
        events.add(new EventDay(calendar3, R.drawable.primevideo));
        CalendarView calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
        List<Calendar> calendars = new ArrayList<>();
        calendars.add(calendar);
        calendars.add(calendar2);
        calendars.add(calendar3);
        calendarView.setHighlightedDays(calendars);
        calendarView.setEvents(events);
    }
}