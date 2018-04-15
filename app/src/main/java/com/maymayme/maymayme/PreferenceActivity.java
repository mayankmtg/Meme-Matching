package com.maymayme.maymayme;

import android.content.Intent;
import android.graphics.Color;
import android.net.http.RequestQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceActivity extends AppCompatActivity {
    String url;
    com.android.volley.RequestQueue queue;
    int pref[] = {0,0,0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
    }
    public void nextAct(View v){
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/preferences";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("pref_tv", pref[0]);
            jsonObject .put("pref_pets", pref[1]);
            jsonObject .put("pref_politics", pref[2]);
            jsonObject .put("pref_art", pref[3]);
            jsonObject .put("pref_gaming", pref[4]);
            jsonObject .put("pref_food", pref[7]);
            jsonObject .put("pref_sports", pref[5]);
            jsonObject .put("pref_science", pref[6]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                com.android.volley.Request.Method.POST, url,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }


                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("lol","lol");
            }
        });

        queue.add(jsonObjectRequest);
        Intent activityChangeIntent = new Intent(PreferenceActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PreferenceActivity.this.startActivity(activityChangeIntent);
        finish();
    }



    public void im1Change(View v){
        pref[0]=1;
        ImageView im1=(ImageView) findViewById(R.id.im1);
        TextView t1=(TextView) findViewById(R.id.text1);

        Log.d("Color", String.valueOf(t1.getCurrentTextColor()));
        Log.d("DColor", String.valueOf(Color.parseColor("#189ad3")));

        if(t1.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t1.setTextColor(Color.WHITE);
            im1.setImageResource(R.drawable.icon1);
        }
        else{
            t1.setTextColor(Color.parseColor("#189ad3"));
            im1.setImageResource(R.drawable.icon1lit);

        }


    }

    public void im2Change(View v){
        pref[1]=1;
        ImageView im2=(ImageView) findViewById(R.id.im2);
        TextView t2=(TextView) findViewById(R.id.text2);

        if(t2.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t2.setTextColor(Color.WHITE);
            im2.setImageResource(R.drawable.icon2);
        }
        else{
            t2.setTextColor(Color.parseColor("#189ad3"));
            im2.setImageResource(R.drawable.icon2lit);

        }
    }

    public void im3Change(View v){
        pref[2]=1;
        ImageView im3=(ImageView) findViewById(R.id.im3);
        TextView t3=(TextView) findViewById(R.id.text3);

        if(t3.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t3.setTextColor(Color.WHITE);
            im3.setImageResource(R.drawable.icon3);
        }
        else{
            t3.setTextColor(Color.parseColor("#189ad3"));
            im3.setImageResource(R.drawable.icon3lit);

        }
    }
    public void im4Change(View v){
        pref[3]=1;
        ImageView im4=(ImageView) findViewById(R.id.im4);
        TextView t4=(TextView) findViewById(R.id.text4);

        if(t4.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t4.setTextColor(Color.WHITE);
            im4.setImageResource(R.drawable.icon4);
        }
        else{
            t4.setTextColor(Color.parseColor("#189ad3"));
            im4.setImageResource(R.drawable.icon4lit);

        }
    }

    public void im5Change(View v){
        pref[4]=1;
        ImageView im5=(ImageView) findViewById(R.id.im5);
        TextView t5=(TextView) findViewById(R.id.text5);

        if(t5.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t5.setTextColor(Color.WHITE);
            im5.setImageResource(R.drawable.icon5);
        }
        else{
            t5.setTextColor(Color.parseColor("#189ad3"));
            im5.setImageResource(R.drawable.icon5lit);

        }
    }

    public void im6Change(View v){
        pref[5]=1;
        ImageView im6=(ImageView) findViewById(R.id.im6);
        TextView t6=(TextView) findViewById(R.id.text6);

        if(t6.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t6.setTextColor(Color.WHITE);
            im6.setImageResource(R.drawable.icon6);
        }
        else{
            t6.setTextColor(Color.parseColor("#189ad3"));
            im6.setImageResource(R.drawable.icon6lit);

        }
    }

    public void im7Change(View v){
        pref[6]=1;
        ImageView im7=(ImageView) findViewById(R.id.im7);
        TextView t7=(TextView) findViewById(R.id.text7);

        if(t7.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t7.setTextColor(Color.WHITE);
            im7.setImageResource(R.drawable.icon7);
        }
        else{
            t7.setTextColor(Color.parseColor("#189ad3"));
            im7.setImageResource(R.drawable.icon7lit);

        }
    }

    public void im8Change(View v){
        pref[7]=1;
        ImageView im8=(ImageView) findViewById(R.id.im8);
        TextView t8=(TextView) findViewById(R.id.text8);

        if(t8.getCurrentTextColor()==Color.parseColor("#189ad3")){
            t8.setTextColor(Color.WHITE);
            im8.setImageResource(R.drawable.icon8);
        }
        else{
            t8.setTextColor(Color.parseColor("#189ad3"));
            im8.setImageResource(R.drawable.icon8lit);

        }
    }
}