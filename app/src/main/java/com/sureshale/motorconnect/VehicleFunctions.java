package com.sureshale.motorconnect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sureshale on 30-11-2017.
 */

public class VehicleFunctions {

    public void pucCheck(String lastPUCDate) throws ParseException {
//  Storing the date in database in YYYY-MM-DD format (ex: 2017-11-30)
        Date PUCDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastPUCDate);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date currentDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(currentDate);

        int years = currentDate1.getYear()-PUCDate.getYear();
        int months = currentDate1.getMonth()-PUCDate.getMonth();

//        If the last PUC of the vehicle is more than 170 days, then need to send notification to user
        if ((years*12 + months)*30 >=170){

//            do notify user for PUC check

        }

    }

    public void tyreCheck(String odometerReading, String lastTyreChange){



    }

    public void insuranceCheck(){

    }

}


