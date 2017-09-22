package com.sureshale.motorconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.sureshale.motorconnect.R;
import java.util.List;

/**
 * Created by sureshale on 13-09-2017.
 */

public class VehiclesListAdapter extends BaseAdapter {

    List<String> vRegistrationList;
    List<String> vModelList;
    LayoutInflater inflater;

      public VehiclesListAdapter(List<String> vRegistrationList, List<String> vModelList, LayoutInflater inflater){

        this.vRegistrationList = vRegistrationList;
        this.vModelList = vModelList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return vRegistrationList.size();
    }

    @Override
    public Object getItem(int position) {

        return vRegistrationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View listView, ViewGroup parent) {

        listView = inflater.inflate(R.layout.list_items,null);
        TextView regNumber = (TextView)listView.findViewById(R.id.regNumber);
        TextView vehicleModel = (TextView)listView.findViewById(R.id.vModel);

        regNumber.setText(vRegistrationList.get(position));
        vehicleModel.setText(vModelList.get(position));

        return listView;
    }
}
