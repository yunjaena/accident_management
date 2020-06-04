package com.yunjaena.accident_management.data.network.entity;

import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private String imageString;

    public Image(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Image(String imageString){
        this.imageString = imageString;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
