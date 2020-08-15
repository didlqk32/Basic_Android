package com.example.myapplication;

public class Bundle_diary_data {

    private String diary_title,diary_day,diary_content,diary_date;
    private String diary_month,diary_year;
//    private String diary_week;

    public Bundle_diary_data(String diary_title, String diary_content, String diary_date, String diary_day, String diary_month, String diary_year) {
//        this.diary_week = diary_week;
        this.diary_title = diary_title;
        this.diary_content = diary_content;
        this.diary_day = diary_day;
        this.diary_date = diary_date;
        this.diary_month = diary_month;
        this.diary_year = diary_year;
    }

    public String getDiary_month() {
        return diary_month;
    }

    public void setDiary_month(String diary_month) {
        this.diary_month = diary_month;
    }

    public String getDiary_year() {
        return diary_year;
    }

    public void setDiary_year(String diary_year) {
        this.diary_year = diary_year;
    }

//    public String getDiary_week() {
//        return diary_week;
//    }
//
//    public void setDiary_week(String diary_week) {
//        this.diary_week = diary_week;
//    }

    public String getDiary_title() {
        return diary_title;
    }

    public void setDiary_title(String diary_title) {
        this.diary_title = diary_title;
    }

    public String getDiary_day() {
        return diary_day;
    }

    public void setDiary_day(String diary_day) {
        this.diary_day = diary_day;
    }

    public String getDiary_content() {
        return diary_content;
    }

    public void setDiary_content(String diary_content) {
        this.diary_content = diary_content;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public void setDiary_date(String diary_date) {
        this.diary_date = diary_date;
    }
}
