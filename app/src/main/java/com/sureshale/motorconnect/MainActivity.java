package com.sureshale.motorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageButton;

import com.example.sureshale.motorconnect.R;

public class MainActivity extends BaseActivity {

    ImageButton ib1;
    ImageButton ib2;
    ImageButton ib3;
    ImageButton ib4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib1 = (ImageButton) findViewById(R.id.MyVehicle);
        ib2 = (ImageButton) findViewById(R.id.UpComing);
        ib3 = (ImageButton) findViewById(R.id.Services);

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyVehicleDetails.class);
                startActivity(intent);
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ServicesActivity.class);
                startActivity(intent);
            }
        });

        useToolbar("ConnectAnyVehicle");
   }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

}
