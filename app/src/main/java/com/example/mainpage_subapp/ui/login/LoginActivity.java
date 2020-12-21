package com.example.mainpage_subapp.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mainpage_subapp.MainActivity;
import com.example.mainpage_subapp.R;
import com.example.mainpage_subapp.SignUp;
import com.example.mainpage_subapp.ui.home.HomeFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build());

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;

    String email, password;

    public static final String userEmail = "";

     EditText usernameEditText;
     EditText passwordEditText;
     ProgressDialog dialog;

    public static final String TAG = "LOGIN";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        dialog = new ProgressDialog(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button signupButton = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Log.d(TAG, "AuthStateChanged:Logout");
                }

            }
        };



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSign();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), SignUp.class);
                startActivity(j);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out.
        mAuth.removeAuthStateListener(mAuthListner);

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        LoginActivity.super.finish();
    }


    private void userSign() {
        email = usernameEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Logging in please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();

                    Toast.makeText(LoginActivity.this, "Login not successful", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();

                    checkIfEmailVerified();

                }
            }
        });

    }

    private void checkIfEmailVerified() {
            FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
            boolean emailVerified=users.isEmailVerified();
            final EditText email = findViewById(R.id.username);
            final EditText password = findViewById(R.id.password);

        if(!emailVerified){
                Toast.makeText(this,"Verify the Email Id",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                finish();
            }
            else {
                email.getText().clear();

                password.getText().clear();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // Sending Email to Dashboard Activity using intent.
                startActivity(intent);

            }

    }
}
