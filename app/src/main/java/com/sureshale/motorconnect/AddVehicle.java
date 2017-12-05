package com.sureshale.motorconnect;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 27-07-2017.
 */

public class AddVehicle extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextInputLayout regNumberLayout, vehicleTypeSpinnerLayout, vehicleManufacturerLayout, vehicleModelLayout;
    DatabaseHelper databaseHelper;
    Spinner type,manufacturer,model,yearOfman;
    String vehicleTypeString, manufacturerString, vehicleModelString;
    Toolbar toolbar;
    Button btn;
    TextView vehicleTypeBtn, vehicleManufacturerBtn, vehicleModelBtn, registrationDate;
    EditText regNumber;
    private Vibrator vibrator;
    int RESULT_CODE_FOR_VEHICLE_TYPE = 0;
    int RESULT_CODE_MANUFACTURER = 11;
    int RESULT_CODE_VEHICLE_MODEL = 12;
    int vehicleTypeCode = 100;
    int manufacturerType,vehicleType = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_vehicle);

        toolbar = (Toolbar)findViewById(R.id.generic_appbar);
        toolbar.setTitle("Add New Vehicle Details");

        databaseHelper = new DatabaseHelper(AddVehicle.this);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        vehicleTypeBtn = (TextView) findViewById(R.id.vehicleTypeBtn);
        vehicleManufacturerBtn =(TextView) findViewById(R.id.vehicleManufacturerBtn);
        vehicleModelBtn = (TextView) findViewById(R.id.vehicleModelBtn);
        registrationDate = (TextView)findViewById(R.id.registration_date);

        regNumberLayout = (TextInputLayout)findViewById(R.id.regNumber_input_layout);
//        vehicleTypeSpinnerLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_vehicleType);
//        vehicleManufacturerLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_manufacturer);
//        vehicleModelLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_model);

        regNumber         = (EditText)findViewById(R.id.registrationNumber);
//        type        =(Spinner)findViewById(R.id.vehicleType);
//        manufacturer=(Spinner)findViewById(R.id.manufacturer);
//        model       =(Spinner)findViewById(R.id.model);
        yearOfman  =(Spinner)findViewById(R.id.year_of_manufacure);
        btn         =(Button)findViewById(R.id.addVehicleData);

//        type.setAdapter(new ArrayAdapter<String>(AddVehicle.this, android.R.layout.simple_list_item_1,
//                getResources().getStringArray(R.array.addVehicle_type)));
////        type.setAdapter(myAdapter);
//        type.setOnItemSelectedListener(this);

        addNewVehicleData();

        vehicleTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVehicle.this, ListOfVehiclesActivity.class);
                startActivityForResult(intent,RESULT_CODE_FOR_VEHICLE_TYPE);
            }
        });

        vehicleManufacturerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vehicleTypeCode == 100){
                    Toast.makeText(AddVehicle.this, "Please select vehicle Type !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(AddVehicle.this,ListOfManufacturersActivity.class);
                    intent.putExtra("vehicleTypeCode",vehicleTypeCode);
                    startActivityForResult(intent,RESULT_CODE_MANUFACTURER);
                }

            }
        });

        vehicleModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vehicleType == 100){
                    Toast.makeText(AddVehicle.this, "Please select Manufacturer Type !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(AddVehicle.this,ListOfVehicleModelsActivity.class);
                    intent.putExtra("vehicleTypeCode",vehicleType);
                    intent.putExtra("manufacturerTypeCode",manufacturerType);
                    startActivityForResult(intent,RESULT_CODE_VEHICLE_MODEL);
                }
            }
        });

        setDate(registrationDate);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE_FOR_VEHICLE_TYPE) {
            if (resultCode == Activity.RESULT_OK) {
                vehicleTypeString = data.getStringExtra("vehicleTypeText");
                vehicleTypeCode = data.getIntExtra("vehicleType",0);
                vehicleTypeBtn.setText(vehicleTypeString);
                vehicleManufacturerBtn.setText("Select");
                vehicleModelBtn.setText("Select");
                vehicleType = 100;
            }
        }
        else if(requestCode == RESULT_CODE_MANUFACTURER){
            if(resultCode == 11){
                manufacturerString = data.getStringExtra("manufacturerTypeText");
                vehicleType = data.getIntExtra("vehicleTypeCode",0);
                manufacturerType = data.getIntExtra("manufacturerTypeCode",0);
                vehicleManufacturerBtn.setText(manufacturerString);
                vehicleModelBtn.setText("Select");
            }
        }
        else if(requestCode == RESULT_CODE_VEHICLE_MODEL){
            if(resultCode == 12){
                vehicleModelString = data.getStringExtra("vehicleNameText");
                vehicleModelBtn.setText(vehicleModelString);
            }
        }
    }

    public void addNewVehicleData(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm() == true && validateRegNumber()==true) {
                    boolean isInserted = databaseHelper.insert_newVehicle_data(regNumber.getText().toString(),
                            vehicleTypeString,
                            manufacturerString,
                            vehicleModelString,
                            yearOfman.getSelectedItem().toString(),
                            registrationDate.getText().toString());
                    if (isInserted = true) {
                        Toast.makeText(AddVehicle.this, "Vehicle Details added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(AddVehicle.this, MyVehicleDetails.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(AddVehicle.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                }
//                if (validateForm() == true) {
//                boolean isInserted = databaseHelper.insert_newVehicle_data(regNumber.getText().toString(),
//                        type.getSelectedItem().toString(),
//                        manufacturer.getSelectedItem().toString(),
//                        model.getSelectedItem().toString(),
//                        yearOfman.getSelectedItem().toString());
//                    if (isInserted = true) {
//                    Toast.makeText(AddVehicle.this, "Vehicle Details added Successfully", Toast.LENGTH_SHORT).show();
//                    finish();
//                    Intent intent = new Intent(AddVehicle.this,MyVehicleDetails.class);
//                    startActivity(intent);
//                    } else
//                    Toast.makeText(AddVehicle.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private boolean validateRegNumber(){
        String validate = regNumber.getText().toString();
        Cursor result = databaseHelper.getData(validate);
        if(result.getCount() == 0){
            return true;
        }
        else {
            Toast.makeText(this, "This vehicle is already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @TargetApi(24)
    private void setDate(final TextView dateView) {
        final int systemYear = Calendar.getInstance().get(Calendar.YEAR);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddVehicle.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = year + "-" + month + "-" + dayOfMonth;
                        dateView.setText(date);
                        System.out.println("Assigned date::" + date);
                        if (year > systemYear) {
//                  dateView.setError("Please enter valid Date !");
                            Toast.makeText(AddVehicle.this, "Select valid date !", Toast.LENGTH_SHORT).show();
                        }
                    }

                }, year, month, day);
                dialog.show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        // TODO Auto-generated method stub
        String str1= type.getSelectedItem().toString();
        if(str1.contentEquals("Two-Wheeler")) {
            manufacturer.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.twoWheeler_manufacturer)));
//            myAdapter2.notifyDataSetChanged();
            manufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str2 = manufacturer.getSelectedItem().toString();
                    switch (str2){
                        case "Hero-Corp" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Hero_Corp)));
                            break;
                        case "Bajaj" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Bajaj)));
                            break;
                        case "Honda" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Honda_twoWheeler)));
                            break;
                        case "Suzuki" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Suzuki_twoWheeler)));
                            break;
                        case "Royal-Enfield" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Royal_Enfield)));
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if(str1.contentEquals("Four-Wheeler")) {
            manufacturer.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                    getResources().getStringArray(R.array.fourWheeler_manufacturer)));
