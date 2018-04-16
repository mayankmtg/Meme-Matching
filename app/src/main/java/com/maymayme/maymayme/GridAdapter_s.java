package com.maymayme.maymayme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mayank on 27/3/18.
 */

public class GridAdapter_s extends BaseAdapter{

    private ArrayList<String> myNames;
    private ArrayList<String> desc;
    private ArrayList<String> ids;
    private ArrayList<String> pics;
    private ArrayList<String> status;
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter_s(Context context, ArrayList<String> myNames, ArrayList<String> desc,ArrayList<String> ids,ArrayList<String> pics, ArrayList<String> status){
        this.context=context;
        this.myNames=myNames;
        this.desc=desc;
        this.ids=ids;
        this.pics=pics;
        this.status=status;
    }

    @Override
    public int getCount() {
        return myNames.size();
    }

    @Override
    public Object getItem(int i) {

        return myNames.get(i);
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
        ImageView im = (ImageView)gridView.findViewById(R.id.profile);
        t1.setText(myNames.get(i));
        t2.setText(desc.get(i));
//        new ImageLoadTask(pics.get(i),im).execute();
        return gridView;
    }

}