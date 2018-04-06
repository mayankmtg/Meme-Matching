package com.maymayme.maymayme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mayank on 27/3/18.
 */

public class GridAdapter_s extends BaseAdapter{

    private String myNames[];
    private String desc[];
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter_s(Context context, String myNames[], String desc[]){
        this.context=context;
        this.myNames=myNames;
        this.desc=desc;


    }

    @Override
    public int getCount() {
        return myNames.length;
    }

    @Override
    public Object getItem(int i) {

        return myNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView=view;
        if(view==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView=inflater.inflate(R.layout.custom_suggestions, null);

        }
        TextView t1=(TextView) gridView.findViewById(R.id.name);
        TextView t2=(TextView) gridView.findViewById(R.id.desc);

        t1.setText(myNames[i]);
        t2.setText(desc[i]);

        return gridView;
    }

}
