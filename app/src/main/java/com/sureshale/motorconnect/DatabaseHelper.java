package com.sureshale.motorconnect;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String systemDate = "sysDate";
    public static final String col_regNumber ="regNumber";
    public static final String col_a = "lastServicingDate";
    public static final String col_b = "lastInsuranceDate";
    public static final String col_c = "lastPollutionDate";
    public static final String col_d = "meterReading";
    public static final String col_e = "lastTyreChangeDate";
    public static final String col_f = "lastWheelAlignmentDate";

//    Table = documents for a specific vehicle
    public static final String table_documents = "vehicleDocuments";
    public static final String col1_regNumber ="regNumber";
    public static final String doc_insurance = "insuranceDoc";
    public static final String doc_registration = "registrationDoc";
    public static final String doc_pollution = "pollutionDoc";
    public static final String doc_warranty = "warrantyDoc";
    public static final String doc_permit = "permitDoc";

//    Test table for image Uri storage
    public static final String table_docs = "documentsUri";
    public static final String c1_regNumber = "regNumber";
    public static final String c2_imageUri = "insuranceUri";
    public static final String c3_imageUri = "regCardUri";
    public static final String c4_imageUri = "pollutionCardUri";
    public static final String c5_imageUri = "warrantyCardUri";
    public static final String c6_imageUri = "permitCardUri";

    public DatabaseHelper(Context context) {
        super(context, dataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table "+ table_list_of_vehicles +" (regNumber TEXT, vehicleType TEXT, vehicleManufacturer TEXT, model TEXT, yearOfman TEXT)");
        db.execSQL("create table "+ table_vehicle_history +" (sysDate TEXT, regNumber TEXT, " +
                "lastServicingDate TEXT, lastInsuranceDate TEXT, " +
                "lastPollutionDate TEXT, meterReading TEXT, lastTyreChangeDate TEXT, lastWheelAlignmentDate TEXT)");
        db.execSQL("create table "+ table_user_details + " (userName TEXT, email TEXT, phNumber TEXT, password TEXT)");
        db.execSQL("create table "+ table_documents + " (regNumber TEXT, insuranceDoc BLOB, registrationDoc BLOB, pollutionDoc BLOB, warrantyDoc BLOB, permitDoc BLOB)");

        db.execSQL("create table " + table_docs + "(regNumber TEXT, insuranceUri TEXT, regCardUri TEXT, pollutionCardUri TEXT, warrantyCardUri TEXT, permitCardUri TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists "+ table_list_of_vehicles);
        onCreate(db);
    }

    public boolean insert_imageUri(String regNumber, String insuranceUri, String registrationUri, String pollutionUri,String warrantyUri, String permitUri){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
                values.put(c1_regNumber,regNumber);
                values.put(c2_imageUri,insuranceUri);
                values.put(c3_imageUri,registrationUri);
                values.put(c4_imageUri,pollutionUri);
                values.put(c5_imageUri,warrantyUri);
                values.put(c6_imageUri,permitUri);
                long result = db.insert(table_docs,null,values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public int deleteUri(String regNumber, String uri, int column){
        SQLiteDatabase db = this.getReadableDatabase();
        int result = 0;
        switch (column){
            case 0:
                result = db.delete(table_docs,"regNumber = '"+regNumber+"' AND insuranceUri = '"+uri+"'",null);
                break;
            case 1:
                result = db.delete(table_docs,"regNumber = '"+regNumber+"' AND regCardUri = '"+uri+"'",null);
                break;
            case 2:
                result = db.delete(table_docs,"regNumber = '"+regNumber+"' AND pollutionCardUri = '"+uri+"'",null);
                break;
            case 3:
                result = db.delete(table_docs,"regNumber = '"+regNumber+"' AND warrantyCardUri = '"+uri+"'",null);
                break;
            case 4:
                result = db.delete(table_docs,"regNumber = '"+regNumber+"' AND permitCardUri = '"+uri+"'",null);
                break;
        }
        return result; 
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

    public boolean update_vehicle_history(String sysDate, String regNumber, String lastServicingDate, String lastInsuranceDate, String lastPollutionDate, String meterReading, String lastTyreChangeDate, String lastWheelAlignmentDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(systemDate,sysDate);
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

    public Cursor getUserData(String email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("select * from " + table_user_details + " where email = " + "'" + email + "'",null);
        return cursorResult;
    }

    public void updateUserData(String email, String newUserName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + table_user_details + " SET userName = " + "'" + newUserName + "'" + " where email = '" + email + "'");
    }

    public Cursor imageDetails(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("select * from " + table_vehicle_history + " where meterReading = '111'",null);
        return cursorResult;
    }


    public Cursor getServiceHistory(String regNumber){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorResult = db.rawQuery("SELECT * from "+ table_vehicle_history + " where regNumber = '" + regNumber + "'",null);
        if(cursorResult != null) {
            return cursorResult;
        }
        else {
            return null;
        }
    }

    public void deleteRow(String regNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+table_list_of_vehicles+" where regNumber = "+"'"+regNumber+"'");
    }
}
