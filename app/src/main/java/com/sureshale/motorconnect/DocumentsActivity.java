package com.sureshale.motorconnect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DocumentsActivity extends AppCompatActivity {

    ListView documentsList;
    ArrayList<String> documentTypeValues;
    DatabaseHelper databaseHelper;
    String regNumber,intentString;
    int option = 0;
    Toolbar toolbar;

    public static final int MY_REQUEST_CAMERA = 10;
    public static final int MY_REQUEST_WRITE_CAMERA = 11;

    public static final int MY_REQUEST_READ_GALLERY = 13;
    public static final int MY_REQUEST_WRITE_GALLERY = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        toolbar.setTitle("Documents");

        regNumber = getIntent().getStringExtra("regNumber");
        intentString = getIntent().getStringExtra("string");
        databaseHelper = new DatabaseHelper(this);
        documentsList = (ListView)findViewById(R.id.documents_list);
        documentTypeValues = new ArrayList<>();
        documentTypeValues.add("Insurance");
        documentTypeValues.add("Registration Card");
        documentTypeValues.add("Pollution Card");
        documentTypeValues.add("Warranty Document");
        documentTypeValues.add("Permit Document");

        documentsList.setAdapter(new MyListAdapter(this,R.layout.activity_documents_list_items,documentTypeValues));

        documentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DocumentsActivity.this,DocumentsViewPagerActivity.class);
                intent.putExtra("regNumber",regNumber);
                intent.putExtra("documentType",position);
                startActivity(intent);
//                Toast.makeText(DocumentsActivity.this, "clicked on list item "+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class MyListAdapter extends ArrayAdapter<String>{

        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.documentType = (TextView)convertView.findViewById(R.id.document_type);
                viewHolder.documentType.setText(documentTypeValues.get(position));
                viewHolder.addBtn = (TextView)convertView.findViewById(R.id.add_document);
                viewHolder.viewBtn = (TextView)convertView.findViewById(R.id.view_document);
                viewHolder.addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialogList(position);

                    }
                });
                viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DocumentsActivity.this,DocumentsViewPagerActivity.class);
                        intent.putExtra("regNumber",regNumber);
                        intent.putExtra("documentType",position);
                        startActivity(intent);
//                        Toast.makeText(DocumentsActivity.this, "clicked on view "+position, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else {
                mainViewHolder = (ViewHolder)convertView.getTag();
                mainViewHolder.documentType.setText(getItem(position));

            }
            return convertView;
        }
    }

    public class ViewHolder{
        TextView documentType, addBtn, viewBtn;

    }

    public void alertDialogList(final int position){
        final CharSequence photo[] = new CharSequence[] {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DocumentsActivity.this);
        builder.setTitle("Select Photo");
        builder.setItems(photo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    imageFromCamera(position);
                }
                else {
                    imageFromGallery(position);
                }
            }
        });
        builder.show();
    }

    private void imageFromGallery(int pos){
        int readPermissionCheck = ContextCompat.checkSelfPermission(DocumentsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermissionCheck = ContextCompat.checkSelfPermission(DocumentsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(readPermissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DocumentsActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},MY_REQUEST_READ_GALLERY);
        }
        else if(writePermissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DocumentsActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_REQUEST_WRITE_GALLERY);

        }
        else {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent,pos);
        }
    }

    private void imageFromCamera(int pos){

        int writeStoragePermissionCheck = ContextCompat.checkSelfPermission(DocumentsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(DocumentsActivity.this,android.Manifest.permission.CAMERA);
        if(writeStoragePermissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DocumentsActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_REQUEST_WRITE_CAMERA);
        }
        else if(cameraPermissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DocumentsActivity.this,new String[]{android.Manifest.permission.CAMERA},MY_REQUEST_CAMERA);
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,pos);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        byte[] image = null;
        if(resultCode != RESULT_OK){
            Log.e("msg","Photo not available");
            return;
        }
        if(option == 1) {

            switch (requestCode) {
                case 0:
                    Bitmap insurancePhoto = (Bitmap) data.getExtras().get("data");
                    image = ImageUtil.getImageBytes(insurancePhoto);
                    boolean isInserted0 = databaseHelper.insert_documents(regNumber,image,null,null,null,null);
                    if(isInserted0 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    Bitmap registrationPhoto = (Bitmap) data.getExtras().get("data");
                    image = ImageUtil.getImageBytes(registrationPhoto);
                    boolean isInserted1 = databaseHelper.insert_documents(regNumber,null,image,null,null,null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    Bitmap pollutionPhoto = (Bitmap) data.getExtras().get("data");
                    image = ImageUtil.getImageBytes(pollutionPhoto);
                    boolean isInserted2 = databaseHelper.insert_documents(regNumber,null,null,image,null,null);
                    if(isInserted2 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    Bitmap warrantyPhoto = (Bitmap) data.getExtras().get("data");
                    image = ImageUtil.getImageBytes(warrantyPhoto);
                    boolean isInserted3 = databaseHelper.insert_documents(regNumber,null,null,null,image,null);
                    if(isInserted3 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    Bitmap permitPhoto = (Bitmap) data.getExtras().get("data");
                    image = ImageUtil.getImageBytes(permitPhoto);
                    boolean isInserted4 = databaseHelper.insert_documents(regNumber,null,null,null,null,image);
                    if(isInserted4 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
        else{
            switch (requestCode){
                case 0:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        image = ImageUtil.getBytes(inputStream);
                    } catch (Exception e) {
                        Log.e("", "Error while creating temp file", e);
                    }
                    boolean isInserted0 = databaseHelper.insert_documents(regNumber,image,null,null,null,null);
                    if(isInserted0 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        image = ImageUtil.getBytes(inputStream);
                    } catch (Exception e) {
                        Log.e("", "Error while creating temp file", e);
                    }
                    boolean isInserted1 = databaseHelper.insert_documents(regNumber,null,image,null,null,null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        image = ImageUtil.getBytes(inputStream);
                    } catch (Exception e) {
                        Log.e("", "Error while creating temp file", e);
                    }
                    boolean isInserted2 = databaseHelper.insert_documents(regNumber,null,null,image,null,null);
                    if(isInserted2 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        image = ImageUtil.getBytes(inputStream);
                    } catch (Exception e) {
                        Log.e("", "Error while creating temp file", e);
                    }
                    boolean isInserted3 = databaseHelper.insert_documents(regNumber,null,null,null,image,null);
                    if(isInserted3 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        image = ImageUtil.getBytes(inputStream);
                    } catch (Exception e) {
                        Log.e("", "Error while creating temp file", e);
                    }
                    boolean isInserted4 = databaseHelper.insert_documents(regNumber,null,null,null,null,image);
                    if(isInserted4 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

    }

    @Override
    public void onBackPressed(){

            Intent intent = new Intent(this, UpdateVehicleDetailsActivity.class);
            intent.putExtra("regNumber",regNumber);
            intent.putExtra("yearOfManu",intentString);
            startActivity(intent);
    }
}