//            myAdapter3.notifyDataSetChanged();
//            manufacturer.setAdapter(myAdapter3);
            manufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str3 = manufacturer.getSelectedItem().toString();
                    switch (str3){
                        case "Honda" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Honda_FourWheeler)));
                            break;
                        case "Maruti-Suzuki" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Maruti_Suzuki)));
                            break;
                        case "Hundai" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Hundai)));
                            break;
                        case "Renault" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Renault)));
                            break;
                        case "Toyota" :
                            model.setAdapter(new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1,
                                    getResources().getStringArray(R.array.Toyota)));
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private boolean validateForm(){

        if(!checkRegNumber()){
            vibrator.vibrate(120);
            return false;
        }
        if(vehicleType()== true) {

            if(vehicleManufacturer()==true){

                if(vehicleModel()==true){

                    if(yearofManufacture()==true){

                        if(registrationDate() == true){
                            return true;
                        }
                       else {
                            vibrator.vibrate(120);
                            return false;
                        }
                    }
                    else {
                        vibrator.vibrate(120);
                        return false;
                    }
                }
                else {
                    vibrator.vibrate(120);
                    return false;
                }
            }
            else {
            vibrator.vibrate(120);
            return false;
            }
        }
        else {
            vibrator.vibrate(120);
            return false;
        }

    }
    private boolean checkRegNumber(){
        if (regNumber.getText().toString().trim().isEmpty()){
            regNumberLayout.setErrorEnabled(true);
            regNumberLayout.setError("Please Enter Vehicle Registration Number");
            regNumber.setError("Valid input required !");
            return false;
        }
        regNumberLayout.setErrorEnabled(false);
        return true;
    }
    private boolean vehicleType(){
        if(vehicleTypeBtn.getText().toString().contentEquals("Select")){
            Toast.makeText(this, "Please select Vehicle Type", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(type.getSelectedItem().toString().isEmpty()||
//                type.getSelectedItem().toString().contentEquals("Select Vehicle Type")){
//            vehicleTypeSpinnerLayout.setErrorEnabled(true);
//            vehicleTypeSpinnerLayout.setError("Please select Vehicle Type");
//            return false;
//        }
//        vehicleTypeSpinnerLayout.setErrorEnabled(false);
        return true;
    }
    private boolean vehicleManufacturer(){
        if(vehicleManufacturerBtn.getText().toString().contentEquals("Select")){
            Toast.makeText(this, "Please select Manufacturer", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(manufacturer.getSelectedItem().toString().isEmpty() ||
//                manufacturer.getSelectedItem().toString().contentEquals("Select Manufacturer")){
//            vehicleManufacturerLayout.setErrorEnabled(true);
//            vehicleManufacturerLayout.setError("Please select Vehicle Manufacturer");
//            return false;
//        }
//        vehicleManufacturerLayout.setErrorEnabled(false);
        return true;
    }
    private boolean vehicleModel(){
        if(vehicleModelBtn.getText().toString().contentEquals("Select")){
            Toast.makeText(this, "Please select Model", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(model.getSelectedItem().toString().isEmpty() ||
//                model.getSelectedItem().toString().contentEquals("Select Model")){
//            vehicleModelLayout.setErrorEnabled(true);
//            vehicleModelLayout.setError("Please select Vehicle Model");
//            return false;
//        }
//        vehicleModelLayout.setErrorEnabled(false);
        return true;
    }
    private boolean yearofManufacture(){
        if(yearOfman.getSelectedItem().toString().isEmpty() ||
                yearOfman.getSelectedItem().toString().contentEquals("YYYY")){
            Toast.makeText(this, "Select year of Manufacture", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean registrationDate(){
        if (registrationDate.getText().toString().contentEquals("Select Date") ||
                registrationDate.getText().toString().isEmpty()){

            Toast.makeText(this, "Select Registration Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
