package com.sureshale.motorconnect;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 15-09-2017.
 */

public class ListItemActivity extends BaseActivity{

    TextView header,type,lastInsuranceDate,lastPollutionDate,lastTyreChangeDate,lastWheelAlignmentDate,lastServicingDate;
    EditText meterReading;
    DatabaseHelper databaseHelper;
    String headerText;
    Toolbar toolbar;
    Button updateButton;

//    DatePickerDialog.OnDateSetListener mDateSetListener1;
//    DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_activity);

        databaseHelper = new DatabaseHelper(this);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        header = (TextView)findViewById(R.id.header);
        type = (TextView)findViewById(R.id.vehicleType_2);
        lastInsuranceDate = (TextView)findViewById(R.id.insurance_date);
        lastPollutionDate = (TextView)findViewById(R.id.pollution_date);
        meterReading = (EditText)findViewById(R.id.meter_reading);
        lastTyreChangeDate = (TextView)findViewById(R.id.tyre_change_date);
        lastWheelAlignmentDate = (TextView)findViewById(R.id.wheel_alignment_date);
        lastServicingDate = (TextView)findViewById(R.id.last_servicing_date);
        updateButton = (Button)findViewById(R.id.update_button);

        headerText = getIntent().getExtras().get("regNumber").toString();
        toolbar.setTitle("Vehicle:"+headerText);

        setSupportActionBar(toolbar);
        header.setText(headerText);

        setDate(lastInsuranceDate);
        setDate(lastPollutionDate);
        setDate(lastTyreChangeDate);
        setDate(lastWheelAlignmentDate);
        setDate(lastServicingDate);

        updateVehicleDetails();

    }

    public void updateVehicleDetails(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = databaseHelper.update_vehicle_history(header.getText().toString(),
                        lastServicingDate.getText().toString(),
                        lastInsuranceDate.getText().toString(),
                        lastPollutionDate.getText().toString(),
                        meterReading.getText().toString(),
                        lastTyreChangeDate.getText().toString(),
                        lastWheelAlignmentDate.getText().toString());
                if (isInserted=true) {
                    Toast.makeText(ListItemActivity.this, "Vehicle History Updated Successfully", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(ListItemActivity.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();


                System.out.println("Selected  insurance Date :::::"+lastInsuranceDate.getText().toString());
                System.out.println("Selected  pollution Date :::::"+lastPollutionDate.getText().toString());
                System.out.println("Selected  pollution Date :::::"+lastTyreChangeDate.getText().toString());
                System.out.println("Selected  pollution Date :::::"+lastWheelAlignmentDate.getText().toString());
                System.out.println("Selected  pollution Date :::::"+lastServicingDate.getText().toString());
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        getAllVehicleDetails(headerText);

    }

    @TargetApi(24)
    private void setDate(final TextView dateView) {

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(ListItemActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            String date = month + "/" +dayOfMonth+ "/"+year;
                            dateView.setText(date);
                            System.out.println("Assigned date::"+date);
                        }
                    },year,month,date);
                dialog.show();

            }
        });
    }

//    @TargetApi(24)
//    private void setDate_pollution() {
//
//        pollutionDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int date = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(ListItemActivity.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener2,year,month,date);
//                dialog.show();
//
//            }
//        });
//        mDateSetListener2 = new DatePickerDialog.OnDateSetListener(){
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month+1;
//                String date = month + "/" +dayOfMonth+ "/"+year;
//                pollutionDate.setText(date);
//                System.out.println("Assigned date::"+date);
//            }
//        };
//    }

    public void getAllVehicleDetails(String column){
        Cursor result = databaseHelper.getData(column);

        while (result.moveToNext()){
            type.setText(result.getString(1));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String row = headerText;
        if(item.getItemId()==R.id.delete_vehicle){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListItemActivity.this);
            alertDialogBuilder.setTitle("Delete Vehicle Details:"+row+" !!");
            alertDialogBuilder.setMessage("Do you really want to delete this Vehicle Details Permanently?");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    databaseHelper.deleteRow(row);
                    Intent intent = new Intent(ListItemActivity.this,MyVehicleDetails.class);
                    startActivity(intent);
                    Toast.makeText(ListItemActivity.this, "Deleted the "+row+" details !!", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return true;
    }

}


