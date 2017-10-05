package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;
import java.util.List;

public class MyVehicleDetails extends BaseActivity {

//    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    VehiclesListAdapter adp;
    List<String> vRegistrationList;
    List<String> vModelList;
    ListView vehicleDetailsList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        vRegistrationList = new ArrayList<>();
        vModelList = new ArrayList<>();
        vehicleDetailsList = (ListView)findViewById(R.id.list_vehicles);

        databaseHelper = new DatabaseHelper(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyVehicleDetails.this, AddVehicle.class));
            }
        });

        useToolbar("My Vehicles");

    }


    @Override
    public void onResume(){
        super.onResume();
        getVehicleDetails();

    }

    @Override
    public void onPause(){
        super.onPause();
        vRegistrationList.clear();
        vModelList.clear();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void getVehicleDetails() {
                Cursor result = databaseHelper.getData();
                if (result.getCount() == 0) {
                    // show message
                    return;
                }
                while (result.moveToNext()) {
                    String vehicle = result.getString(2)+" "+result.getString(3);
                    vRegistrationList.add(result.getString(0));
                    vModelList.add(vehicle);
                }
       // Show all data
        adp = new VehiclesListAdapter(vRegistrationList,vModelList,getLayoutInflater());
        vehicleDetailsList.setAdapter(adp);
        vehicleDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MyVehicleDetails.this, "You have selected :"+vRegistrationList.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyVehicleDetails.this,UpdateVehicleDetailsActivity.class);
                intent.putExtra("regNumber",vRegistrationList.get(position));
                startActivity(intent);
            }
        });
    }

}
