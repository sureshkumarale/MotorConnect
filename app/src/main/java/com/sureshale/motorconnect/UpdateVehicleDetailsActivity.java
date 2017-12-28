package com.sureshale.motorconnect;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Created by sureshale on 15-09-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class UpdateVehicleDetailsActivity extends BaseActivity {

    TextView type, lastInsuranceDate, lastPollutionDate, lastTyreChangeDate, lastWheelAlignmentDate, lastServicingDate;
    TextView textInsuranceDate, textPollutionDate;
    EditText meterReading;
    DatabaseHelper databaseHelper;
    String registrationNumber, string;
    Button updateButton, serviceHistoryBtn, documentsBtn;
    int mNotificationCount;
    static final String NOTIFICATION_COUNT = "notificationCount";
    private static final String TAG = "PUC Alarm";
    Date regDate;

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
        textInsuranceDate = (TextView)findViewById(R.id.text_insurance_date);
        textPollutionDate = (TextView)findViewById(R.id.text_pollution_date);
        string = getIntent().getExtras().get("yearOfManu").toString();
        registrationNumber = getIntent().getExtras().get("regNumber").toString();

        Cursor result = databaseHelper.getData(registrationNumber);
        String registrationDate = null;
        while (result.moveToNext()) {
            registrationDate = result.getString(6);
        }

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

        try {
           regDate = new SimpleDateFormat("yyyy-MM-dd").parse(registrationDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        Date systemDate = Calendar.getInstance().getTime();
        int diff = (int)(systemDate.getTime()/(24*60*60*1000)) - (int)(regDate.getTime()/(24*60*60*1000));

        System.out.println("difference Days :::"+diff);
        if (diff<=360){
            lastInsuranceDate.setFocusable(false);
            textInsuranceDate.setEnabled(false);
            lastPollutionDate.setFocusable(false);
            textPollutionDate.setEnabled(false);
            setDate(lastTyreChangeDate);

            setDate(lastWheelAlignmentDate);
            setDate(lastServicingDate);
        }else {
            setDate(lastInsuranceDate);
            setDate(lastPollutionDate);
            setDate(lastTyreChangeDate);
            setDate(lastWheelAlignmentDate);
            setDate(lastServicingDate);
        }
        updateVehicleDetails();

    }

    public void updateVehicleDetails() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servicing, insurance, pollution, tyreChange, tyreAlignment;

                if(lastServicingDate.getText().toString().isEmpty()){
                    servicing = null;
                }else servicing = lastServicingDate.getText().toString();

                if(lastInsuranceDate.getText().toString().isEmpty()){
                    insurance = null;
                }else insurance = lastInsuranceDate.getText().toString();

                if(lastPollutionDate.getText().toString().isEmpty()){
                    pollution = null;
                }else pollution = lastPollutionDate.getText().toString();

                if(lastTyreChangeDate.getText().toString().isEmpty()){
                    tyreChange = null;
                }else tyreChange = lastTyreChangeDate.getText().toString();

                if(lastWheelAlignmentDate.getText().toString().isEmpty()){
                    tyreAlignment = null;
                }else tyreAlignment = lastWheelAlignmentDate.getText().toString();

                try {
                    if(validateForm() == true) {
                        boolean isInserted = databaseHelper.update_vehicle_history(
                                systemDate,
                                registrationNumber,
                                servicing,
                                insurance,
                                pollution,
                                meterReading.getText().toString(),
                                tyreChange,
                                tyreAlignment);
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

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllVehicleDetails(registrationNumber);

    }

    public boolean validateForm() throws ParseException {
        if(validateServicingDate() == true){
            if(validateInsuranceDate() == true) {
                if(validatePollutionDate() == true) {
                    if(validateMeterReading() == true) {
                        if(validateTyreChangeDate() == true) {
                            if (validateWheelAlignmentDate() == true) {

                                return true;
                            } else return false;
                        }else return false;
                    }else return false;

                }else return false;

            }else return false;

        }else return false;

    }

    public boolean validateServicingDate() throws ParseException {

//        lastServicingDate
//        lastInsuranceDate
//        lastPollutionDate
//        meterReading
//        lastTyreChangeDate
//        lastWheelAlignmentDate

        String actualDate = lastServicingDate.getText().toString();
        if(!actualDate.isEmpty()) {
            String columnName = "lastServicingDate";
            String dateFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

                while (cursor.moveToNext()) {
                    dateFromDB = cursor.getString(0);
                }
            if (dateFromDB != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.parse(actualDate).before(sdf.parse(dateFromDB))) {
                    Toast.makeText(this, "Enter valid Servicing Date", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            }else return true;
        }else return true;

    }

    public boolean validateInsuranceDate() throws ParseException {
        String actualDate = lastInsuranceDate.getText().toString();
        if(!actualDate.isEmpty()) {
            String columnName = "lastInsuranceDate";
            String dateFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

            while (cursor.moveToNext()) {
                dateFromDB = cursor.getString(0);
            }
            if (dateFromDB != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.parse(actualDate).before(sdf.parse(dateFromDB))) {
                    Toast.makeText(this, "Enter valid Insurance Date", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            }else return true;
        }else return true;

    }

    public boolean validatePollutionDate() throws ParseException {
        String actualDate = lastPollutionDate.getText().toString();
        if(!actualDate.isEmpty()) {
            String columnName = "lastPollutionDate";
            String dateFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

            while (cursor.moveToNext()) {
                dateFromDB = cursor.getString(0);
            }
            if (dateFromDB != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.parse(actualDate).before(sdf.parse(dateFromDB))) {
                    Toast.makeText(this, "Enter valid Pollution Date", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            }else return true;
        }else return true;

    }

    public boolean validateMeterReading() {
        int actualValue = parseInt(meterReading.getText().toString());
        if (!meterReading.getText().toString().isEmpty()) {
            String columnName = "meterReading";
            String valueFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

            while (cursor.moveToNext()) {
                valueFromDB = cursor.getString(0);
            }
            if (valueFromDB != null) {
                if (actualValue <= parseInt(valueFromDB)) {
                    Toast.makeText(this, "Enter valid Reading value", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            } else return true;
        } else return true;

    }

    public boolean validateTyreChangeDate() throws ParseException {
        String actualDate = lastTyreChangeDate.getText().toString();
        if(!actualDate.isEmpty()) {
            String columnName = "lastTyreChangeDate";
            String dateFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

            while (cursor.moveToNext()) {
                dateFromDB = cursor.getString(0);
            }
            if (dateFromDB != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.parse(actualDate).before(sdf.parse(dateFromDB))) {
                    Toast.makeText(this, "Enter valid Insurance Date", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            }else return true;
        }else return true;

    }

    public boolean validateWheelAlignmentDate() throws ParseException {
        String actualDate = lastWheelAlignmentDate.getText().toString();
        if(!actualDate.isEmpty()) {
            String columnName = "lastWheelAlignmentDate";
            String dateFromDB = null;
            Cursor cursor = databaseHelper.getOrderByServiceHistory(registrationNumber, columnName);

            while (cursor.moveToNext()) {
                dateFromDB = cursor.getString(0);
            }
            if (dateFromDB != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.parse(actualDate).before(sdf.parse(dateFromDB))) {
                    Toast.makeText(this, "Enter valid Insurance Date", Toast.LENGTH_SHORT).show();
                    return false;

                } else return true;

            }else return true;
        }else return true;

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
            type.setText(result.getString(3) + " " + result.getString(4));
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
                    databaseHelper.deleteVehicle(row);
                    Intent intent = new Intent(UpdateVehicleDetailsActivity.this, MyVehicleDetails.class);
                    startActivity(intent);
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Deleted the " + row + " details !!", Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

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