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
    String names[]={"Mayank", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman"};
    String descs[]={"Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline"};
    String status[]={"Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline"};
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
                Toast.makeText(chatActivity.this, "Clicked Here: "+names[i], Toast.LENGTH_SHORT).show();
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
