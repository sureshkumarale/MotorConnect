package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
    List<String> yearOfManu;
    ListView vehicleDetailsList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle_details);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        vRegistrationList = new ArrayList<>();
        vModelList = new ArrayList<>();
        yearOfManu = new ArrayList<>();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void getVehicleDetails() {
        String email = new UserSharedPreference(MyVehicleDetails.this).getEmail();
        Cursor result = databaseHelper.getListOfVehicles(email);
        if (result.getCount() != 0) {


            while (result.moveToNext()) {
                String vehicle = result.getString(3) + " " + result.getString(4);
                vRegistrationList.add(result.getString(1));
                vModelList.add(vehicle);
                yearOfManu.add(result.getString(5));
            }
            // Show all data
            adp = new VehiclesListAdapter(vRegistrationList, vModelList, getLayoutInflater());
            vehicleDetailsList.setAdapter(adp);
            vehicleDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MyVehicleDetails.this, "You have selected :" + vRegistrationList.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyVehicleDetails.this, UpdateVehicleDetailsActivity.class);
                    intent.putExtra("regNumber", vRegistrationList.get(position));
                    intent.putExtra("yearOfManu", yearOfManu.get(position));
                    startActivity(intent);
                }
            });
        }
    }
}
