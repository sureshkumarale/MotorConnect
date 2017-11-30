package com.sureshale.motorconnect;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;

/**
 * Created by sureshale on 27-11-2017.
 */

public class ListOfManufacturersAdapter extends BaseAdapter{

    Context ctx;
    ArrayList<Integer> manufacturerImageId = new ArrayList<>();
    ArrayList<String> manufacturerNameText = new ArrayList<>();
    LayoutInflater layoutInflater;
    public ListOfManufacturersAdapter(Context ctx, ArrayList<Integer> manufacturerImageId, ArrayList<String> manufacturerNameText, LayoutInflater layoutInflater){
        this.ctx = ctx;
        this.manufacturerImageId = manufacturerImageId;
        this.manufacturerNameText = manufacturerNameText;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return manufacturerNameText.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_items_for_list_of_manufacturers,null);
        AppCompatImageView vehicleImage = (AppCompatImageView)convertView.findViewById(R.id.image_of_manufacturer);
        TextView vehicleType = (TextView)convertView.findViewById(R.id.name_of_manufacturer);

        vehicleImage.setImageResource(manufacturerImageId.get(position));
        vehicleType.setText(manufacturerNameText.get(position));
        return convertView;
    }
}
