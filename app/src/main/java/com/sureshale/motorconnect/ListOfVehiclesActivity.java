package com.sureshale.motorconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;

/**
 * Created by sureshale on 20-11-2017.
 */

public class ListOfVehiclesActivity extends BaseActivity {

    ListView listView;
    ArrayList<Integer> vehicleImageId = new ArrayList<>();
    ArrayList<String> vehicleTypeText = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_vehicles);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        useToolbar("Select Vehicle Type :");
        listView = (ListView) findViewById(R.id.types_of_vehicles);
        vehicleImageId.add(R.drawable.two_wheeler);
        vehicleImageId.add(R.drawable.four_wheeler);
        vehicleImageId.add(R.drawable.mini_truck);

        vehicleTypeText.add("Two Wheeler");
        vehicleTypeText.add("Four Wheeler");
        vehicleTypeText.add("Mini Truck");

        ListOfVehiclesAdapter adapter = new ListOfVehiclesAdapter(this,vehicleImageId,vehicleTypeText,getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("vehicleTypeText",vehicleTypeText.get(position));
                intent.putExtra("vehicleType",position);
                setResult(Activity.RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }

}
