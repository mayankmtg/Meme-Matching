package com.maymayme.maymayme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.security.AccessController.getContext;
import org.json.*;
/**
 * Created by varnitjain on 10/03/18.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    //private int[] image_resources = {R.drawable.meme1,R.drawable.meme2,R.drawable.meme3};
    //private String[] image_resources = {"https://pre00.deviantart.net/e17b/th/pre/f/2011/103/8/4/yao_ming_meme_new_version_hd_by_guillersevilla-d3dwein.png","https://images5.alphacoders.com/299/299759.png"};

    public static ArrayList<String> image_resources = new ArrayList<String>();

    private static Set<Integer> allSeenMemes = new HashSet<Integer>();
    private static Set<Integer> allLikedMemes = new HashSet<Integer>();

    public static Bitmap currentImg;


    //private String[] image_resources;
    private Context ctx;
    private CustomSwipeAdapter ctx2;
    private LayoutInflater layoutInflater;
    private JSONObject jsonMemes;
    private int currentElem;
    private int isDouble=0;

    public CustomSwipeAdapter(Context ctx) throws IOException,JSONException {
        this.ctx=ctx;
        ctx2=this;

        System.out.println("lallalalalal");

        //new HttpAsyncTask().execute("http://52.66.164.8:8000/api/memes/1");
        //MemeActivity.setFirst();

        List<String> empty = new ArrayList<>();

        getMoreMemes(empty);

    }

    public void sendLiked(String link){

        com.android.volley.RequestQueue queue;
        String url;

        queue = Volley.newRequestQueue(ctx);
        url = ctx.getString(R.string.ip)+"/api/like_meme";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("link", link);

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

    public void sendDisliked(String link){

        com.android.volley.RequestQueue queue;
        String url;

        queue = Volley.newRequestQueue(ctx);
        url = ctx.getString(R.string.ip)+"/api/dislike_meme";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("link", link);

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

    public void getMoreMemes(List<String> allSeen) throws JSONException {


        System.out.println("wohooooooo");

        com.android.volley.RequestQueue queue;
        String url;

        queue = Volley.newRequestQueue(ctx);
        url = ctx.getString(R.string.ip)+"/api/get_memes";
        JSONObject jsonObject = new JSONObject();

        JSONArray allSeenJson = new JSONArray();

        for(int i=0; i < allSeen.size(); i++)
        {
            JSONObject j =  new JSONObject();
            j.put("link", allSeen.get(i));
            allSeenJson.put(j);
        }


        try {
            jsonObject .put("user_id", MainActivity.user_id);
            jsonObject .put("seen_memes", allSeenJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                com.android.volley.Request.Method.POST, url,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //ADD THEM TO IMAGE RESOURCES

                        System.out.println("Getting More Memes");
                        //System.out.println(response);

                        try {

                            JSONArray arr = response.getJSONArray("new_memes");
                            System.out.println("wow");

                            for(int i=0;i<10;i++){
                                System.out.println(arr.get(i).toString());
                                image_resources.add(arr.get(i).toString());
                            }

                            ctx2.notifyDataSetChanged();

                            //System.out.println(arr);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("lol5",error.toString());
            }
        });


        queue.add(jsonObjectRequest);

    }


    @Override
    public int getCount() {
        return image_resources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(LinearLayout)object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position){

        System.out.println("Position:- " + Integer.toString(position));

        MemeActivity.lastSeen=position;

        allSeenMemes.add(position);

        if (position%10==2){
            ArrayList<String> temp2 = new ArrayList<>();
            for (Integer s1 : allSeenMemes) {
                temp2.add(image_resources.get(s1));
            }
            try {
                getMoreMemes(temp2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            allSeenMemes.clear();
            //empty set
        }

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);


        final ImageView imageView = (ImageView) item_view.findViewById(R.id.memeView);

        //imageView.setImageResource(image_resources[position]);

        //ViewPager vp=(ViewPager) ctx.findViewById(ViewPagerId);


//        imageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v)
//            {
//                System.out.println(position);
//            }
//
//        });


        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap src, Picasso.LoadedFrom from) {

                int w = src.getWidth();
                int h = src.getHeight();
                Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
                Canvas canvas = new Canvas(result);
                canvas.drawBitmap(src, 0, 0, null);

                Bitmap waterMark = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.lit_icon);
                canvas.drawBitmap(Bitmap.createScaledBitmap(waterMark, 50, 50, false), 0, 0, null);

                imageView.setImageBitmap(result);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };




        imageView.setOnClickListener(new DoubleClickListener(500) {
            @Override
 			public void onDoubleClick() {
 				// double-click code that is executed if the user double-taps
 				// within a span of 500ms (default).
                System.out.print("DOUBLE CLICK BITCH: ");
                System.out.println(position);

                if (allLikedMemes.contains(position)){
                    allLikedMemes.remove(position);
                    Picasso.get().load(image_resources.get(position)).into(imageView);
                    sendDisliked(image_resources.get(position));
                }else{
                    allLikedMemes.add(position);
                    Picasso.get().load(image_resources.get(position)).into(target);
                    sendLiked(image_resources.get(position));
                    MemeActivity.changePage();
                }

            }
 		});




        if (allLikedMemes.contains(position)){
            Picasso.get().load(image_resources.get(position)).into(target);
        }
        else {
            Picasso.get().load(image_resources.get(position)).into(imageView);
        }



        //Picasso.get().load(image_resources.get(position)).into(imageView);

        //Picasso.get().load(image_resources.get(position)).into(temp);


//        if (isDouble==1){
//            Picasso.get().load(image_resources.get(position+1)).into(imageView);
//            isDouble=0;
//        }else{
//            Picasso.get().load(image_resources.get(position)).into(imageView);
//            isDouble=0;
//        }

        container.addView(item_view);

        return item_view;
    }



    private Bitmap addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Bitmap waterMark = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.lit_icon);
        canvas.drawBitmap(waterMark, 0, 0, null);

        return result;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }


    public String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder();
        while((line = bufferedReader.readLine()) != null)
            result.append(line);

        inputStream.close();
        System.out.println("wowowowowow");
        System.out.println(result);
//        isJsonSet=true;
//        jsonAnswer= result.toString();

        JSONObject mainObject = new JSONObject(result.toString());

        JSONArray st = mainObject.getJSONArray("memes");

        for(int i=0;i<st.length();i++)
        {
            String street = st.getString(i);
            image_resources.add(street);
        }

        this.notifyDataSetChanged();//notifyDataSetChanged();

        return result.toString();

    }

    @SuppressLint("StaticFieldLeak")
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ctx, "Received!", Toast.LENGTH_LONG).show();
        }
    }



}


