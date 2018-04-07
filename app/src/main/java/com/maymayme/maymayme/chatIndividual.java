package com.maymayme.maymayme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class chatIndividual extends AppCompatActivity {
    private String chatTitle="Mayank";
    private String chatSubTitle="Online";
    private int chatImage=R.drawable.profile_pic;


    private String messages[]={"My name is mayank", "What is your name?"};
    GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_individual);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView myTitleView=(TextView) findViewById(R.id.title);
        myTitleView.setText(this.chatTitle);

        TextView mySubView=(TextView) findViewById(R.id.subtitle);
        mySubView.setText(this.chatSubTitle);

        ImageView im=(ImageView) findViewById(R.id.logo);
        im.setImageResource(this.chatImage);

        gv=(GridView) findViewById(R.id.myGrid);
        chatGrid adapter = new chatGrid(chatIndividual.this, this.messages);
        gv.setAdapter(adapter);




    }
}
