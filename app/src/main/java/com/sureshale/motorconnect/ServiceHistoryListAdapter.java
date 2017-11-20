package com.sureshale.motorconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sureshale.motorconnect.R;

import java.util.List;

/**
 * Created by sureshale on 06-11-2017.
 */

public class ServiceHistoryListAdapter extends BaseAdapter {

    String regNumb,vModel;
    List<String> serviceDoneOnList;
    List<String> odometerList;
    List<String> tyreChangeOnList;
    List<String> wheelAlignmentOnList;
    LayoutInflater inflater;

    public ServiceHistoryListAdapter(String regNumb,
                                     String vModel,
                                     List<String> serviceDoneOnList,
                                     List<String> odometerList,
                                     List<String> tyreChangeOnList,
                                     List<String> wheelAlignmentOnList,
                                     LayoutInflater inflater){

        this.regNumb = regNumb;
        this.vModel = vModel;
        this.serviceDoneOnList = serviceDoneOnList;
        this.odometerList = odometerList;
        this.tyreChangeOnList = tyreChangeOnList;
        this.wheelAlignmentOnList = wheelAlignmentOnList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return serviceDoneOnList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceDoneOnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View listView, ViewGroup parent) {
        listView = inflater.inflate(R.layout.activity_service_history_list_items,null);
        TextView regNumber = (TextView)listView.findViewById(R.id.regNumber_text);
        TextView vehicleModel = (TextView)listView.findViewById(R.id.vehicleType_text);
        TextView serviceDoneOn = (TextView)listView.findViewById(R.id.date_of_service);
        TextView odometer = (TextView)listView.findViewById(R.id.odometer_service);
        TextView tyreChangeOn = (TextView)listView.findViewById(R.id.tyreChange_servicing);
        TextView wheelAlignmentOn = (TextView)listView.findViewById(R.id.wheel_alignment_servicing);

        regNumber.setText(regNumb);
        vehicleModel.setText(vModel);
        serviceDoneOn.setText(serviceDoneOnList.get(position));
        odometer.setText(odometerList.get(position));
        tyreChangeOn.setText(tyreChangeOnList.get(position));
        wheelAlignmentOn.setText(wheelAlignmentOnList.get(position));

        return listView;
    }
}
