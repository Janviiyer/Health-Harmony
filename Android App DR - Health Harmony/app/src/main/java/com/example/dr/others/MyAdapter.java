package com.example.dr.others;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dr.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<List_data> {
    ArrayList<List_data> listdata;
    Context context;
    int resource;
    public MyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<List_data> listdata) {
        super(context, resource, listdata);
        this.listdata=listdata;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.list_item,null,true);
        }
        List_data listdata=getItem(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.image_icons);
        TextView name=(TextView) convertView.findViewById(R.id.glu);
        TextView value=(TextView) convertView.findViewById(R.id.glose);
        Picasso.get()
                .load(listdata.getImg())
                .into(img);

        name.setText(listdata.getName());
        value.setText(listdata.getValue());

        return convertView;
    }
}