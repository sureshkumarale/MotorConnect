package com.sureshale.motorconnect;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sureshale.motorconnect.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sureshale on 02-11-2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {


    ArrayList<ImagesList> imagesLists = new ArrayList<>();
    Bitmap images[];

    private Context ctx;
    int documentTypePosition;
    String regNumber;
    private LayoutInflater layoutInflater;


    public CustomSwipeAdapter(Context ctx, String regNumber, int documentTypePosition){
        this.ctx = ctx;
        this.regNumber = regNumber;
        this.documentTypePosition = documentTypePosition;
        getImages();
    }


    public void getImages(){
        DatabaseHelper databaseHelper = new DatabaseHelper(ctx);
        byte[] blob;
        documentTypePosition = documentTypePosition+1;
        System.out.println("Document position :"+documentTypePosition);
        Cursor result=databaseHelper.getDocs(regNumber);
            while (result.moveToNext()) {
                blob = result.getBlob(documentTypePosition);
                if (blob != null) {
                    Bitmap bytes = ImageUtil.getImage(blob);
                    imagesLists.add(new ImagesList(bytes));
                }
            }
    }


    @Override
    public int getCount() {
        return imagesLists.size();
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
        imageView.setImageBitmap(imagesLists.get(position).getBitmap());
        TextView textView = (TextView) item_view.findViewById(R.id.image_id);
        int i = position +1;
        textView.setText("Image : "+i);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

}
