package com.maymayme.maymayme;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    CallbackManager mCallbackManager;


    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        //updateUI(currentUser);
//    }


    private void handleFacebookAccessToken(AccessToken token) {
        //Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            System.out.println("USER LOGGED IN");

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                            //Toast.LENGTH_SHORT).show();
                            System.out.println("USER NOT LOGGED IN");
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.packagename",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//
//        //FIREBASE
//
//        setContentView(R.layout.activity_main);
//
//        loginButton = (LoginButton)findViewById(R.id.fb_login_bn);
//        callbackManager = CallbackManager.Factory.create();
//
//        final Button button = (Button) findViewById(R.id.loginButton);
//        button.setVisibility(View.VISIBLE);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//                Button b1 = (Button) findViewById(R.id.loginButton);
//                b1.setBackgroundColor(Color.parseColor("#189ad3"));
//                b1.setTextColor(Color.WHITE);
//                loginButton.performClick();
//
//            }
//        });
//
//
//        mAuth = FirebaseAuth.getInstance();
//
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = findViewById(R.id.fb_login_bn);
//
//        loginButton.setReadPermissions("email", "public_profile");
//
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                //Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                //Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                //Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });








        //FACEBOOK
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
                    activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    MainActivity.this.startActivity(activityChangeIntent);
                    finish();
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

        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        //mCallbackManager.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
