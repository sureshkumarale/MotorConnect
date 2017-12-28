package com.sureshale.motorconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sureshale on 06-11-2017.
 */

public class ServiceHistoryActivity extends AppCompatActivity{

    String regNumber,intentString;
    Toolbar toolbar;
    ListView serviceHistory;
    DatabaseHelper databaseHelper;
    ServiceHistoryListAdapter serviceHistoryListAdapter;
    String vModel;
    List<String> serviceDoneOnList;
    List<String> odometerList;
    List<String> tyreChangeOnList;
    List<String> wheelAlignmentOnList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_history);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        toolbar.setTitle("Service History");

        databaseHelper = new DatabaseHelper(this);

        regNumber = getIntent().getStringExtra("regNumber");
        intentString = getIntent().getStringExtra("yearOfManu");

        serviceHistory = (ListView)findViewById(R.id.list_service_history);

        serviceDoneOnList = new ArrayList<>();
        odometerList = new ArrayList<>();
        tyreChangeOnList = new ArrayList<>();
        wheelAlignmentOnList = new ArrayList<>();

    }

    @Override
    public void onResume(){
        super.onResume();
        getServiceHistory();

    }

    private void getServiceHistory() {
        Cursor cursor = databaseHelper.getServiceHistory(regNumber);
        Cursor cursor1 = databaseHelper.getData(regNumber);
        if (cursor.getCount() == 0) {
            // show message
            Toast.makeText(this, "No service history found !", Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()) {

                serviceDoneOnList.add(cursor.getString(2));
                odometerList.add(cursor.getString(5));
                tyreChangeOnList.add(cursor.getString(6));
                wheelAlignmentOnList.add(cursor.getString(7));
            }
        }
        while (cursor1.moveToNext()) {

            vModel = cursor1.getString(3)+" "+cursor1.getString(4);
        }
        serviceHistoryListAdapter = new ServiceHistoryListAdapter(regNumber,vModel,serviceDoneOnList,odometerList,tyreChangeOnList,wheelAlignmentOnList,getLayoutInflater());
        serviceHistory.setAdapter(serviceHistoryListAdapter);
    }

    @Override
    public void onBackPressed(){

            Intent intent = new Intent(this, UpdateVehicleDetailsActivity.class);
            intent.putExtra("regNumber",regNumber);
            intent.putExtra("yearOfManu",intentString);
            startActivity(intent);
    }
}
