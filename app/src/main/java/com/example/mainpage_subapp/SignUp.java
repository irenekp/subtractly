package com.example.mainpage_subapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mainpage_subapp.ui.login.LoginActivity;

public class SignUp extends AppCompatActivity {
    TextView msg;
    EditText emailText;
    EditText passwordText;
    EditText phoneText;
    Button confirmbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        msg = findViewById(R.id.signup_text);
        emailText = findViewById(R.id.signup_email);
        passwordText = findViewById(R.id.signup_password);
        phoneText = findViewById(R.id.phoneNo);
        confirmbtn = findViewById(R.id.confirm_signup);

        confirmbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String confirmationPrompt = "Welcome aboard!";
                Toast.makeText(getApplicationContext(), confirmationPrompt, Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}