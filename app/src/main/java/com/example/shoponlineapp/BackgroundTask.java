package com.example.shoponlineapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.widget.EditText;


import com.example.shoponlineapp.Activities.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;



public class BackgroundTask extends AsyncTask<String,Void,String> {
    String register_url = "http://10.0.0.4/loginapp/register.php";
    String login_url = "http://10.0.0.4/loginapp/login.php";

    Context ctx;

    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;


    public BackgroundTask(Context ctx) {

        this.ctx = ctx;
        activity = (Activity) ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Connecting to server .... ");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        if (method.equals("register")) {

            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String owner_name = params[1];
                String shop_name = params[2];
                String phone_no = params[3];
                String shop_address = params[4];
                String opening_time = params[5];
                String closing_time = params[6];
                String password = params[7];
                String data = URLEncoder.encode("owner_name", "UTF-8") + "=" + URLEncoder.encode(owner_name, "UTF-8") + "&" +
                        URLEncoder.encode("shop_name", "UTF-8") + "=" + URLEncoder.encode(shop_name, "UTF-8") + "&" +
                        URLEncoder.encode("phone_no", "UTF-8") + "=" + URLEncoder.encode(phone_no, "UTF-8") + "&" +
                        URLEncoder.encode("shop_address", "UTF-8") + "=" + URLEncoder.encode(shop_address, "UTF-8") + "&" +
                        URLEncoder.encode("opening_time", "UTF-8") + "=" + URLEncoder.encode(opening_time, "UTF-8") + "&" +
                        URLEncoder.encode("closing_time", "UTF-8") + "=" + URLEncoder.encode(closing_time, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {


                    stringBuilder.append(line + "\n");

                }
                httpURLConnection.disconnect();

                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } else if (method.equals("login")) {
            try {
                URL url = new URL(login_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String phone_no, password;
                phone_no = params[1];
                password = params[2];
                String data = URLEncoder.encode("phone_no", "UTF-8") + "=" + URLEncoder.encode(phone_no, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {


                    stringBuilder.append(line + "\n");

                }
                httpURLConnection.disconnect();

                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {

        progressDialog.dismiss();
        try {

            if(json!=null) {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonarry = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonarry.getJSONObject(0);
                String code = JO.getString("code");
                String message = JO.getString("message");

                if (code.equals("reg_true")) {

                    showDialog("Registration Success", code, message);

                } else if (code.equals("reg_false")) {


                    showDialog("Registration Failed", code, message);

                } else if (code.equals("login_true")) {


                    Intent intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra("message", message);
                    activity.startActivity(intent);
                } else if (code.equals("login_false")) {
                    showDialog("Login Error", code, message);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String title, String code, String message) {

        builder.setTitle(title);
        if (code.equals("reg_true") || code.equals("reg_false")) {

            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }

            });


        } else if (code.equals("login_false")) {

            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText phone_no, password;
                    phone_no = (EditText) activity.findViewById(R.id.etNumber);
                    password = (EditText) activity.findViewById(R.id.etPass);
                    phone_no.setText("");
                    password.setText("");
                    dialog.dismiss();
                }
            });


        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }



}
