package com.jaida.keeper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by antonnazareth on 14/11/2015.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    Integer[] mThumbIds;
    int[] mColours;

    public GridViewAdapter(Context c, Integer[] thumbIds, int[] colours) {
        mContext = c;
        mThumbIds = thumbIds;
        mColours = colours;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setBackgroundColor(mColours[position]);
            imageView.setBackgroundColor(mColours[10]);

            //int black = Color.parseColor("FFFFFF");
            //imageView.setBackgroundColor(black);//Color.parseColor(mColours[position]));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        //Color black = new Color(0xFF0096);
        //Color bColor = Color.decode("FFFFFF");
        //imageView.setBackgroundColor(black);//Color.parseColor(mColours[position]));
        return imageView;
    }

    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.add_group, R.drawable.del_button,
//            R.drawable.multi_user, R.drawable.two_user
//    };
}