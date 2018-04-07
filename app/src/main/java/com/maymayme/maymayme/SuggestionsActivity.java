package com.maymayme.maymayme;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class SuggestionsActivity extends AppCompatActivity {
    GridView gridView;
    String names[]={"Mayank MOHINDRA", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman","Mayank", "Aman"};
    String descs[]={"Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline","Online", "Offline"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        gridView=(GridView) findViewById(R.id.suggestGrid);

        GridAdapter_s gridAdapter=new GridAdapter_s(SuggestionsActivity.this, names, descs);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SuggestionsActivity.this, "Clicked Here: "+names[i], Toast.LENGTH_SHORT).show();
                ImageView pl = (ImageView)view.findViewById(R.id.plus);
                pl.setImageDrawable(getDrawable(R.drawable.tick_b));
            }

        });

    }
    public void nextAct(View v){
        Intent activityChangeIntent = new Intent(SuggestionsActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SuggestionsActivity.this.startActivity(activityChangeIntent);
        finish();
    }

    public void onSugg(View v) {

        Intent activityChangeIntent = new Intent(SuggestionsActivity.this, MemeActivity.class);
        activityChangeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SuggestionsActivity.this.startActivity(activityChangeIntent);

    }

}
