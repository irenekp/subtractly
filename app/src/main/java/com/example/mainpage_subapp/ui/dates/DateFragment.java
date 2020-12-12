package com.example.mainpage_subapp.ui.dates;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mainpage_subapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class DateFragment extends Fragment {
    MaterialCalendarView widget;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date, container, false);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        widget=getActivity().findViewById(R.id.calendarView);
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        /**
        ArrayList<CalendarDay> dates=new ArrayList<>();
        dates.add(CalendarDay.from(2020,  12, 21));// year, month, date
        dates.add(CalendarDay.from(2020,  12, 13));// year, month, date
        dates.add(CalendarDay.from(2020,  12, 31));// year, month, date
        cal.addDecorator(new EventDecorator(Color.WHITE, dates));
        System.out.println("ggggggggggggggg");
         **/
    }
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalDate temp;
                temp = LocalDate.now().minusMonths(2);
                final ArrayList<CalendarDay> dates = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    final CalendarDay day = CalendarDay.from(2020,12,i+1);
                    dates.add(day);
                    temp = temp.plusDays(5);
                }
                System.out.println("AAAAAAAAAa");
                return dates;
            }

            return null;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}