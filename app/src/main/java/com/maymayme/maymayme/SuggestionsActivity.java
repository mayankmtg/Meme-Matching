package com.maymayme.maymayme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SuggestionsActivity extends AppCompatActivity {
    public GridView gridView;
    public static ArrayList<String> names;
    public static ArrayList<String> descs;
    public static ArrayList<String> ids;
    public static ArrayList<String> pics;
    public static ArrayList<String> status;
    String url;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        names = new ArrayList<>();
        descs = new ArrayList<>();
        ids = new ArrayList<>();
        pics = new ArrayList<>();
        status = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/get_suggestions";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Suggestions

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                com.android.volley.Request.Method.POST, url,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray values = response.getJSONArray("suggestions");
                            for (int i = 0; i < values.length(); i++) {

                                JSONObject temp = values.getJSONObject(i);
                                System.out.println(temp);
                                names.add(temp.getString("name"));
                                ids.add(temp.getString("uid"));
                                descs.add(temp.getString("score"));
                                pics.add(temp.getString("fb_pic"));
                                status.add("0");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        gridView=(GridView) findViewById(R.id.suggestGrid);
                        Log.d("ll",Integer.toString(names.size()));
                        GridAdapter_s gridAdapter=new GridAdapter_s(SuggestionsActivity.this, names, descs,ids,pics,status);
                        gridView.setAdapter(gridAdapter);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(SuggestionsActivity.this, "Clicked Here: "+names.get(i), Toast.LENGTH_SHORT).show();
                                ImageView pl = (ImageView)view.findViewById(R.id.plus);
                                String act;
                                if(status.get(i).equals("0")){
                                    pl.setImageDrawable(getDrawable(R.drawable.minus_b));
                                    act = "Accept";
                                    status.set(i,"1");
                                }
                                else{
                                    pl.setImageDrawable(getDrawable(R.drawable.plus_b));
                                    act = "Reject";
                                    status.set(i,"0");
                                }

                                url = getString(R.string.ip)+"/api/add_suggestion";
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject .put("user_id", MainActivity.user_id);
                                    jsonObject .put("add_uid", ids.get(i));
                                    jsonObject .put("action",act);

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

                            }

                        });

                    }


                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("lol","lol");
            }
        });

        queue.add(jsonObjectRequest);


    }
    public void nextAct(View v){
        Intent activityChangeIntent = new Intent(SuggestionsActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(activityChangeIntent);
        finish();
    }
}


















