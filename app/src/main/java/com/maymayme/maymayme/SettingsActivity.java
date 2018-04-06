package com.maymayme.maymayme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

public class SettingsActivity extends AppCompatActivity {

    ImageView back;

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        back = (ImageView) findViewById(R.id.bac);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(SettingsActivity.this, ProfileActivity.class);
                SettingsActivity.this.startActivity(activityChangeIntent);
            }
        });


    }
    public void onLog(View v) {

        if (isLoggedIn()) {

            //LoginButton loginButton = (LoginButton)findViewById(R.id.fb_login_bn);
            //loginButton.performClick();

            LoginManager.getInstance().logOut();
            MainActivity.user_id = "0";

            Intent activityChangeIntent = new Intent(SettingsActivity.this, MainActivity.class);
            SettingsActivity.this.startActivity(activityChangeIntent);


        }

    }

    public void onPref(View v) {

            Intent activityChangeIntent = new Intent(SettingsActivity.this, PreferenceActivity.class);
            SettingsActivity.this.startActivity(activityChangeIntent);

    }
}