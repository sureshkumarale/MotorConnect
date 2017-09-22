package com.sureshale.motorconnect;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        View header = navigationView.getHeaderView(0);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        TextView userText = (TextView)header.findViewById(R.id.user_name);
//        userText.setText("Hello "+getIntent().getExtras().getString("userText"));
//        System.out.println("Logged User Name :::"+getIntent().getExtras().getString("userText"));

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyVehicleDetails.class);
                startActivity(intent);
            }
        });
   }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            finishAffinity();
//        }
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.home) {
//            // Handle the camera action
//        } else if (id == R.id.my_vehicle) {
//
//            Intent intent = new Intent(MainActivity.this,MyVehicleDetails.class);
//            startActivity(intent);
//
//        } else if (id == R.id.add_vehicle) {
//
//            Intent intent = new Intent(MainActivity.this,AddVehicle.class);
//            startActivity(intent);
//
//        } else if (id == R.id.share) {
//
//        } else if (id == R.id.my_profile) {
//
//        } else if (id == R.id.logout) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}
