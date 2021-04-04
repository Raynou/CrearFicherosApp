package com.example.tomarscreenyguardar;

import android.graphics.Bitmap;
import android.view.View;

public class TomarScreenshot {
    public static Bitmap tomarCaptura(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap((v.getDrawingCache()));
        v.setDrawingCacheEnabled(false);
        return b;
    }
     public static Bitmap devolverCaptura(View v){
        return tomarCaptura(v.getRootView());
    }

}
