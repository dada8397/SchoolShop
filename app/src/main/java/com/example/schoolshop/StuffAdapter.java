package com.example.schoolshop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StuffAdapter extends ArrayAdapter<Stuff> {

    private static final String TAG = "StuffAdapter";

    private Context mContext;
    int mResource;

    public StuffAdapter(Context context, int resource, List<Stuff> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View item, ViewGroup parent){

        String id = getItem(position).getId();
        String name = getItem(position).getName();
        String owner = getItem(position).getOwner();
        String description = getItem(position).getDescription();
        String img_url = getItem(position).getImgUrl();
        String price = getItem(position).getPrice();
        String status = getItem(position).getStatus();
        String created_at = getItem(position).getCreatedAt();
        String updated_at = getItem(position).getUpdatedAt();


        Stuff stuff = new Stuff(id, name, owner, description, img_url, price, status, created_at, updated_at);

        LayoutInflater inflator = LayoutInflater.from(mContext);
        item = inflator.inflate(mResource, parent, false);

        TextView tvName = item.findViewById(R.id.textView1);
        TextView tvDescription = item.findViewById(R.id.textView2);

        /*images.setImageResource(comImage[position]);*/
        tvName.setText(name);
        tvDescription.setText(description);

        return item;
    }
}
