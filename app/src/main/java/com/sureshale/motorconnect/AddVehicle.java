package com.sureshale.motorconnect;

import android.os.Bundle;
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

    DatabaseHelper databaseHelper;
    Spinner type,manufacturer,model,yearOfman;
    Toolbar toolbar;
    Button btn;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_new_vehicle);

        toolbar = (Toolbar)findViewById(R.id.generic_appbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(AddVehicle.this);

        et1         = (EditText)findViewById(R.id.editText1);
        type        =(Spinner)findViewById(R.id.vehicleType);
        manufacturer=(Spinner)findViewById(R.id.manufacturer);
        model       =(Spinner)findViewById(R.id.model);
        yearOfman  =(Spinner)findViewById(R.id.year_of_manufacure);
        btn         =(Button)findViewById(R.id.addVehicleData);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddVehicle.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.addVehicle_type));
        type.setAdapter(myAdapter);
        type.setOnItemSelectedListener(this);

        addNewVehicleData();

    }
    public void addNewVehicleData(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.insert_newVehicle_data(et1.getText().toString(),
                        type.getSelectedItem().toString(),
                        manufacturer.getSelectedItem().toString(),
                        model.getSelectedItem().toString(),
                        yearOfman.getSelectedItem().toString());
                if (isInserted=true) {
                    Toast.makeText(AddVehicle.this, "Vehicle Details added Successfully", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    yearOfman.setAdapter(null);
                    model.setAdapter(null);
                    manufacturer.setAdapter(null);
                    type.setAdapter(null);

                }
                else
                    Toast.makeText(AddVehicle.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
        // TODO Auto-generated method stub
        String str1= type.getSelectedItem().toString();
        if(str1.contentEquals("Two-Wheeler")) {
            ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.twoWheeler_manufacturer));
            myAdapter2.notifyDataSetChanged();
            manufacturer.setAdapter(myAdapter2);
            manufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str2 = manufacturer.getSelectedItem().toString();
                    switch (str2){
                        case "Hero-Corp" :
                            ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Hero_Corp));
                            model.setAdapter(myAdapter4);
                            break;
                        case "Bajaj" :
                            ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Bajaj));
                            model.setAdapter(myAdapter5);
                            break;
                        case "Honda" :
                            ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Honda_twoWheeler));
                            model.setAdapter(myAdapter6);
                            break;
                        case "Suzuki" :
                            ArrayAdapter<String> myAdapter7 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Suzuki_twoWheeler));
                            model.setAdapter(myAdapter7);
                            break;
                        case "Royal-Enfield" :
                            ArrayAdapter<String> myAdapter8 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Royal_Enfield));
                            model.setAdapter(myAdapter8);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        if(str1.contentEquals("Four-Wheeler")) {
            ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fourWheeler_manufacturer));
            myAdapter3.notifyDataSetChanged();
            manufacturer.setAdapter(myAdapter3);
            manufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str3 = manufacturer.getSelectedItem().toString();
                    switch (str3){
                        case "Honda" :
                            ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Honda_FourWheeler));
                            model.setAdapter(myAdapter4);
                            break;
                        case "Maruti-Suzuki" :
                            ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Maruti_Suzuki));
                            model.setAdapter(myAdapter5);
                            break;
                        case "Hundai" :
                            ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Hundai));
                            model.setAdapter(myAdapter6);
                            break;
                        case "Renault" :
                            ArrayAdapter<String> myAdapter7 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Renault));
                            model.setAdapter(myAdapter7);
                            break;
                        case "Toyota" :
                            ArrayAdapter<String> myAdapter8 = new ArrayAdapter<String>(AddVehicle.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Toyota));
                            model.setAdapter(myAdapter8);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String str1 = type.getSelectedItem().toString();
//        switch (str1){
//            case "Two-Wheeler" :
//                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.twoWheeler_manufacturer));
//                manufacturer.setAdapter(myAdapter2);
//                manufacturer.setOnItemSelectedListener(this);
//                String str2 = manufacturer.getSelectedItem().toString();
//                switch (str2){
//                    case "Hero-Corp" :
//                        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Hero_Corp));
//                        model.setAdapter(myAdapter4);
//                        break;
//                    case "Bajaj" :
//                        ArrayAdapter<String> myAdapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Bajaj));
//                        model.setAdapter(myAdapter5);
//                        break;
//                    case "Honda" :
//                        ArrayAdapter<String> myAdapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Honda_twoWheeler));
//                        model.setAdapter(myAdapter6);
//                        break;
//                    case "Suzuki" :
//                        ArrayAdapter<String> myAdapter7 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Suzuki_twoWheeler));
//                        model.setAdapter(myAdapter7);
//                        break;
//                    case "Royal-Enfield" :
//                        ArrayAdapter<String> myAdapter8 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Royal_Enfield));
//                        model.setAdapter(myAdapter8);
//                        break;
//                }
//                break;
//
//            case "Four-Wheeler" :
//                ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fourWheeler_manufacturer));
//                manufacturer.setAdapter(myAdapter3);
//                manufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
//                break;
//        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


}
