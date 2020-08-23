package com.example.myapplication;

public class Dm_data {
    private String dm_content; //메세지 담는 변수
    private String dm_time; //메세지 담는 변수
    private String dm_id;
    private String dm_nickname;

    public Dm_data(String dm_content,String dm_time, String dm_id, String dm_nickname) {
        this.dm_content = dm_content;
        this.dm_time = dm_time;
        this.dm_id = dm_id;
        this.dm_nickname = dm_nickname;
    }

    public Dm_data(String dm_content,String dm_time, String dm_id) {
        this.dm_content = dm_content;
        this.dm_time = dm_time;
        this.dm_id = dm_id;
    }

    public Dm_data() {

    }

    public String getDm_nickname() {
        return dm_nickname;
    }

    public void setDm_nickname(String dm_nickname) {
        this.dm_nickname = dm_nickname;
    }

    public String getDm_id() {
        return dm_id;
    }

    public void setDm_id(String dm_id) {
        this.dm_id = dm_id;
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
