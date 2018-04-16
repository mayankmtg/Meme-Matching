package com.maymayme.maymayme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<String> names;
    ArrayList<String> uids;
    ArrayList<String> profile_images;
    String url;
    com.android.volley.RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        gridView=(GridView) findViewById(R.id.chatGrid);

        names=new ArrayList<String>();
        uids=new ArrayList<String>();
        profile_images=new ArrayList<String>();
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/get_chats";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
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
                            JSONArray values = response.getJSONArray("chats");
                            for (int i = 0; i < values.length(); i++) {

                                JSONObject temp = values.getJSONObject(i);
                                System.out.println(temp);
                                names.add(temp.getString("name"));
                                uids.add(temp.getString("uid"));
                                profile_images.add(temp.getString("fb_pic"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        GridAdapter gridAdapter=new GridAdapter(chatActivity.this, names, uids,profile_images);
                        gridView.setAdapter(gridAdapter);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent chatIntent=new Intent(chatActivity.this, chatIndividual.class);
                                chatIntent.putExtra("name",names.get(i));
                                chatIntent.putExtra("other_uid",uids.get(i));
                                chatIntent.putExtra("image",profile_images.get(i));
                                startActivity(chatIntent);
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
        Intent activityChangeIntent = new Intent(chatActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(activityChangeIntent);
        finish();
    }
}
