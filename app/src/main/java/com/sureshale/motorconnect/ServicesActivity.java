package com.sureshale.motorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 26-09-2017.
 */

public class ServicesActivity extends BaseActivity {

    Button servicingCenter, insuranceAgent, pollutionCheck, fuelStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        servicingCenter = (Button)findViewById(R.id.servicing_centers);
        insuranceAgent = (Button)findViewById(R.id.insurance_agents);
        pollutionCheck = (Button)findViewById(R.id.pollution_centers);
        fuelStation = (Button)findViewById(R.id.fuel_station);

        servicingCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesActivity.this, MapsActivity.class);
//                intent.putExtra("car_servicing","car servicing center");
                startActivity(intent);
            }
        });


        useToolbar("Services");
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
}
