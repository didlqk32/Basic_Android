package com.example.myapplication;

import android.graphics.Bitmap;

public class Mainpage_data {
    private String diary_title;
    private String diary_content;
    private String Heartcount;
    private Bitmap diary_image;

    public Mainpage_data(String diary_title, String diary_content, String heartcount, Bitmap diary_image) {
        this.diary_title = diary_title;
        this.diary_content = diary_content;
        Heartcount = heartcount;
        this.diary_image = diary_image;
    }

    public String getDiary_title() {
        return diary_title;
    }

    public void setDiary_title(String diary_title) {
        this.diary_title = diary_title;
    }

    public String getDiary_content() {
        return diary_content;
    }

    public void setDiary_content(String diary_content) {
        this.diary_content = diary_content;
    }

    public String getHeartcount() {
        return Heartcount;
    }

    public void setHeartcount(String heartcount) {
        Heartcount = heartcount;
    }

    public Bitmap getDiary_image() {
        return diary_image;
    }

    public void setDiary_image(Bitmap diary_image) {
        this.diary_image = diary_image;
    }
}
