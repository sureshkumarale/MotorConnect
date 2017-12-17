package com.sureshale.motorconnect;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.sureshale.motorconnect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sureshale on 13-12-2017.
 */

public class NotificationEventReceiver extends BroadcastReceiver {
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 1;
//    private static final int NOTIFICATION_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        int PUC_NOTIFICATION_ID = 1;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor cursorPUC = databaseHelper.getServiceHistoryForPUCNotification();
        while (cursorPUC.moveToNext()) {
            String dateString = cursorPUC.getString(0);
            if (!dateString.isEmpty()) {
                System.out.println("dateString PUC ::: " + dateString);
                Date pucDate = null;
                try {
                    pucDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date systemDate = android.icu.util.Calendar.getInstance().getTime();
                int diff = (int) (systemDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (pucDate.getTime() / (24 * 60 * 60 * 1000));

//        If the last PUC of the vehicle is more than 170 days, then need to send notification to user
                if (diff >= 170) {
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            PUC_NOTIFICATION_ID,
                            new Intent(context, MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle("Notification: PUC Check")
                            .setAutoCancel(true)
                            .setContentText("PUC Check for the vehicle :"+cursorPUC.getString(1))
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pendingIntent);


                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(PUC_NOTIFICATION_ID, builder.build());
                    PUC_NOTIFICATION_ID++;
                }
            }
        }

        int INSURANCE_NOTIFICATION_ID = 1;
        Cursor cursorInsurance = databaseHelper.getServiceHistoryForInsuranceNotification();
        while (cursorInsurance.moveToNext()) {
            String dateString = cursorInsurance.getString(0);
            if (!dateString.isEmpty()) {
                System.out.println("dateString Insurance ::: " + dateString);
                Date insuranceDate = null;
                try {
                    insuranceDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date systemDate = android.icu.util.Calendar.getInstance().getTime();
                int diff = (int) (systemDate.getTime() / (24 * 60 * 60 * 1000)) - (int) (insuranceDate.getTime() / (24 * 60 * 60 * 1000));

//        If the last Insurance of the vehicle is more than 170 days, then need to send notification to user
                if (diff >= 350) {
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,
                            INSURANCE_NOTIFICATION_ID,
                            new Intent(context, MainActivity.class),
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle("Notification: Insurance Check")
                            .setAutoCancel(true)
                            .setContentText("Insurance Check for the vehicle :"+cursorInsurance.getString(1))
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pendingIntent);


                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(INSURANCE_NOTIFICATION_ID, builder.build());
                    INSURANCE_NOTIFICATION_ID++;
                }
            }
        }
    }

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        System.out.println("Time :: "+calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationEventReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
