package com.maymayme.maymayme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class MemeActivity extends AppCompatActivity {

    static ViewPager viewPager;
    CustomSwipeAdapter adapter;
    private static String jsonAnswer="";
    private static boolean isJsonSet=false;
    private static int currentImage=0;
    ImageView chatView;
    ImageView suggestView;
    ImageView profileView;
    ImageView saveView;
    ImageView shareView;
    Context ctx;
    public static int lastSeen=0;

    Bitmap currentImg;

    public static void changePage(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
    }

    public static void setFirst(){
        viewPager.setCurrentItem(1, true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        ctx = this;

        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        chatView = (ImageView) findViewById(R.id.chat);
        profileView = (ImageView) findViewById(R.id.profile);
        suggestView = (ImageView) findViewById(R.id.suggest);
        saveView = (ImageView) findViewById(R.id.save);
        shareView = (ImageView) findViewById(R.id.share);

//        if (lastSeen>0){
//            viewPager.setCurrentItem(lastSeen-1, true);
//        }


        final Target forImgSave = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap src, Picasso.LoadedFrom from) {
                currentImg=src;
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };


        chatView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                System.out.println("CHAT");
                Intent activityChangeIntent = new Intent(MemeActivity.this, chatActivity.class);
                activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MemeActivity.this.startActivity(activityChangeIntent);
            }

        });

        suggestView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                System.out.println("SUGGEST");
                Intent activityChangeIntent = new Intent(MemeActivity.this, SuggestionsActivity.class);
                activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MemeActivity.this.startActivity(activityChangeIntent);
            }

        });

        profileView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                System.out.println("PROFILE");
                Intent activityChangeIntent = new Intent(MemeActivity.this, ProfileActivity.class);
                activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                MemeActivity.this.startActivity(activityChangeIntent);
            }

        });

        saveView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                System.out.println("SAVE");

                //Bitmap toSave = CustomSwipeAdapter.currentImg;

                Picasso.get().load(CustomSwipeAdapter.image_resources.get(viewPager.getCurrentItem())).into(forImgSave);

                Save savefile = new Save();

                savefile.saveImage(ctx,currentImg);

            }

        });

        shareView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                System.out.println("SHARE");
                Picasso.get().load(CustomSwipeAdapter.image_resources.get(viewPager.getCurrentItem())).into(forImgSave);

                try{
                    Share share = new Share();
                    share.share_bitMap_to_Apps(ctx,currentImg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        });



        viewPager = (ViewPager)findViewById(R.id.memePager);

        JSONObject memesJson=null;


        System.out.println("momomomomomom");


//        viewPager.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                //this will log the page number that was click
//                System.out.print("poop: ");
//                System.out.println(viewPager.getCurrentItem()); //("TAG", "This page was clicked: " + pos);
//                //viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
//
//            }
//        });


//        viewPager.setOnClickListener(new DoubleClickListener(500) {
//            @Override
// 			public void onDoubleClick() {
// 				// double-click code that is executed if the user double-taps
// 				// within a span of 500ms (default).
//                System.out.println("DOUBLE CLICK BITCH");
//            }
// 		});


        //Load new images when currentImage is last to last of previous

        /*if (currentImage==0){
            new HttpAsyncTask().execute("http://52.66.164.8:8000/api/memes/1");
        }
        else if (currentImage%5==2){
            String loadSetNumber = Integer.toString(currentImage/5+2);
            String linkAddresses = "http://52.66.164.8:8000/api/memes/".concat(loadSetNumber);
            new HttpAsyncTask().execute(linkAddresses);
        }*/

        //new HttpAsyncTask().execute("http://52.66.164.8:8000/api/memes/1");

        //isJsonSet=false;

//        while(!isJsonSet){
//            SystemClock.sleep(1000);
//        }
//
//        System.out.println("HOHOHOHOHOHO");
//        System.out.println(jsonAnswer);


        try{

            adapter = new CustomSwipeAdapter(this);

        }catch(Exception e){

        }

        viewPager.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(0);
            }
        }, 1000);
        //viewPager.setCurrentItem(1);


    }

}
