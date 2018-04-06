package com.maymayme.maymayme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity {


    final private ArrayList<TextView> allCat = new ArrayList<TextView>();
    private ImageView backButton;
    private ImageView settingsButton;
    private Context ctx;
    private TextView nameText;
    Bitmap proPic;
    CircleImageView profPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx =this;

        setContentView(R.layout.activity_profile);

        allCat.add((TextView) findViewById(R.id.category1));
        allCat.add((TextView) findViewById(R.id.category2));
        allCat.add((TextView) findViewById(R.id.category3));
        allCat.add((TextView) findViewById(R.id.category4));
        allCat.add((TextView) findViewById(R.id.category5));
        allCat.add((TextView) findViewById(R.id.category6));
        allCat.add((TextView) findViewById(R.id.category7));
        allCat.add((TextView) findViewById(R.id.category8));

        nameText = (TextView) findViewById(R.id.personName);
        profPic = (CircleImageView) findViewById(R.id.profile_image);

        backButton = (ImageView) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(ProfileActivity.this, MemeActivity.class);
                ProfileActivity.this.startActivity(activityChangeIntent);
            }
        });

        settingsButton = (ImageView) findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(ProfileActivity.this, SettingsActivity.class);
                ProfileActivity.this.startActivity(activityChangeIntent);
            }
        });

        //Settings Button
        getProfile();

    }

    public void getProfile(){

        com.android.volley.RequestQueue queue;
        String url;

        queue = Volley.newRequestQueue(ctx);
        url = ctx.getString(R.string.ip)+"/api/profile";
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

                        System.out.println(response);

                        try {
                            String fbPic = response.getString("fb_pic");
                            String name = response.getString("name");

                            nameText.setText(name);

                            Picasso.get().load(fbPic).into(profPic);

                            List<Tuple<String, int[], Integer>> tuples = new ArrayList<Tuple<String, int[], Integer>>();

                            int tv = response.getInt("pref_tv");
                            tuples.add(new Tuple<String, int[], Integer>("TV", new int[] { 1 }, tv));
                            int pets = response.getInt("pref_pets");
                            tuples.add(new Tuple<String, int[], Integer>("Pets", new int[] { 1 }, pets));
                            int politics = response.getInt("pref_politics");
                            tuples.add(new Tuple<String, int[], Integer>("Politics", new int[] { 1 }, politics));
                            int art = response.getInt("pref_art");
                            tuples.add(new Tuple<String, int[], Integer>("Art", new int[] { 1 }, art));
                            int gaming = response.getInt("pref_gaming");
                            tuples.add(new Tuple<String, int[], Integer>("Gaming", new int[] { 1 }, gaming));
                            int food = response.getInt("pref_food");
                            tuples.add(new Tuple<String, int[], Integer>("Food", new int[] { 1 }, food));
                            int sports = response.getInt("pref_sports");
                            tuples.add(new Tuple<String, int[], Integer>("Sports", new int[] { 1 }, sports));
                            int science = response.getInt("pref_science");
                            tuples.add(new Tuple<String, int[], Integer>("Science", new int[] { 1 }, science));



                            Comparator<Tuple<String, int[], Integer>> comparator = new Comparator<Tuple<String, int[], Integer>>()
                            {

                                public int compare(Tuple<String, int[], Integer> tupleA,
                                                   Tuple<String, int[], Integer> tupleB)
                                {
                                    return tupleA.getIndex().compareTo(tupleB.getIndex());
                                }

                            };

                            Collections.sort(tuples, comparator);

                            int i=0;
                            for (Tuple<String, int[], Integer> tuple : tuples)
                            {
                                System.out.println(tuple.getName() + " -> " + tuple.getIndex());
                                allCat.get(i).setText(tuple.getName());
                                allCat.get(i).getLayoutParams().width = 300+(tuple.getIndex()/5);
                                i+=1;

                            }


                            //allCat.get(0).setText("");


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


}
