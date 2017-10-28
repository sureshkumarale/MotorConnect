package com.sureshale.motorconnect;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by sureshale on 15-09-2017.
 */

public class UpdateVehicleDetailsActivity extends BaseActivity {

    TextView type,lastInsuranceDate,lastPollutionDate,lastTyreChangeDate,lastWheelAlignmentDate,lastServicingDate;
    EditText meterReading;
    DatabaseHelper databaseHelper;
    String headerText;
    Button updateButton;
    Button insuranceImageBtn, pollutionImageBtn;
    private static final int SELECT_PICTURE = 100;

    public static final int MY_REQUEST_CAMERA = 10;
    public static final int MY_REQUEST_WRITE_CAMERA = 11;
    public static final int CAPTURE_CAMERA = 12;

    public static final int MY_REQUEST_READ_GALLERY = 13;
    public static final int MY_REQUEST_WRITE_GALLERY = 14;
    public static final int MY_REQUEST_GALLERY = 15;

    File filen;
    public byte[] insuranceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle_details);

        databaseHelper = new DatabaseHelper(this);

        type = (TextView)findViewById(R.id.vehicleType);
        lastInsuranceDate = (TextView)findViewById(R.id.insurance_date);
        lastPollutionDate = (TextView)findViewById(R.id.pollution_date);
        meterReading = (EditText)findViewById(R.id.meter_reading);
        lastTyreChangeDate = (TextView)findViewById(R.id.tyre_change_date);
        lastWheelAlignmentDate = (TextView)findViewById(R.id.wheel_alignment_date);
        lastServicingDate = (TextView)findViewById(R.id.last_servicing_date);
        updateButton = (Button)findViewById(R.id.update_button);
        insuranceImageBtn = (Button)findViewById(R.id.add_insurance_image);
        pollutionImageBtn = (Button)findViewById(R.id.add_pollution_image);

        headerText = getIntent().getExtras().get("regNumber").toString();
        useToolbar(headerText);

        setDate(lastInsuranceDate);
        setDate(lastPollutionDate);
        setDate(lastTyreChangeDate);
        setDate(lastWheelAlignmentDate);
        setDate(lastServicingDate);

       insuranceImageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               alertDialogList();
           }
       });

       pollutionImageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });
        updateVehicleDetails();

    }

    public void alertDialogList(){
        final CharSequence photo[] = new CharSequence[] {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateVehicleDetailsActivity.this);
        builder.setTitle("Select Photo");
        builder.setItems(photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){

                    imageFromCamera();
                }
                else {
                    imageFromGallery();
                }
            }
        });
        builder.show();
    }

    public void imageFromCamera(){
        checkPermissionCameraWrite();
    }

    public void imageFromGallery(){
        checkPermissionReadGallery();
    }

    private void checkPermissionReadGallery(){
        int permissionCheck = ContextCompat.checkSelfPermission(UpdateVehicleDetailsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateVehicleDetailsActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},MY_REQUEST_READ_GALLERY);
        }
        else {
            checkPermissionWriteGallery();
        }
    }
    private void checkPermissionWriteGallery(){
        int permissionCheck = ContextCompat.checkSelfPermission(UpdateVehicleDetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateVehicleDetailsActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_REQUEST_WRITE_GALLERY);
        }
        else {
            getPhotos();
        }
    }

    private void getPhotos(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,MY_REQUEST_GALLERY);
    }

    private void checkPermissionCameraWrite(){
        int permissionCheck = ContextCompat.checkSelfPermission(UpdateVehicleDetailsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateVehicleDetailsActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_REQUEST_WRITE_CAMERA);
        }
        else {
            checkPermissionCA();
        }
    }
    private void checkPermissionCA(){
        int permissionCheck = ContextCompat.checkSelfPermission(UpdateVehicleDetailsActivity.this,android.Manifest.permission.CAMERA);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UpdateVehicleDetailsActivity.this,new String[]{android.Manifest.permission.CAMERA},MY_REQUEST_CAMERA);
        }
        else {
            catchPhoto();
        }
    }
    private void catchPhoto(){
//        filen = getFile();
        if(true){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
//                Uri photocUri = Uri.fromFile(filen);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,photocUri);
                startActivityForResult(intent,CAPTURE_CAMERA);
            } catch (ActivityNotFoundException e){

            }
        } else {
            Toast.makeText(this, "Please Check your SDcard status !", Toast.LENGTH_SHORT).show();
        }
    }

