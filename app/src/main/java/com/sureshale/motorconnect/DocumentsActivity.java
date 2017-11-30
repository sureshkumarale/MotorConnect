package com.sureshale.motorconnect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class DocumentsActivity extends AppCompatActivity {

    ListView documentsList;
    ArrayList<String> documentTypeValues;
    DatabaseHelper databaseHelper;
    String regNumber,intentString;
    int option = 0;
    Toolbar toolbar;
    Uri newUri;

    public static final int MY_REQUEST_CAMERA = 10;
    public static final int MY_REQUEST_WRITE_CAMERA = 11;

    public static final int MY_REQUEST_READ_GALLERY = 13;
    public static final int MY_REQUEST_WRITE_GALLERY = 14;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        toolbar = (Toolbar) findViewById(R.id.generic_appbar);
        toolbar.setTitle("Documents");

        regNumber = getIntent().getStringExtra("regNumber");
        intentString = getIntent().getStringExtra("yearOfManu");
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
                intent.putExtra("yearOfManu",intentString);
                startActivity(intent);
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
                        intent.putExtra("yearOfManu",intentString);
                        startActivity(intent);
                    }
                });
                convertView.setTag(viewHolder);
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
                    option = which;
                }
                else {
                    imageFromGallery(position);
                    option = which;
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
            newUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",getFilePath());
            intent.putExtra(MediaStore.EXTRA_OUTPUT,newUri);
            startActivityForResult(intent,pos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != RESULT_OK){
            Log.e("msg","Photo not available");
            return;
        }
        if(option == 0) {

            switch (requestCode) {
                case 0:
                    boolean isInserted0 = databaseHelper.insert_imageUri(regNumber,newUri.toString(),null,null,null,null);
                    if(isInserted0 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    boolean isInserted1 = databaseHelper.insert_imageUri(regNumber,null,newUri.toString(),null,null,null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    boolean isInserted2 = databaseHelper.insert_imageUri(regNumber,null,null,newUri.toString(),null,null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    boolean isInserted3 = databaseHelper.insert_imageUri(regNumber,null,null,null,newUri.toString(),null);
                    if(isInserted3 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    boolean isInserted4 = databaseHelper.insert_imageUri(regNumber,null,null,null,null,newUri.toString());
                    if(isInserted4 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else{
            Uri galleryImageUri;

            switch (requestCode){

                case 0:
                    File newFilePath0 = getFilePath();
                    try {
                        copyGalleryImageToApp(new File(getGalleryImagePath(data.getData())),newFilePath0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    galleryImageUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",newFilePath0);
                    boolean isInserted0 = databaseHelper.insert_imageUri(regNumber,galleryImageUri.toString(),null,null,null,null);
                    if(isInserted0 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    File newFilePath1 = getFilePath();
                    try {
                        copyGalleryImageToApp(new File(getGalleryImagePath(data.getData())),newFilePath1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    galleryImageUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",newFilePath1);
                    boolean isInserted1 = databaseHelper.insert_imageUri(regNumber,null,galleryImageUri.toString(),null,null,null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    File newFilePath2 = getFilePath();
                    try {
                        copyGalleryImageToApp(new File(getGalleryImagePath(data.getData())),newFilePath2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    galleryImageUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",newFilePath2);
                    boolean isInserted2 = databaseHelper.insert_imageUri(regNumber,null,null,galleryImageUri.toString(),null,null);
                    if(isInserted2 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    File newFilePath3 = getFilePath();
                    try {
                        copyGalleryImageToApp(new File(getGalleryImagePath(data.getData())),newFilePath3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    galleryImageUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",newFilePath3);
                    boolean isInserted3 = databaseHelper.insert_imageUri(regNumber,null,null,null,galleryImageUri.toString(),null);
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    File newFilePath4 = getFilePath();
                    try {
                        copyGalleryImageToApp(new File(getGalleryImagePath(data.getData())),newFilePath4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    galleryImageUri = FileProvider.getUriForFile(this,"com.sureshale.fileprovider",newFilePath4);
                    boolean isInserted4 = databaseHelper.insert_imageUri(regNumber,null,null,null,null,galleryImageUri.toString());
                    if(isInserted1 = true)Toast.makeText(this, "image saved @" +requestCode, Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this, "Something went Wrong, details not added", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    public File getFilePath(){
        File directory = new File(getApplicationContext().getFilesDir()+"/imageDir/");
        if(!directory.exists()){
            directory.mkdir();
        }
        File file = new File(directory,System.currentTimeMillis() + ".png");
        return file;
    }

    public String getGalleryImagePath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void copyGalleryImageToApp(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
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
