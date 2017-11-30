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

public class ListOfVehicleModelsAdapter extends BaseAdapter{

    Context ctx;
    ArrayList<Integer> vehicleImageId = new ArrayList<>();
    ArrayList<String> vehicleNameText = new ArrayList<>();
    LayoutInflater layoutInflater;
    public ListOfVehicleModelsAdapter(Context ctx, ArrayList<Integer> vehicleImageId, ArrayList<String> vehicleNameText, LayoutInflater layoutInflater){
        this.ctx = ctx;
        this.vehicleImageId = vehicleImageId;
        this.vehicleNameText = vehicleNameText;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return vehicleNameText.size();
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
        convertView = layoutInflater.inflate(R.layout.list_items_for_list_of_vehicle_models,null);
        AppCompatImageView vehicleImage = (AppCompatImageView)convertView.findViewById(R.id.image_of_vehicle);
        TextView vehicleType = (TextView)convertView.findViewById(R.id.name_of_vehicle);

        vehicleImage.setImageResource(vehicleImageId.get(position));
        vehicleType.setText(vehicleNameText.get(position));
        return convertView;
    }
}