//    public File getFile(){
//        File fileDir = new File(Environment.getExternalStorageDirectory()
//                +"/Android/data/"
//                +getApplicationContext().getPackageName()
//                +"/Files");
//        if(!fileDir.exists()){
//            if(!fileDir.mkdir()){
//                return null;
//            }
//        }
//
//        File mediaFile = new File(fileDir.getPath()+File.separator+"temp.jpg");
//        return mediaFile;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK){
            Log.e("msg","Photo not available");
            return;
        }
        switch (requestCode){
            case CAPTURE_CAMERA:
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                insuranceImage = ImageUtil.getImageBytes(photo);
                break;
            case MY_REQUEST_GALLERY:
                try {

                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
//                    filen = getFile();
//                    FileOutputStream fileOutputStream = new FileOutputStream(filen);
                    insuranceImage = ImageUtil.getBytes(inputStream);

//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//                    while ((bytesRead = inputStream.read(buffer)) != -1){
//                        fileOutputStream.write(buffer,0,bytesRead);
//                    }
//                    fileOutputStream.close();
//                    inputStream.close();

                }catch (Exception e){
                    Log.e("","Error while creating temp file",e);
                }
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){

        switch (requestCode){
            case MY_REQUEST_CAMERA:
                catchPhoto();
                break;
            case MY_REQUEST_WRITE_CAMERA:
                checkPermissionCA();
                break;
            case MY_REQUEST_READ_GALLERY:
                checkPermissionWriteGallery();
                break;
            case MY_REQUEST_WRITE_GALLERY:
                getPhotos();
                break;
        }
    }
    public void updateVehicleDetails(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = databaseHelper.update_vehicle_history(headerText,
                        lastServicingDate.getText().toString(),
                        lastInsuranceDate.getText().toString(),
                        lastPollutionDate.getText().toString(),
                        meterReading.getText().toString(),
                        lastTyreChangeDate.getText().toString(),
                        lastWheelAlignmentDate.getText().toString(),
                        insuranceImage);
                if (isInserted=true) {
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Vehicle History Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(UpdateVehicleDetailsActivity.this,MyVehicleDetails.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();


                System.out.println("Selected  insurance Date :::::"+lastInsuranceDate.getText().toString());
                System.out.println("Selected  pollution Date :::::"+lastPollutionDate.getText().toString());
                System.out.println("Selected  tyre change Date :::::"+lastTyreChangeDate.getText().toString());
                System.out.println("Selected  wheel alignment Date :::::"+lastWheelAlignmentDate.getText().toString());
                System.out.println("Selected  servicing Date :::::"+lastServicingDate.getText().toString());
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        getAllVehicleDetails(headerText);

    }

    @Override
    public void onBackPressed(){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            Intent intent = new Intent(this, MyVehicleDetails.class);
            startActivity(intent);
        }
    }

    @TargetApi(24)
    private void setDate(final TextView dateView) {

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(UpdateVehicleDetailsActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            month = month+1;
                            String date = year + "-" +month+ "-"+dayOfMonth;
                            dateView.setText(date);
                            System.out.println("Assigned date::"+date);
                        }
                    },year,month,date);
                dialog.show();

            }
        });
    }

    public void getAllVehicleDetails(String column){
        Cursor result = databaseHelper.getData(column);

        while (result.moveToNext()){
            type.setText(result.getString(2)+" "+result.getString(3));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vehicle_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final String row = headerText;
        if(item.getItemId()==R.id.delete_vehicle){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateVehicleDetailsActivity.this);
            alertDialogBuilder.setIcon(R.drawable.warning_24px);
            alertDialogBuilder.setTitle("Delete Vehicle Details:"+row+" !!");
            alertDialogBuilder.setMessage("Do you really want to delete this Vehicle Details Permanently?");
            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    databaseHelper.deleteRow(row);
                    Intent intent = new Intent(UpdateVehicleDetailsActivity.this,MyVehicleDetails.class);
                    startActivity(intent);
                    Toast.makeText(UpdateVehicleDetailsActivity.this, "Deleted the "+row+" details !!", Toast.LENGTH_SHORT).show();
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


    // Show simple message using SnackBar
    void showMessage(String message) {
//        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
//        snackbar.show();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PICTURE);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//
//                Uri selectedImageUri = data.getData();
//
//                if (null != selectedImageUri) {
//
//                    // Saving to Database...
//                    if (saveImageInDB(selectedImageUri)) {
//                        showMessage("Image Saved in Database...");
//                        imgView.setImageURI(selectedImageUri);
//                    }
//
//                    // Reading from Database after 3 seconds just to show the message
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (loadImageFromDB()) {
//                                showMessage("Image Loaded from Database...");
//                            }
//                        }
//                    }, 3000);
//                }
//
//            }
//        }
//    }
//
//
//
//    // Save the
//    Boolean saveImageInDB(Uri selectedImageUri) {
//
//        try {
//            dbHelper.open();
//            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
//            byte[] inputData = Utils.getBytes(iStream);
//            dbHelper.insertImage(inputData);
//            dbHelper.close();
//            return true;
//        } catch (IOException ioe) {
//            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
//            dbHelper.close();
//            return false;
//        }
//
//    }
//
//    Boolean loadImageFromDB() {
//        try {
//            dbHelper.open();
//            byte[] bytes = dbHelper.retreiveImageFromDB();
//            dbHelper.close();
//            // Show Image from DB in ImageView
//            imgView.setImageBitmap(Utils.getImage(bytes));
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
//            dbHelper.close();
//            return false;
//        }
//    }

}


