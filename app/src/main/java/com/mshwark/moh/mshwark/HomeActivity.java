package com.mshwark.moh.mshwark;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.SharedPreferences;


import java.util.jar.Attributes;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final TextView userName = (TextView) findViewById(R.id.userName);
        final Button logout = (Button) findViewById(R.id.buttonLogut);
        final Button btRequestCar = (Button) findViewById(R.id.btRequestCar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting out sharedpreferences
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();

                //Puting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                //Putting blank value to email
                editor.putString(Config.EMAIL_SHARED_PREF, "");

                //Saving the sharedpreferences
                editor.commit();

                //Starting login activity
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });




        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(Config.ID_SHARED_PREF,"Not Available");
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        String name = sharedPreferences.getString(Config.Name_SHARED_PREF,"Not Available");
        String mobile = sharedPreferences.getString(Config.Mobile_SHARED_PREF,"Not Available");

        //Showing the current logged in email to textview

        //Showing the current logged in email to textview
        userName.setText("Current User: " + email + name + mobile + "id" + id );



        //userName.setText(WelcomeUserName);


        btRequestCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });
    }
}
