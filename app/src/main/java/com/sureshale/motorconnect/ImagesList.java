package com.sureshale.motorconnect;

import android.graphics.Bitmap;

/**
 * Created by sureshale on 05-11-2017.
 */

public class ImagesList {

    private Bitmap bitmap;

    public ImagesList(Bitmap b){
        this.bitmap = b;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
