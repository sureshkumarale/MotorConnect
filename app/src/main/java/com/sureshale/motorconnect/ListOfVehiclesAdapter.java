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
 * Created by sureshale on 20-11-2017.
 */

public class ListOfVehiclesAdapter extends BaseAdapter {

    Context ctx;
    ArrayList<String> vehicleTypeText = new ArrayList<>();
    ArrayList<Integer> vehicleImageId = new ArrayList<>();
    LayoutInflater layoutInflater;
    public ListOfVehiclesAdapter(Context ctx, ArrayList<Integer> vehicleImageId, ArrayList<String> vehicleTypeText, LayoutInflater layoutInflater){
        this.ctx = ctx;
        this.vehicleImageId = vehicleImageId;
        this.vehicleTypeText = vehicleTypeText;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return vehicleTypeText.size();
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
        convertView = layoutInflater.inflate(R.layout.list_items_for_types_of_vehicles,null);
        AppCompatImageView vehicleImage = (AppCompatImageView)convertView.findViewById(R.id.image_vehicle_type);
        TextView vehicleType = (TextView)convertView.findViewById(R.id.vehicle_type);

        vehicleImage.setImageResource(vehicleImageId.get(position));
        vehicleType.setText(vehicleTypeText.get(position));
        return convertView;
    }
}
