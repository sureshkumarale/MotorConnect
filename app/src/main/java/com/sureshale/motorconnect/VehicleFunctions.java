package com.sureshale.motorconnect;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sureshale on 30-11-2017.
 */

public class VehicleFunctions {

    Context ctx;
    DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void pucCheck(String lastPUCDate) throws ParseException {
//  Storing the date in database in YYYY-MM-DD format (ex: 2017-11-30)

        Date PUCDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastPUCDate);

        Date systemDate = Calendar.getInstance().getTime();
        int diff = (int)(systemDate.getTime()/(24*60*60*1000)) - (int)(PUCDate.getTime()/(24*60*60*1000));

//        If the last PUC of the vehicle is more than 170 days, then need to send notification to user
        if (diff >=170){

//            do notify user for PUC check

        }

    }

    public void tyreCheck(String odometerReading, String lastTyreChange){



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insuranceCheck(String lastInsuranceDate) throws ParseException {

        Date insuranceDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastInsuranceDate);

        Date systemDate = Calendar.getInstance().getTime();
        int diff = (int)(systemDate.getTime()/(24*60*60*1000)) - (int)(insuranceDate.getTime()/(24*60*60*1000));

//        If the last PUC of the vehicle is more than 170 days, then need to send notification to user
        if (diff >=350){

//            do notify user for insurance renewal

        }
    }

}


