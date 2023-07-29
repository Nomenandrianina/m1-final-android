package com.example.m1_final_android;

public class Setting_item {
    public int imageResId;
    public String title;

    public Setting_item(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }
}
