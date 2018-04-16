package com.maymayme.maymayme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mayank on 27/3/18.
 */

public class GridAdapter extends BaseAdapter{

    private ArrayList<String> names;
    private ArrayList<String> uids;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> profile_images;

    public GridAdapter(Context context, ArrayList<String> names, ArrayList<String> uids,ArrayList<String> profile_images){
        this.context=context;
        this.names=names;
        this.profile_images=profile_images;
        this.uids = uids;


    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {

        return names.get(i);
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
            gridView=inflater.inflate(R.layout.custom_chatroom, null);

        }
        TextView t1=(TextView) gridView.findViewById(R.id.name);
        ImageView profile_image = (ImageView) gridView.findViewById(R.id.imageView1);
        t1.setText(names.get(i));

        return gridView;
    }
}
