package com.example.myapplication;

import android.graphics.Bitmap;
import android.net.Uri;

public class numberItem {
    String name;
    String number;
    Bitmap bitmap = null;

    public numberItem(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber(){
        return number;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
}

