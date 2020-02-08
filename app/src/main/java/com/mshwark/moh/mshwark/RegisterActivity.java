package com.mshwark.moh.mshwark;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText  etMobile = (EditText) findViewById(R.id.etMobile);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button  bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean valid = true; // to make sure everthing is OK


                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String mobile = etMobile.getText().toString();
                final String password = etPassword.getText().toString();


                if (name.isEmpty()) {
                    etName.setError("Enter your name");
                    valid = false;
                } else {
                    etName.setError(null);
                }

                if (username.isEmpty()) {
                    etUsername.setError("Enter your email");
                    valid = false;

                } else {
                    etUsername.setError(null);
                }

                if (mobile.isEmpty()) {
                    etMobile.setError("Enter your mobile");
                    valid = false;

                } else {
                    etMobile.setError(null);
                }

                if (password.isEmpty()) {
                    etPassword.setError("Enter your password");
                    valid = false;

                } else {
                    etPassword.setError(null);
                }


                if (valid) { // if everthing is OK


                    //message to show dialog
                    final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this, R.style.AppTheme);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating Account...");
                    progressDialog.show();


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    RegisterRequest registerRequest = new RegisterRequest(name, username, mobile, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);

                } // end valid




            }
        });
    }
}
