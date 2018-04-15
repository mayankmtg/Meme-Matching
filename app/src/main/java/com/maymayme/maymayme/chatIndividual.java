package com.maymayme.maymayme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class chatIndividual extends AppCompatActivity {
    private String chatSubTitle="Online";
    private int chatImage=R.drawable.profile_pic;


    private Messages myMessages[];
    GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView myTitleView=(TextView) findViewById(R.id.title);
        myTitleView.setText(getIntent().getStringExtra("name"));

        TextView mySubView=(TextView) findViewById(R.id.subtitle);
        mySubView.setText(this.chatSubTitle);

        ImageView im=(ImageView) findViewById(R.id.logo);
        im.setImageResource(this.chatImage);


        myMessages=new Messages[2];
        myMessages[0]=new Messages("This is the message that I want you to read and this is a long one for multiple lines.This is the message that I want you to read and this is a long one for multiple lines.", 0);
        myMessages[1]=new Messages("So this is the message that I want you to read and this is a long one for multiple lines.This is the message that I want you to read and this is a long one for multiple lines.", 1);


        gv=(GridView) findViewById(R.id.myGrid);
        chatGrid adapter = new chatGrid(chatIndividual.this, this.myMessages);
        gv.setAdapter(adapter);




    }
}
