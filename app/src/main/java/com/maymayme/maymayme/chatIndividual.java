package com.maymayme.maymayme;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class chatIndividual extends AppCompatActivity {
    private String chatSubTitle="Online";
    private int chatImage=R.drawable.profile_pic;

    public String friend_id;
    private ArrayList<Messages> myMessages;
    String url;
    com.android.volley.RequestQueue queue;
    GridView gv;
    chatGrid adapter;
    EditText t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView myTitleView=(TextView) findViewById(R.id.title);
        myTitleView.setText(getIntent().getStringExtra("name"));

        friend_id=getIntent().getStringExtra("other_uid");

        t1=(EditText) findViewById(R.id.message);
        TextView mySubView=(TextView) findViewById(R.id.subtitle);
        mySubView.setText(this.chatSubTitle);

        ImageView im=(ImageView) findViewById(R.id.logo);
        im.setImageResource(this.chatImage);


        myMessages=new ArrayList<Messages>();

        gv=(GridView) findViewById(R.id.myGrid);
        adapter = new chatGrid(chatIndividual.this, this.myMessages);
        gv.setAdapter(adapter);

    }
    public void getMessage(){
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/get_messages";
        String new_messsage=t1.getText().toString();
        t1.setText("");
        myMessages.add(myMessages.size(),new Messages(new_messsage, 1));
        adapter.notifyDataSetChanged();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("other_id", this.friend_id);
            jsonObject .put("num_msg", 50);

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
                            myMessages= new ArrayList<Messages>();
                            JSONArray values = response.getJSONArray("messages");
                            for (int i = 0; i < values.length(); i++) {
                                JSONObject temp = values.getJSONObject(i);
                                System.out.println(temp);
                                myMessages.add(new Messages(temp.getString("message"), Integer.parseInt(temp.getString("user"))));

                            }
                            System.out.println(myMessages);
                            adapter = new chatGrid(chatIndividual.this, myMessages);
                            gv.setAdapter(adapter);
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
    }

    public void sendMessage(View view) {
        queue = Volley.newRequestQueue(this);
        url = getString(R.string.ip)+"/api/send_message";
        String new_messsage=t1.getText().toString();
        t1.setText("");
        myMessages.add(myMessages.size(),new Messages(new_messsage, 1));
        adapter.notifyDataSetChanged();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("other_id", this.friend_id);
            jsonObject .put("message", new_messsage);

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
        getMessage();

    }
}
