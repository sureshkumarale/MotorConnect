package com.sureshale.motorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 22-09-2017.
 */

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        if(useToolbar())
        {
            setSupportActionBar(toolbar);
            setTitle("Activity Title");
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, fullView, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            fullView.setDrawerListener(toggle);
            toggle.syncState();
        }
        else{
            toolbar.setVisibility(View.GONE);
        }

    }

    protected boolean useToolbar()
    {
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.my_vehicle) {

            Intent intent = new Intent(this,MyVehicleDetails.class);
            startActivity(intent);

        } else if (id == R.id.add_vehicle) {

            Intent intent = new Intent(this,AddVehicle.class);
            startActivity(intent);

        } else if (id == R.id.share) {

        } else if (id == R.id.my_profile) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
