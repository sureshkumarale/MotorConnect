package com.sureshale.motorconnect;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Date;

/**
 * Created by sureshale on 11-09-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String dataBase_Name = "admin.db";

//  UserSharedPreference Table
    public static final String table_user_details = "userDetails";
    public static final String inputUserName = "userName";
    public static final String inputEmail = "email";
    public static final String inputPhNumber = "phNumber";
    public static final String inputPassword = "password";

    // Table = List of vehicles details:::
    public static final String table_list_of_vehicles = "vehicleDetails";
    public static final String col_1 = "regNumber";
    public static final String col_2 = "vehicleType";
    public static final String col_3 = "vehicleManufacturer";
    public static final String col_4 = "model";
    public static final String col_5 = "yearOfman";

//    Table = Vehicle History details:::
    public static final String table_vehicle_history = "vehicleHistory";
    public static final String col_regNumber ="regNumber";
    public static final String col_a = "lastServicingDate";
    public static final String col_b = "lastInsuranceDate";
    public static final String col_c = "lastPollutionDate";
    public static final String col_d = "meterReading";
    public static final String col_e = "lastTyreChangeDate";
    public static final String col_f = "lastWheelAlignmentDate";

    public DatabaseHelper(Context context) {
        super(context, dataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table "+ table_list_of_vehicles +" (regNumber TEXT, vehicleType TEXT, vehicleManufacturer TEXT, model TEXT, yearOfman TEXT)");
        db.execSQL("create table "+ table_vehicle_history +" (regNumber TEXT, lastServicingDate TEXT, lastInsuranceDate TEXT, lastPollutionDate TEXT, meterReading TEXT, lastTyreChangeDate TEXT, lastWheelAlignmentDate TEXT)");
        db.execSQL("create table "+table_user_details+ " (userName TEXT, email TEXT, phNumber TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+ table_list_of_vehicles);
        onCreate(db);
    }

    public boolean insert_newUser_data(String userName, String email, String phNumber, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(inputUserName,userName);
        values.put(inputEmail,email);
        values.put(inputPhNumber,phNumber);
        values.put(inputPassword,password);
        long result = db.insert(table_user_details,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insert_newVehicle_data(String regNumber, String vehicleType, String vehicleManufacturer, String model, String yearOfman){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_1, regNumber);
        values.put(col_2,vehicleType);
        values.put(col_3,vehicleManufacturer);
        values.put(col_4,model);
        values.put(col_5,yearOfman);
        long result = db.insert(table_list_of_vehicles,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean update_vehicle_history(String regNumber,String lastServicingDate, String lastInsuranceDate, String lastPollutionDate, String meterReading, String lastTyreChangeDate, String lastWheelAlignmentDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_regNumber,regNumber);
        values.put(col_a,lastServicingDate);
        values.put(col_b,lastInsuranceDate);
        values.put(col_c,lastPollutionDate);
        values.put(col_d,meterReading);
        values.put(col_e,lastTyreChangeDate);
        values.put(col_f,lastWheelAlignmentDate);
        long result = db.insert(table_vehicle_history,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("select * from "+table_list_of_vehicles,null);
        return cursorResult;
    }

    public Cursor getData(String column){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorResult = db.rawQuery("select * from "+table_list_of_vehicles+" where regNumber = "+"'"+column+"'",null);
        return cursorResult;
    }

    public Cursor userValidation(String email_phone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("select * from "+table_user_details+" where email = " + "'" +email_phone+ "'" + " OR "+ "phNumber = " + "'"+email_phone+"'",null);
        return cursorResult;
    }

// public Cursor getUserData(String email, String phone){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursorResult = db.rawQuery();
//        return cursorResult;
//    }

    public void deleteRow(String regNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+table_list_of_vehicles+" where regNumber = "+"'"+regNumber+"'");
    }
}
