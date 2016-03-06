package com.jaida.keeper;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by antonnazareth on 22/08/2015.
 */


public class CustomAdapter extends BaseAdapter {
    ArrayList<String> arrayData;
    Context context;
    ArrayList<Integer> imageIdArray;

    private static LayoutInflater inflater=null;

    //    public CustomAdapter(Activity activity, String[] itemStrings, int[] itemImages) {
    public CustomAdapter(Activity activity, ArrayList<String> itemStrings, ArrayList<Integer> imageArray){//}, OnClickListener clickListen) {

        arrayData=itemStrings;
        context=activity;
        imageIdArray=imageArray;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageButton btn;
        ImageView img;
    }

    public void add(String text){
        arrayData.add(text);
    }

    public void remove(int position){
        arrayData.remove(position);
        notifyDataSetChanged();
    }

    public String getText(int position) {
        // TODO Auto-generated method stub
        return arrayData.get(position);
    }

    public void clearData() {
        // clear the data
        arrayData.clear();
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        Holder holder=new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.generic_list_item, null);

        holder.tv = (TextView) rowView.findViewById(R.id.list_item_text_view);
        holder.tv.setText(arrayData.get(position));

        holder.img = (ImageView) rowView.findViewById(R.id.list_item_image);
        holder.img.setImageResource(imageIdArray.get(position));

        holder.btn = (ImageButton) rowView.findViewById(R.id.list_item_img_button);
        holder.btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });

        return rowView;
    }

}



