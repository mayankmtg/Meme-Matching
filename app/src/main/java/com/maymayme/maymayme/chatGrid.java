package com.maymayme.maymayme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by mayank on 8/4/18.
 */

public class chatGrid extends BaseAdapter {

    private String messages[];
    private Context context;
    private LayoutInflater layoutInflater;

    public chatGrid(Context context, String messages[]){
        this.context=context;
        this.messages=messages;

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
        View lv=(View) gridView.findViewById(R.id.leftSpace);
        TextView mes=(TextView) gridView.findViewById(R.id.message);
        View rv=(View) gridView.findViewById(R.id.rightSpace);
        mes.setText(messages[i]);


        return gridView;
    }
}
