package com.example.myapplication;

public class ExerciseReport_data {

    private String report_date; //운동한 날짜를 담는 변수
    private String report_title; //운동 제목을 담는 변수
    private String report_level; //운동 난이도를 담는 변수

    public ExerciseReport_data(String report_date, String report_title, String report_level) {
        this.report_date = report_date;
        this.report_title = report_title;
        this.report_level = report_level;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public String getReport_level() {
        return report_level;
    }

    public void setReport_level(String report_level) {
        this.report_level = report_level;
    }

}
