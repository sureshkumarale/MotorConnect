package com.sureshale.motorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;

/**
 * Created by sureshale on 27-11-2017.
 */

public class ListOfVehicleModelsActivity extends BaseActivity{

    ListView listView;
    ArrayList<Integer> vehicleImageId = new ArrayList<>();
    ArrayList<String> vehicleNameText = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_vehicle_models);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        final int typeOfVehicle = getIntent().getIntExtra("vehicleTypeCode",0);
        final int typeOfManufacturer = getIntent().getIntExtra("manufacturerTypeCode",0);

        useToolbar("Select Vehicle Model :");
        listView = (ListView) findViewById(R.id.list_of_vehicle_models);
        if(typeOfVehicle == 0) {

            switch (typeOfManufacturer) {
                case 0:
                    vehicleImageId.add(R.drawable.bajaj_avenger_image);
                    vehicleImageId.add(R.drawable.bajaj_pulsar_150_image);
                    vehicleImageId.add(R.drawable.bajaj_pulsar_220_image);

                    vehicleNameText.add("Avenger");
                    vehicleNameText.add("Pulsar 150");
                    vehicleNameText.add("Pulsar 220");
                    break;

                case 1:
                    vehicleImageId.add(R.drawable.honda_2wheeler_logo);
                    vehicleImageId.add(R.drawable.honda_2wheeler_logo);
                    vehicleImageId.add(R.drawable.honda_2wheeler_logo);

                    vehicleNameText.add("Activa");
                    vehicleNameText.add("Dio");
                    vehicleNameText.add("CBR 150");
                    break;
            }
        }
        else if(typeOfVehicle == 1){

            switch (typeOfManufacturer) {
                case 0:
                    vehicleImageId.add(R.drawable.maruti_suzuki_logo);
                    vehicleImageId.add(R.drawable.maruti_suzuki_logo);

                    vehicleNameText.add("Swift");
                    vehicleNameText.add("Dzire");
                    break;

                case 1:
                    vehicleImageId.add(R.drawable.honda_4wheeler_logo);
                    vehicleImageId.add(R.drawable.honda_4wheeler_logo);

                    vehicleNameText.add("Brio");
                    vehicleNameText.add("Amaze");
                    break;
            }
        }else {
            Toast.makeText(this, "Not Defined !!", Toast.LENGTH_SHORT).show();
        }


        ListOfVehiclesAdapter adapter = new ListOfVehiclesAdapter(this,vehicleImageId,vehicleNameText,getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("vehicleNameText",vehicleNameText.get(position));
                setResult(12,intent);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }

}