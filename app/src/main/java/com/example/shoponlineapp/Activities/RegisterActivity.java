package com.example.shoponlineapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoponlineapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etNumber,etPass;
    Button btRegister;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);
        etNumber=findViewById(R.id.etNumber);
        etPass=findViewById(R.id.etPass);
        btRegister=findViewById(R.id.btRegister);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 createAccount();
            }
        });


    }

    private void createAccount() {
        String name=etName.getText().toString();
        String number=etNumber.getText().toString();
        String pass=etPass.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(number)){
            Toast.makeText(this, "Enter number..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Enter password..", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Registering user, please wait..");
            progressDialog.show();

            validateUser(name,number,pass);
        }
    }

    private void validateUser(final String name, final String number, final String pass) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("users").child(number).exists()){
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("name",name);
                    map.put("number",number);
                    map.put("password",pass);

                    databaseReference.child("users").child(number).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Congratulations, you are registered!!", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                      //  progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "there seems to be some error, try again!", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                }else{
                    Toast.makeText(RegisterActivity.this, "this number"+number+" already exists", Toast.LENGTH_SHORT).show();
             //       progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again with some another number", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//private Button CreateAccountButton;
//    private EditText InputName, InputPhoneNumber, InputPassword;
//    private ProgressDialog loadingBar;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//
//        CreateAccountButton = (Button) findViewById(R.id.btRegister);
//        InputName = (EditText) findViewById(R.id.etName);
//        InputPassword = (EditText) findViewById(R.id.etPass);
//        InputPhoneNumber = (EditText) findViewById(R.id.etNumber);
//        loadingBar = new ProgressDialog(this);
//
//
//        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                CreateAccount();
//            }
//        });
//    }
//
//
//
//    private void CreateAccount()
//    {
//        String name = InputName.getText().toString();
//        String phone = InputPhoneNumber.getText().toString();
//        String password = InputPassword.getText().toString();
//
//        if (TextUtils.isEmpty(name))
//        {
//            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(phone))
//        {
//            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(password))
//        {
//            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            loadingBar.setTitle("Create Account");
//            loadingBar.setMessage("Please wait, while we are checking the credentials.");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//
//            ValidatephoneNumber(name, phone, password);
//        }
//    }
//
//
//
//    private void ValidatephoneNumber(final String name, final String phone, final String password)
//    {
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (!(dataSnapshot.child("Users").child(phone).exists()))
//                {
//                    HashMap<String, Object> userdataMap = new HashMap<>();
//                    userdataMap.put("phone", phone);
//                    userdataMap.put("password", password);
//                    userdataMap.put("name", name);
//
//                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task)
//                                {
//                                    if (task.isSuccessful())
//                                    {
//                                        Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
//                                        loadingBar.dismiss();
//
//                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//                                    }
//                                    else
//                                    {
//                                        loadingBar.dismiss();
//                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else
//                {
//                    Toast.makeText(RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}