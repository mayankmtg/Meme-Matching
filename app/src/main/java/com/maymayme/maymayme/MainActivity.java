package com.maymayme.maymayme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    String url;
    RequestQueue queue;
    String fl="false";
    public static String user_id="0";

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/login";
        if(isLoggedIn()){
            setContentView(R.layout.activity_main);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject .put("access_token", AccessToken.getCurrentAccessToken().getToken());
                jsonObject .put("user_id", AccessToken.getCurrentAccessToken().getUserId());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    com.android.volley.Request.Method.POST, url,
                    jsonObject,
                    new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                fl = response.getString("first_login");
                                user_id = response.getString("user_id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }, new com.android.volley.Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("lol","lol");
                }
            });

            queue.add(jsonObjectRequest);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(fl.equals("true")){
                        Intent activityChangeIntent = new Intent(MainActivity.this, PreferenceActivity.class);
                        MainActivity.this.startActivity(activityChangeIntent);
                    }
                    else{
                        Intent activityChangeIntent = new Intent(MainActivity.this, MemeActivity.class);
                        MainActivity.this.startActivity(activityChangeIntent);
                    }
                    MainActivity.this.finish();
                }
            }, 1000);

        }
        else{

            //requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);
            loginButton = (LoginButton)findViewById(R.id.fb_login_bn);
            callbackManager = CallbackManager.Factory.create();
            final Button button = (Button) findViewById(R.id.loginButton);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    Button b1 = (Button) findViewById(R.id.loginButton);
                    b1.setBackgroundColor(Color.parseColor("#189ad3"));
                    b1.setTextColor(Color.WHITE);
                    loginButton.performClick();

                }
            });
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject .put("access_token", loginResult.getAccessToken().getToken());
                        jsonObject .put("user_id", loginResult.getAccessToken().getUserId());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            com.android.volley.Request.Method.POST, url,
                            jsonObject,
                            new com.android.volley.Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.v("reponse", "" + response);
                                }


                            }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("lol","lol");
                        }
                    });

                    queue.add(jsonObjectRequest);
                    Intent activityChangeIntent = new Intent(MainActivity.this, PreferenceActivity.class);
                    MainActivity.this.startActivity(activityChangeIntent);
                }

                @Override
                public void onCancel() {
                    textView.setText("Login Cancelled");
                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
