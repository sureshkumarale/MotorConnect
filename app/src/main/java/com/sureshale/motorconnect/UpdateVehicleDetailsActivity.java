package com.sureshale.motorconnect;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.text.SimpleDateFormat;

/**
 * Created by sureshale on 15-09-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class UpdateVehicleDetailsActivity extends BaseActivity {

    TextView type, lastInsuranceDate, lastPollutionDate, lastTyreChangeDate, lastWheelAlignmentDate, lastServicingDate;
    EditText meterReading;
    DatabaseHelper databaseHelper;
    String registrationNumber, string;
    Button updateButton, serviceHistoryBtn, documentsBtn;
    AlarmManager alarmManager;
    int mNotificationCount;
    static final String NOTIFICATION_COUNT = "notificationCount";
    private static final String TAG = "PUC Alarm";

    String systemDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mNotificationCount = savedInstanceState.getInt(NOTIFICATION_COUNT);
        }
        setContentView(R.layout.activity_update_vehicle_details);

        databaseHelper = new DatabaseHelper(this);

        type = (TextView) findViewById(R.id.vehicleType);
        lastInsuranceDate = (TextView) findViewById(R.id.insurance_date);
        lastPollutionDate = (TextView) findViewById(R.id.pollution_date);
        meterReading = (EditText) findViewById(R.id.meter_reading);
        lastTyreChangeDate = (TextView) findViewById(R.id.tyre_change_date);
        lastWheelAlignmentDate = (TextView) findViewById(R.id.wheel_alignment_date);
        lastServicingDate = (TextView) findViewById(R.id.last_servicing_date);

        string = getIntent().getExtras().get("yearOfManu").toString();
        registrationNumber = getIntent().getExtras().get("regNumber").toString();

        useToolbar(registrationNumber);

        updateButton = (Button) findViewById(R.id.update_button);
        serviceHistoryBtn = (Button) findViewById(R.id.service_historyBtn);
        documentsBtn = (Button) findViewById(R.id.documentsBtn);

        serviceHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateVehicleDetailsActivity.this,ServiceHistoryActivity.class);
                intent.putExtra("regNumber", registrationNumber);
                intent.putExtra("yearOfManu", string);
                startActivity(intent);
            }
        });

        documentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateVehicleDetailsActivity.this, DocumentsActivity.class);
                intent.putExtra("regNumber", registrationNumber);
                intent.putExtra("yearOfManu", string);
                startActivity(intent);
            }
        });



        setDate(lastInsuranceDate);
        setDate(lastPollutionDate);
        setDate(lastTyreChangeDate);
        setDate(lastWheelAlignmentDate);
        setDate(lastServicingDate);

        updateVehicleDetails();

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(NOTIFICATION_COUNT, mNotificationCount);
        super.onSaveInstanceState(savedInstanceState);
    }

//    @Override
//    protected void onNewIntent( Intent intent ) {
//        Log.i( TAG, "onNewIntent(), intent = " + intent );
//        if (intent.getExtras() != null)
//        {
//            Log.i(TAG, "in onNewIntent = " + intent.getExtras().getString("test"));
//        }
//        super.onNewIntent( intent );
//        setIntent( intent );
//    }

    public void updateVehicleDetails() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = databaseHelper.update_vehicle_history(
                        systemDate,
                        registrationNumber,
                        lastServicingDate.getText().toString(),
                        lastInsuranceDate.getText().toString(),
                        lastPollutionDate.getText().toString(),
                        meterReading.getText().toString(),
                        lastTyreChangeDate.getText().toString(),
                        lastWheelAlignmentDate.getText().toString());
                if (isInserted = true) {
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Vehicle History Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdateVehicleDetailsActivity.this, MyVehicleDetails.class);
                    startActivity(intent);
                } else
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();


                System.out.println("Selected  insurance Date :::::" + lastInsuranceDate.getText().toString());
                System.out.println("Selected  pollution Date :::::" + lastPollutionDate.getText().toString());
                System.out.println("Selected  tyre change Date :::::" + lastTyreChangeDate.getText().toString());
                System.out.println("Selected  wheel alignment Date :::::" + lastWheelAlignmentDate.getText().toString());
                System.out.println("Selected  servicing Date :::::" + lastServicingDate.getText().toString());

                setAlarm();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllVehicleDetails(registrationNumber);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, MyVehicleDetails.class);
            startActivity(intent);
        }
    }

    @TargetApi(24)
    private void setDate(final TextView dateView) {

        final int yearOfManu = Integer.parseInt(string);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateVehicleDetailsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        dateView.setText(date);
                        System.out.println("Assigned date::" + date);
                        if (yearOfManu > year) {
//                               dateView.setError("Please enter valid Date !");
                            Toast.makeText(UpdateVehicleDetailsActivity.this, "Select valid date !", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, year, month, day);
                dialog.show();

            }
        });
    }

    public void getAllVehicleDetails(String column) {
        Cursor result = databaseHelper.getData(column);

        while (result.moveToNext()) {
            type.setText(result.getString(2) + " " + result.getString(3));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String row = registrationNumber;
        if (item.getItemId() == R.id.delete_vehicle) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateVehicleDetailsActivity.this);
            alertDialogBuilder.setIcon(R.drawable.ic_launcher);
            alertDialogBuilder.setTitle("Delete Vehicle Details:" + row + " !!");
            alertDialogBuilder.setMessage("Do you really want to delete this Vehicle Details Permanently?");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    databaseHelper.deleteRow(row);
                    Intent intent = new Intent(UpdateVehicleDetailsActivity.this, MyVehicleDetails.class);
                    startActivity(intent);
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Deleted the " + row + " details !!", Toast.LENGTH_SHORT).show();
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

    public void setAlarm(){
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(  this, 0, alarmIntent, 0);


        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 10);
        alarmStartTime.set(Calendar.MINUTE, 00);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
    }
    private int getInterval(){
//        int days = 1;
//        int hours = 24;
        int minutes = 60;
        int seconds = 60;
        int milliseconds = 1000;
//        int repeatMS = days * hours * minutes * seconds * milliseconds;
        int repeatMS = 2 * seconds * milliseconds;
        return repeatMS;
    }
}
