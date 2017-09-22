package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;
import java.util.List;

public class MyVehicleDetails extends BaseActivity {

    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    VehiclesListAdapter adp;
    List<String> vRegistrationList;
    List<String> vModelList;
    ListView vehicleDetailsList;
    FloatingActionButton fab;

//    ExpandableListView expandableListView;
//    List<String> parent;
//    Map<String,List<String>> child;
//    VehiclesExpandableListAdapter newadp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        vRegistrationList = new ArrayList<>();
        vModelList = new ArrayList<>();
        vehicleDetailsList = (ListView)findViewById(R.id.list_vehicles);
//
//        expandableListView = (ExpandableListView)findViewById(R.id.expandable_list_view);
//        parent = new ArrayList<>();
//        child = new HashMap<>();

        databaseHelper = new DatabaseHelper(this);
//        registerForContextMenu(vehicleDetailsList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyVehicleDetails.this, AddVehicle.class));
            }
        });

    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getMenuInflater().inflate(R.menu.context_delete_update, menu);
//        menu.setHeaderTitle("Options");
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.update :
//                break;
//
//            case R.id.delete :
//                break;
//        }
//        return super.onContextItemSelected(item);
//    }


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
                Intent intent = new Intent(MyVehicleDetails.this,ListItemActivity.class);
                intent.putExtra("regNumber",vRegistrationList.get(position));
                startActivity(intent);
            }
        });
    }

//    public void getVehicleDetails() {
//                Cursor result = databaseHelper.getData();
//                List<String> childList = new ArrayList<>();
//                if (result.getCount() == 0) {
//                    return;
//                }
//                while (result.moveToNext()) {
//                    parent.add(result.getString(0)+"\n"+result.getString(2)+" "+result.getString(3));
//                    childList.add(result.getString(1));
//                    child.put("Type : ",childList);
//                }
//            newadp = new VehiclesExpandableListAdapter(parent,child,this);
//        expandableListView.setAdapter(newadp);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//            startActivity(new Intent(MyVehicleDetails.this, AddVehicle.class));
//
//            return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.vehicle_details_menu,menu);
//        return true;
//
//    }


}
