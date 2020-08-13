package com.example.myapplication;

public class Dm_data {
    private String dm_content; //메세지 담는 변수
    private String dm_time; //메세지 담는 변수
    private boolean itemViewType;

    public Dm_data(String dm_content,String dm_time,boolean itemViewType) {
        this.dm_content = dm_content;
        this.dm_time = dm_time;
        this.itemViewType = itemViewType;
    }

    public Dm_data() {

    }

    public boolean getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(boolean itemViewType) {
        this.itemViewType = itemViewType;
    }

    public String getDm_content() {
        return dm_content;
    }

    public void setDm_content(String dm_content) {
        this.dm_content = dm_content;
    }

    public String getDm_time() {
        return dm_time;
    }

    public void setDm_time(String dm_time) {
        this.dm_time = dm_time;
    }

}
