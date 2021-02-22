package com.example.shoponlineapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoponlineapp.BackgroundTask;
import com.example.shoponlineapp.Models.Users;
import com.example.shoponlineapp.Prevalent.Prevalent;
import com.example.shoponlineapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class LoginActivity extends AppCompatActivity {

    Button btLogin;
    EditText etNumber,etPass;
    ProgressDialog progressDialog;
    TextView tvForgot,adminLink;
    CheckBox remember_me;

    public static String  PREFS_NAME="NAME";
    public static String PREF_NUMBER="";
    public static String PREF_PASSWORD="";
    public static boolean isAdmin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin=findViewById(R.id.btLogin);
        etNumber=findViewById(R.id.etNumber);
        etPass=findViewById(R.id.etPass);
        tvForgot=findViewById(R.id.tvForgot);
        remember_me=findViewById(R.id.remember_me);
        adminLink=findViewById(R.id.adminLink);

        progressDialog=new ProgressDialog(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=etNumber.getText().toString();
                String pass=etPass.getText().toString();

                if(TextUtils.isEmpty(number)){
                    Toast.makeText(LoginActivity.this, "Enter number..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "Enter password..", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Checking credentials, please wait..");
                    progressDialog.show();

                    if(isAdmin){
                        admin_login(number,pass);
                    }else {
                        login(number, pass);
                    }
                }
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String phone = pref.getString(PREF_NUMBER, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (phone != null || password != null) {
            etNumber.setText(phone);
            etPass.setText(password);
            remember_me.setChecked(true);
        }
        else {
            remember_me.setChecked(false);
        }

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLink.setVisibility(View.INVISIBLE);
                btLogin.setText("Admin Login");

                isAdmin=true;
            }

        });

    }

    private void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"yukta.mittal2@gmail.com"};
        String[] CC = {"aditiverma0308@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Set new Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LoginActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(final String number, final String pass) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userNameRef = rootRef.child("users").child(number);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(LoginActivity.this, "This number doesn't exist, try again!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressDialog.dismiss();
//                    Users userData=dataSnapshot.getValue(Users.class);
//                    if(userData.getPass().equals(pass))
//                      Toast.makeText(LoginActivity.this, "Congratulations! You are logged in!", Toast.LENGTH_SHORT).show();
//                    else{
//                        Toast.makeText(LoginActivity.this, "Password is wrong", Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                    }
//                    Prevalent.currentOnlineUser=userData;
                    Toast.makeText(LoginActivity.this, "Congratulations! You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //   Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        userNameRef.addListenerForSingleValueEvent(eventListener);

        String type = "login";
        if(remember_me.isChecked()){
            rememberMe(number,pass);
        }
        else{
            clear();
        }
        BackgroundTask bt = new BackgroundTask(this);
        bt.execute(type, number, pass);
    }

    private void rememberMe(String number, String pass) {
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_NUMBER,number)
                .putString(PREF_PASSWORD,pass)
                .commit();
    }
    public void clear(){
        SharedPreferences sharedPrefs =getSharedPreferences(LoginActivity.PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
    }

    private void admin_login(final String number, final String pass) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Admins").child(number).exists()){
                    Toast.makeText(LoginActivity.this, "This number doesn't exist admin, try again!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();
//                            Users userData=dataSnapshot.child("Admins").child(number).getValue(Users.class);
//                            if(userData.getPass().equals(pass))
//                                Toast.makeText(LoginActivity.this, "Congratulations Admin! You are logged in!", Toast.LENGTH_SHORT).show();
//                            else{
//                                Toast.makeText(LoginActivity.this, "Password is wrong admin", Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//                                startActivity(intent);
//                            }
                    Toast.makeText(LoginActivity.this, "Congratulations Admin! You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,AdminCategoryActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        String type = "login";
        if(remember_me.isChecked()){
            rememberMe(number,pass);
        }
        else{
            clear();
        }
        BackgroundTask bt = new BackgroundTask(this);
        bt.execute(type, number, pass);
    }
}