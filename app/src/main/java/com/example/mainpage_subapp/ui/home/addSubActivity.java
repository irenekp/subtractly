package com.example.mainpage_subapp.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.example.mainpage_subapp.R;

public class addSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);
        ScrollView scr=findViewById(R.id.memberlist);
        LayoutInflater inflater = LayoutInflater.from(addSubActivity.this);
        View inflatedLayout = inflater.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout);

        LayoutInflater inflater1 = LayoutInflater.from(addSubActivity.this);
        View inflatedLayout1 = inflater1.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout1);

        LayoutInflater inflater2 = LayoutInflater.from(addSubActivity.this);
        View inflatedLayout2 = inflater2.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout2);

        LayoutInflater inflater3 = LayoutInflater.from(addSubActivity.this);
        View inflatedLayout3 = inflater3.inflate(R.layout.member_entry, null, false);
        scr.addView(inflatedLayout3);
    }
}