package com.example.myapplication;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;

public class HomeTraining_data {
    private String homet_title,homet_level_text,homet_timenum,homet_timetext,homet_timenum2,homet_timetext2;
    private int thumbnail_image,homet_clock_item;
//    private ArrayList<ImageView> stararrayList;
    private int homet_level_star1;
    private int homet_level_star2;
    private int homet_level_star3;
    private int homet_level_star4;
    private int homet_level_star5;

    private int homet_bookmark_image;

    public HomeTraining_data(int homet_bookmark_image, String homet_title, String homet_level_text, String homet_timenum, String homet_timetext, String homet_timenum2, String homet_timetext2, int thumbnail_image, int homet_clock_item, int homet_level_star1, int homet_level_star2, int homet_level_star3, int homet_level_star4, int homet_level_star5) {
        this.homet_bookmark_image = homet_bookmark_image;
        this.homet_title = homet_title;
        this.homet_level_text = homet_level_text;
        this.homet_timenum = homet_timenum;
        this.homet_timetext = homet_timetext;
        this.homet_timenum2 = homet_timenum2;
        this.homet_timetext2 = homet_timetext2;
        this.thumbnail_image = thumbnail_image;
        this.homet_clock_item = homet_clock_item;
        this.homet_level_star1 = homet_level_star1;
        this.homet_level_star2 = homet_level_star2;
        this.homet_level_star3 = homet_level_star3;
        this.homet_level_star4 = homet_level_star4;
        this.homet_level_star5 = homet_level_star5;
    }

    public HomeTraining_data(int homet_bookmark_image, String homet_title, String homet_level_text, String homet_timenum, String homet_timetext, String homet_timenum2, String homet_timetext2, int thumbnail_image, int homet_clock_item, int homet_level_star1, int homet_level_star2, int homet_level_star3, int homet_level_star4) {
        this.homet_bookmark_image = homet_bookmark_image;
        this.homet_title = homet_title;
        this.homet_level_text = homet_level_text;
        this.homet_timenum = homet_timenum;
        this.homet_timetext = homet_timetext;
        this.homet_timenum2 = homet_timenum2;
        this.homet_timetext2 = homet_timetext2;
        this.thumbnail_image = thumbnail_image;
        this.homet_clock_item = homet_clock_item;
        this.homet_level_star1 = homet_level_star1;
        this.homet_level_star2 = homet_level_star2;
        this.homet_level_star3 = homet_level_star3;
        this.homet_level_star4 = homet_level_star4;
    }
    public HomeTraining_data(int homet_bookmark_image, String homet_title, String homet_level_text, String homet_timenum, String homet_timetext, String homet_timenum2, String homet_timetext2, int thumbnail_image, int homet_clock_item, int homet_level_star1, int homet_level_star2, int homet_level_star3) {
        this.homet_bookmark_image = homet_bookmark_image;
        this.homet_title = homet_title;
        this.homet_level_text = homet_level_text;
        this.homet_timenum = homet_timenum;
        this.homet_timetext = homet_timetext;
        this.homet_timenum2 = homet_timenum2;
        this.homet_timetext2 = homet_timetext2;
        this.thumbnail_image = thumbnail_image;
        this.homet_clock_item = homet_clock_item;
        this.homet_level_star1 = homet_level_star1;
        this.homet_level_star2 = homet_level_star2;
        this.homet_level_star3 = homet_level_star3;
    }
    public HomeTraining_data(int homet_bookmark_image, String homet_title, String homet_level_text, String homet_timenum, String homet_timetext, String homet_timenum2, String homet_timetext2, int thumbnail_image, int homet_clock_item, int homet_level_star1, int homet_level_star2) {
        this.homet_bookmark_image = homet_bookmark_image;
        this.homet_title = homet_title;
        this.homet_level_text = homet_level_text;
        this.homet_timenum = homet_timenum;
        this.homet_timetext = homet_timetext;
        this.homet_timenum2 = homet_timenum2;
        this.homet_timetext2 = homet_timetext2;
        this.thumbnail_image = thumbnail_image;
        this.homet_clock_item = homet_clock_item;
        this.homet_level_star1 = homet_level_star1;
        this.homet_level_star2 = homet_level_star2;
    }
    public HomeTraining_data(int homet_bookmark_image, String homet_title, String homet_level_text, String homet_timenum, String homet_timetext, String homet_timenum2, String homet_timetext2, int thumbnail_image, int homet_clock_item, int homet_level_star1) {
        this.homet_bookmark_image = homet_bookmark_image;
        this.homet_title = homet_title;
        this.homet_level_text = homet_level_text;
        this.homet_timenum = homet_timenum;
        this.homet_timetext = homet_timetext;
        this.homet_timenum2 = homet_timenum2;
        this.homet_timetext2 = homet_timetext2;
        this.thumbnail_image = thumbnail_image;
        this.homet_clock_item = homet_clock_item;
        this.homet_level_star1 = homet_level_star1;
    }


    public String getHomet_timenum2() {
        return homet_timenum2;
    }

    public void setHomet_timenum2(String homet_timenum2) {
        this.homet_timenum2 = homet_timenum2;
    }

    public String getHomet_timetext2() {
        return homet_timetext2;
    }

    public void setHomet_timetext2(String homet_timetext2) {
        this.homet_timetext2 = homet_timetext2;
    }

    public String getHomet_title() {
        return homet_title;
    }

    public void setHomet_title(String homet_title) {
        this.homet_title = homet_title;
    }

    public String getHomet_level_text() {
        return homet_level_text;
    }

    public void setHomet_level_text(String homet_level_text) {
        this.homet_level_text = homet_level_text;
    }

    public String getHomet_timenum() {
        return homet_timenum;
    }

    public void setHomet_timenum(String homet_timenum) {
        this.homet_timenum = homet_timenum;
    }

    public String getHomet_timetext() {
        return homet_timetext;
    }

    public void setHomet_timetext(String homet_timetext) {
        this.homet_timetext = homet_timetext;
    }

    public int getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(int thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public int getHomet_clock_item() {
        return homet_clock_item;
    }

    public void setHomet_clock_item(int homet_clock_item) {
        this.homet_clock_item = homet_clock_item;
    }


//    public ArrayList<ImageView> getStararrayList() {
//        return stararrayList;
//    }
//
//    public void setStararrayList(ArrayList<ImageView> stararrayList) {
//        this.stararrayList = stararrayList;
//    }

    public int getHomet_level_star1() {
        return homet_level_star1;
    }

    public void setHomet_level_star1(int homet_level_star) {
        this.homet_level_star1 = homet_level_star1;
    }

    public int getHomet_level_star2() {
        return homet_level_star2;
    }

    public void setHomet_level_star2(int homet_level_star2) {
        this.homet_level_star2 = homet_level_star2;
    }

    public int getHomet_level_star3() {
        return homet_level_star3;
    }

    public void setHomet_level_star3(int homet_level_star3) {
        this.homet_level_star3 = homet_level_star3;
    }

    public int getHomet_level_star4() {
        return homet_level_star4;
    }

    public void setHomet_level_star4(int homet_level_star4) {
        this.homet_level_star4 = homet_level_star4;
    }

    public int getHomet_level_star5() {
        return homet_level_star5;
    }

    public void setHomet_level_star5(int homet_level_star5) {
        this.homet_level_star5 = homet_level_star5;
    }

    public int getHomet_bookmark_image() {
        return homet_bookmark_image;
    }

    public void setHomet_bookmark_image(int homet_bookmark_image) {
        this.homet_bookmark_image = homet_bookmark_image;
    }
}
