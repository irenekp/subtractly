package com.example.mainpage_subapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.mainpage_subapp.MainActivity;
import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.firebasedata.MembersFB;
import com.example.mainpage_subapp.firebasedata.SubscriptionFB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class addSubActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        List<MembersFB> membersToAdd = new ArrayList<MembersFB>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        ImageView iv=findViewById(R.id.addSubLogo);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(iv);
        Glide.with(this).load(R.raw.logo).into(imageViewTarget);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        EditText edittext = (EditText) findViewById(R.id.StartDate);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(addSubActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ImageButton addMem = findViewById(R.id.addmember);
        addMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout scr = findViewById(R.id.memberlist);
                LayoutInflater inflater = LayoutInflater.from(addSubActivity.this);
                EditText memname = (EditText) findViewById(R.id.memberName);
                EditText memprice = (EditText) findViewById(R.id.subPriceText);
                String price = memprice.getText().toString();
                String name = memname.getText().toString();
                membersToAdd.add(new MembersFB(name));
                View inflatedLayout1 = inflater.inflate(R.layout.member_entry, null, false);
                TextView setname = inflatedLayout1.findViewById(R.id.memberThingy);
                TextView setprice = inflatedLayout1.findViewById(R.id.membershare);
                setprice.setText("");
                setname.setText(name);
                scr.addView(inflatedLayout1);
                memname.setText("");
            }
        });

        Button addSubScription = findViewById(R.id.addsubscription);
        addSubScription.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String name;
                String date;
                String plan;
                String service_type;
                int price;
                int cyclePlan;
                final String[] cycle = new String[1];

                EditText subname = (EditText) findViewById(R.id.subname);
                EditText memprice = (EditText) findViewById(R.id.subPriceText);
                EditText memDate = (EditText) findViewById(R.id.StartDate);
                Spinner planType = (Spinner) findViewById(R.id.planType);
                Spinner serviceType = (Spinner) findViewById(R.id.ServiceType);
                RadioGroup cycleSpan = (RadioGroup)findViewById(R.id.cycleLength);
                RadioButton one, three, full;
                one = (RadioButton)findViewById(R.id.onemonth);
                three = (RadioButton)findViewById(R.id.threemonth);
                full = (RadioButton)findViewById(R.id.oneyear);

                if(one.isChecked()){
                    cyclePlan = 30;
                }

                else if(three.isChecked()){
                    cyclePlan = 90;
                }

                else if(full.isChecked()){
                    cyclePlan = 365;
                }

                else{
                    cyclePlan = 30;
                }

                name = subname.getText().toString();
                price = Integer.parseInt(memprice.getText().toString());
                date = memDate.getText().toString();
                plan = planType.getSelectedItem().toString();
                service_type=serviceType.getSelectedItem().toString();
                membersToAdd.add(new MembersFB("You"));
                int shared = membersToAdd.size();
                String id = name.substring(0,4)+plan.substring(0,4)+price;
                SubscriptionFB toAdd = new SubscriptionFB();
                toAdd.insertSubscription(id, name, plan, price, shared, cyclePlan, R.drawable.ic_launcher, date, membersToAdd,service_type);
                Intent i;
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

}