package com.example.mainpage_subapp.ui.analytics;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalyticsFragment extends Fragment {

    private AnalyticsViewModel analyticsViewModel;
    List<String>subs;
    List<Integer>no_mems;
    List<String>memNames;
    List<Integer>money;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_analytics, container, false);

        //DATABASE RELATED
        /*
        Quick notes:
        1) All subscriptions are now created with a "you" member automatically.
        2) To find this view member - access the subscription.members as a List<MembersFB> and iterate over it.
        3) Unfortunately, the price per subscription is still not an actual attribute and is derived from the number of members and subscription price
           I'll get around to fixing this soon, sorry
        4)
         */
         //will be used to calculate logged in users final expenditure

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("subscriptions");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subs=new ArrayList<>();
                no_mems=new ArrayList<>();
                memNames=new ArrayList<>();
                money= Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
                int end_Graph=Calendar.getInstance().get(Calendar.MONTH);
                int cur_year=Calendar.getInstance().get(Calendar.YEAR);
                for(DataSnapshot ds : snapshot.getChildren()) {
                    SubscriptionFB subscription = ds.getValue(SubscriptionFB.class);
                    //data in subscription can be accessed as seen below:
                    //System.out.println(subscription.name + ", " + subscription.plan); //etc
                    subs.add(subscription.service_type);
                    String startDate = subscription.start_date;
                    //creating a list of members that you can access
                    List<String> membernames = new ArrayList<String>();
                    for (int i = 0; i < ds.child("members").getChildrenCount(); i++) {
                        if (subscription.members != null) {
                            membernames.add(subscription.members.get(i).member_name);
                        }
                    }
                    //total price of this subscription
                    int priceOfSub = subscription.price;

                    //each members share can be calculated by dividing by total number of members
                    int pricePerMember = priceOfSub/membernames.size();
                    try {
                        //Setting the date to the given date
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        c.setTime(sdf.parse(startDate));
                        int startMonth=0;
                        if(c.get(Calendar.YEAR)==cur_year){
                            startMonth=c.get(Calendar.MONTH);
                        }
                        for(int mth=startMonth;mth<=end_Graph;mth++){
                            money.set(mth,money.get(mth)+pricePerMember);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int totalSpent = 0;
                    //now, you can calculate total cash used by "you" member like so:
                    for(int x = 0; x<membernames.size(); x++){
                        memNames.add(membernames.get(x));
                        if(membernames.get(x).equals("You")){
                            totalSpent += pricePerMember;
                        }
                    }
                    no_mems.add(membernames.size());
                    //System.out.println(membernames.size()+"|");
                    //System.out.println(totalSpent);
                }
                System.out.println("subs:"+ subs.toString());
                System.out.println("no_mems:"+ no_mems.toString());
                System.out.println("memNames:"+ memNames.toString());
                PieChart categoryChart =(PieChart)getActivity().findViewById(R.id.genrechart);
                List<String>names=new ArrayList<>();
                List<Integer>values=new ArrayList<>();
                Set<String> distinct = new HashSet<>(subs);
                for (String s: distinct) {
                    names.add(s);
                    values.add(Collections.frequency(names, s));
                }
                initPieChart(categoryChart,"Usage by Category",names,values);
                names=new ArrayList<>();
                values=new ArrayList<>();
                distinct = new HashSet<>(memNames);
                for (String s: distinct) {
                    names.add(s);
                    values.add(Collections.frequency(names, s));
                }
                PieChart colabChart=(PieChart)getActivity().findViewById(R.id.colabchart);
                initPieChart(colabChart,"% Shared with Friends",names,values);
                LineChart costChart=(LineChart)getActivity().findViewById(R.id.costchart);
                initLineChart(costChart,money);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void initLineChart(LineChart chart, List<Integer>money){
        final HashMap<Integer,String> Months = new HashMap<Integer, String>();
        Months.put(0,"Jan");
        Months.put(1,"Feb");
        Months.put(2,"Mar");
        Months.put(3,"Apr");
        Months.put(4,"May");
        Months.put(5,"Jun");
        Months.put(6,"Jul");
        Months.put(7,"Aug");
        Months.put(8,"Sept");
        Months.put(9,"Oct");
        Months.put(10,"Nov");
        Months.put(11,"Dec");
        List<Entry> entries = new ArrayList<Entry>();
        for (int i=0;i<12;i++) {
            entries.add(new Entry(i,money.get(i)));
        }
        LineDataSet dataSet = new LineDataSet(entries, "Costs"); // add entries to dataset
        dataSet.setLineWidth(2f);
        dataSet.setColor(R.color.colorPrimary);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(R.color.colorPrimary);
        dataSet.setFillAlpha(80);
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        LineData lineData = new LineData(dataSet);
        XAxis xAxis = chart.getXAxis();
        String [] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getDescription().setEnabled(false);
        chart.setData(lineData);
        chart.invalidate();
    }
    private void initPieChart(PieChart chart, String title,List<String>names,List<Integer>values){
        ////////////////////
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        Typeface x=Typeface.defaultFromStyle(Typeface.BOLD);
        chart.setCenterTextColor(R.color.colorPrimary);
        chart.setCenterTextTypeface(x);
        chart.setCenterText(title);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);
        // add a selection listener
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                Log.i("VAL SELECTED",
                        "Value: " + e.getY() + ", index: " + h.getX()
                                + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        //Legend l = chart.getLegend();
        //l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //l.setOrientation(Legend.LegendOrientation.VERTICAL);
        //l.setDrawInside(false);
        //l.setXEntrySpace(7f);
        //l.setYEntrySpace(0f);
        //l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(R.color.colorPrimary);
        chart.setEntryLabelTextSize(12f);
        //Typeface x=Typeface.defaultFromStyle(Typeface.BOLD);
        chart.setEntryLabelTypeface(x);
        ///////////////////////////////////////////
        // x.setText("Actitiititi");
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i=0;i<values.size(); i++) {
            entries.add(new PieEntry(values.get(i),names.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(R.color.colorPrimary);
        data.setValueTypeface(x);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }
}