package com.example.schoolshop;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StuffAdapter extends BaseAdapter {

    List<Stuff> stuffs = new ArrayList<Stuff>();
    Context context;

    public StuffAdapter(Context context){
        this.context = context;
    }

    public void add(Stuff stuff) {
        this.stuffs.add(stuff);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return stuffs.size();
    }

    @Override
    public Object getItem(int i) {
        return stuffs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup){

        StuffViewHolder holder = new StuffViewHolder();
        LayoutInflater stuffInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Stuff stuff = stuffs.get(i);

        convertView = stuffInflater.inflate(R.layout.stuff_list, null);
        holder.stuffImage = convertView.findViewById(R.id.stuff_image);
        holder.stuffName = convertView.findViewById(R.id.stuff_name);
        holder.stuffPrice = convertView.findViewById(R.id.stuff_price);
        holder.stuffDescription = convertView.findViewById(R.id.stuff_description);
        convertView.setTag(holder);
        holder.stuffName.setText(stuff.getName());
        holder.stuffPrice.setText(stuff.getPrice());
        holder.stuffDescription.setText(stuff.getDescription());
        String json = stuff.getImgUrl();
        try {
            JSONObject obj = new JSONObject(json);
            Picasso.get().load(obj.getString("1")).into(holder.stuffImage);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return convertView;
    }
}

class StuffViewHolder {
    public ImageView stuffImage;
    public TextView stuffName;
    public TextView stuffPrice;
    public TextView stuffDescription;
}