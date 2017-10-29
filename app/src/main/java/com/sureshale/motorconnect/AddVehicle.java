package com.sureshale.motorconnect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

/**
 * Created by sureshale on 27-07-2017.
 */

public class AddVehicle extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextInputLayout regNumberLayout, vehicleTypeSpinnerLayout, vehicleManufacturerLayout, vehicleModelLayout;
    DatabaseHelper databaseHelper;
    Spinner type,manufacturer,model,yearOfman;
    Toolbar toolbar;
    Button btn;
    EditText regNumber;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_new_vehicle);

        toolbar = (Toolbar)findViewById(R.id.generic_appbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(AddVehicle.this);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        regNumberLayout = (TextInputLayout)findViewById(R.id.regNumber_input_layout);
        vehicleTypeSpinnerLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_vehicleType);
        vehicleManufacturerLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_manufacturer);
        vehicleModelLayout = (TextInputLayout)findViewById(R.id.spinner_input_layout_model);

        regNumber         = (EditText)findViewById(R.id.registrationNumber);
        type        =(Spinner)findViewById(R.id.vehicleType);
        manufacturer=(Spinner)findViewById(R.id.manufacturer);
        model       =(Spinner)findViewById(R.id.model);
        yearOfman  =(Spinner)findViewById(R.id.year_of_manufacure);
        btn         =(Button)findViewById(R.id.addVehicleData);

        type.setAdapter(new ArrayAdapter<String>(AddVehicle.this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.addVehicle_type)));
//        type.setAdapter(myAdapter);
        type.setOnItemSelectedListener(this);

        addNewVehicleData();

    }
    public void addNewVehicleData(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm() == true) {
                boolean isInserted = databaseHelper.insert_newVehicle_data(regNumber.getText().toString(),
                        type.getSelectedItem().toString(),
                        manufacturer.getSelectedItem().toString(),
                        model.getSelectedItem().toString(),
                        yearOfman.getSelectedItem().toString());
                if (isInserted = true) {
                    Toast.makeText(AddVehicle.this, "Vehicle Details added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(AddVehicle.this,MyVehicleDetails.class);
                    startActivity(intent);

                } else
                    Toast.makeText(AddVehicle.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
            }
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

        if(!checkName()){
            vibrator.vibrate(120);
            return false;
        }
        if(vehicleType()== true) {

            if(vehicleManufacturer()==true){

                if(vehicleModel()==true){

                    if(yearofManufacture()==true){
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
    private boolean checkName(){
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
        if(type.getSelectedItem().toString().isEmpty()||
                type.getSelectedItem().toString().contentEquals("Select Vehicle Type")){
            vehicleTypeSpinnerLayout.setErrorEnabled(true);
            vehicleTypeSpinnerLayout.setError("Please select Vehicle Type");
            return false;
        }
        vehicleTypeSpinnerLayout.setErrorEnabled(false);
        return true;
    }
    private boolean vehicleManufacturer(){
        if(manufacturer.getSelectedItem().toString().isEmpty() ||
                manufacturer.getSelectedItem().toString().contentEquals("Select Manufacturer")){
            vehicleManufacturerLayout.setErrorEnabled(true);
            vehicleManufacturerLayout.setError("Please select Vehicle Manufacturer");
            return false;
        }
        vehicleManufacturerLayout.setErrorEnabled(false);
        return true;
    }
    private boolean vehicleModel(){
        if(model.getSelectedItem().toString().isEmpty() ||
                model.getSelectedItem().toString().contentEquals("Select Model")){
            vehicleModelLayout.setErrorEnabled(true);
            vehicleModelLayout.setError("Please select Vehicle Model");
            return false;
        }
        vehicleModelLayout.setErrorEnabled(false);
        return true;
    }
    private boolean yearofManufacture(){
        if(yearOfman.getSelectedItem().toString().isEmpty() ||
                yearOfman.getSelectedItem().toString().contentEquals("0000")){
            Toast.makeText(this, "Please select year of Manufacture of vehicle", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
