package com.mshwark.moh.mshwark;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Make sure if user is login go to HomeActivity
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }


        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);



        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent (LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean valid = true; // to make sure everthing is OK


                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();


                if (username.isEmpty()) {
                    etUsername.setError("Enter your email");
                    valid = false;

                } else {
                    etUsername.setError(null);
                }

                if (password.isEmpty()) {
                    etPassword.setError("Enter your password");
                    valid = false;

                } else {
                    etPassword.setError(null);
                }


                if (valid) { // if everthing is OK


                //message to show dialog
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Login...");
                progressDialog.show();


                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                String id = jsonResponse.getString("id");
                                String name = jsonResponse.getString("fullname");
                                String email = jsonResponse.getString("email");
                                String mobile = jsonResponse.getString("mobile");


                                /* make new session
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString(Name, name);
                                editor.putString(Email, email);
                                editor.putString(Phone, mobile);
                                editor.commit();

                                Intent in = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(in);*/

                                //Creating a shared preference
                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                editor.putString(Config.ID_SHARED_PREF, id);
                                editor.putString(Config.EMAIL_SHARED_PREF, email);
                                editor.putString(Config.Name_SHARED_PREF, name);
                                editor.putString(Config.Mobile_SHARED_PREF, mobile);

                                //Saving values to editor
                                editor.commit();

                                //Starting profile activity
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);




                                /*Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("mobile", mobile);
                                LoginActivity.this.startActivity(intent);*/

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            } // end valid
            }
        });


    }
}
