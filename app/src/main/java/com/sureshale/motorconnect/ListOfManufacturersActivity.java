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

public class ListOfManufacturersActivity extends BaseActivity{

    ListView listView;
    ArrayList<Integer> manufacturerImageId = new ArrayList<>();
    ArrayList<String> manufacturerNameText = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_manufacturers);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        final int typeOfVehicle = getIntent().getIntExtra("vehicleTypeCode",0);

        useToolbar("Select Manufacturer :");
        listView = (ListView) findViewById(R.id.list_of_manufacturers);
        if(typeOfVehicle == 0) {

            manufacturerImageId.add(R.drawable.bajaj_logo);
            manufacturerImageId.add(R.drawable.honda_2wheeler_logo);


            manufacturerNameText.add("Bajaj");
            manufacturerNameText.add("Honda");
        }
        else if(typeOfVehicle == 1){
            manufacturerImageId.add(R.drawable.maruti_suzuki_logo);
            manufacturerImageId.add(R.drawable.honda_4wheeler_logo);


            manufacturerNameText.add("Maruti Suzuki");
            manufacturerNameText.add("Honda");
        }else {
            Toast.makeText(this, "Not Defined !!", Toast.LENGTH_SHORT).show();
        }


        ListOfVehiclesAdapter adapter = new ListOfVehiclesAdapter(this,manufacturerImageId,manufacturerNameText,getLayoutInflater());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("manufacturerTypeText",manufacturerNameText.get(position));
                intent.putExtra("vehicleTypeCode",typeOfVehicle);
                intent.putExtra("manufacturerTypeCode",position);
                setResult(11,intent);
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            }
        });

    }

}