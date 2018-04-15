package com.maymayme.maymayme;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * Created by mayank on 8/4/18.
 */

public class chatGrid extends BaseAdapter {

    private Messages messages[];
    private Context context;
    private LayoutInflater layoutInflater;

    public chatGrid(Context context, Messages myMessages[]){
        this.context=context;
        this.messages=myMessages;

    }
    @Override
    public int getCount() {
        return messages.length;
    }

    @Override
    public Object getItem(int i) {
        return messages[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView = view;
        if(view==null){
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView=layoutInflater.inflate(R.layout.custom_chat, null);
        }
        CardView mv=(CardView)gridView.findViewById(R.id.mainView);
        View lv=(View) gridView.findViewById(R.id.leftSpace);
        TextView mes=(TextView) gridView.findViewById(R.id.message);
        View rv=(View) gridView.findViewById(R.id.rightSpace);


        if(messages[i].person==1){
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight=1.0f;
            params2.weight=0.7f;
            mv.setCardBackgroundColor(Color.parseColor("#189ad3"));
            lv.setLayoutParams(params2);
            rv.setLayoutParams(params);

        }
        mes.setText(messages[i].message);


        return gridView;
    }
}
