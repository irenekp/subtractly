package com.example.mainpage_subapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mainpage_subapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    String Email, Password;

    TextView msg;

    EditText email;
    EditText password;

    Button confirmbtn;

    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    ProgressDialog mDialog;
    TextView mLoginPageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        msg = findViewById(R.id.signup_text);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        confirmbtn = findViewById(R.id.confirm_signup);

        mAuth = FirebaseAuth.getInstance();
        confirmbtn.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }
    @Override
    public void onClick(View v) {
        if (v==confirmbtn){
            UserRegister();
        }
    }

    private void UserRegister() {
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(SignUp.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(SignUp.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.length() < 6) {
            Toast.makeText(SignUp.this, "Password must be greater then 6 digits", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Creating User!");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendEmailVerification();
                    mDialog.dismiss();
                    OnAuth(task.getResult().getUser());
                    mAuth.signOut();
                }else{
                    Toast.makeText(SignUp.this,"error on creating user",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUp.this, "Check your Email for verification", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        mdatabase.child(uid).setValue(user);
    }

    private User BuildNewuser(){
        return new User(
                getUserEmail(),
                new Date().getTime()
        );
    }


    public String getUserEmail() {
        return Email;
    }
}