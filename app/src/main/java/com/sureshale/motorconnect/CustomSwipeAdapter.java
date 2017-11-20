package com.sureshale.motorconnect;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sureshale on 02-11-2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {


    ArrayList<ImagesList> imagesLists = new ArrayList<>();
    List<String> uriList = new ArrayList<>();
    Bitmap images[];

    private Context ctx;
    int documentTypePosition;
    String regNumber, uriString,intentString;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx, String regNumber, int documentTypePosition,String intentString){
        this.ctx = ctx;
        this.regNumber = regNumber;
        this.documentTypePosition = documentTypePosition;
        this.intentString = intentString;
        getUri();
    }

//    public void getImages(){
//        DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
//        documentTypePosition = documentTypePosition+1;
//        System.out.println("Document position :"+documentTypePosition);
//        Cursor result=databaseHelper.getDocs(regNumber);
//            while (result.moveToNext()) {
//                byte[] blob = result.getBlob(documentTypePosition);
//                if (blob != null) {
//                    Bitmap bytes = ImageUtil.getImage(blob);
//                    imagesLists.add(new ImagesList(bytes));
//                }
//            }
//    }

    public void getUri(){
        DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String uriString;
        Cursor result;
        switch (documentTypePosition){
            case 0:
            result = db.rawQuery("select insuranceUri from documentsUri where regNumber = "+"'"+regNumber+"'",null);
            if (result.moveToFirst()==true) {
                while (result.moveToNext()) {
                    uriString = result.getString(0);
                    if (uriString!=null) {
                        uriList.add(uriString);
                    }
                }
            }
            break;

            case 1:
                result = db.rawQuery("select regCardUri from documentsUri where regNumber = "+"'"+regNumber+"'",null);
                if (result.moveToFirst()==true) {
                    while (result.moveToNext()) {
                        uriString = result.getString(0);
                        if (uriString!=null) {
                            uriList.add(uriString);
                        }
                    }
                }
                break;

            case 2:
                result = db.rawQuery("select pollutionCardUri from documentsUri where regNumber = "+"'"+regNumber+"'",null);
                if (result.moveToFirst()==true) {
                    while (result.moveToNext()) {
                        uriString = result.getString(0);
                        if (uriString!=null) {
                            uriList.add(uriString);
                        }
                    }
                }
                break;

            case 3:
                result = db.rawQuery("select warrantyCardUri from documentsUri where regNumber = "+"'"+regNumber+"'",null);
                if (result.moveToFirst()==true) {
                    while (result.moveToNext()) {
                        uriString = result.getString(0);
                        if (uriString!=null) {
                            uriList.add(uriString);
                        }
                    }
                }
                break;

            case 4:
                result = db.rawQuery("select permitCardUri from documentsUri where regNumber = "+"'"+regNumber+"'",null);
                if (result.moveToFirst()==true) {
                    while (result.moveToNext()) {
                        uriString = result.getString(0);
                        if (uriString!=null) {
                            uriList.add(uriString);
                        }
                    }
                }
                break;

        }

//        documentTypePosition = documentTypePosition+1;
//        System.out.println("Document position :"+documentTypePosition);
//        result=databaseHelper.get_imageUri(regNumber);

    }


    @Override
    public int getCount() {

//        return imagesLists.size();
        return uriList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.activity_documents_swipe_view,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.swipe_image_vew);
//        TextView textView = (TextView) item_view.findViewById(R.id.image_id);
        Button button = (Button)item_view.findViewById(R.id.viewPager_deleteBtn);
        uriString = uriList.get(position);
        if(uriString!=null) {

            try {
                imageView.setImageBitmap(getImage(Uri.parse(uriString)));

            } catch (IOException e) {
                e.printStackTrace();
            }
//            int i = position +1;
//            textView.setText("Image : "+i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
                int result = databaseHelper.deleteUri(regNumber,uriString,documentTypePosition);
                if (result>0){
                    Intent intent = new Intent(ctx,DocumentsActivity.class);
                    intent.putExtra("regNumber",regNumber);
                    intent.putExtra("yearOfManu",intentString);
                    ctx.startActivity(intent);
                    uriList.clear();
                }
                else{
                    Toast.makeText(ctx, "The document is not deleted !", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }


        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public Bitmap getImage(Uri uri) throws IOException {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);
            return bitmap;
    }


}
