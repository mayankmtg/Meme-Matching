package com.maymayme.maymayme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class chatActivity extends AppCompatActivity {
    GridView gridView;
    String names[]={"Mayank", "Aman"};
    String descs[]={"Online", "Offline"};
    String status[]={"Online", "Offline"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        gridView=(GridView) findViewById(R.id.chatGrid);

        GridAdapter gridAdapter=new GridAdapter(chatActivity.this, names, descs,status);
        gridView.setAdapter(gridAdapter);
        
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent chatIntent=new Intent(chatActivity.this, chatIndividual.class);
                chatIntent.putExtra("name",names[i]);
                startActivity(chatIntent);
            }
        });

    }
    public void nextAct(View v){
        Intent activityChangeIntent = new Intent(chatActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(activityChangeIntent);
        finish();
    }
}
