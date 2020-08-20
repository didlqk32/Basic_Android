package com.example.myapplication;

import android.graphics.Bitmap;

public class Mainpage_data {

    private String nickname;
    private String diary_title;
    private String diary_content;
    private String Heartcount;
    private Bitmap diary_image;
    private String diary_id;

    private String diary_date;

    public Mainpage_data(String nickname, String diary_title, String heartcount, Bitmap diary_image, String diary_content, String diary_id, String diary_date) {
        this.nickname = nickname;
        this.diary_title = diary_title;
        this.Heartcount = heartcount;
        this.diary_image = diary_image;
        this.diary_content = diary_content;
        this.diary_id = diary_id;
        this.diary_date = diary_date;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public void setDiary_date(String diary_date) {
        this.diary_date = diary_date;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
