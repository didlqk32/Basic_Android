package com.example.myapplication;

import android.graphics.Bitmap;

public class OtherpeopleDiary_data {
    private String my_massage;
    private Bitmap my_profile;
    private String comments_id;
    private String profile_nickname;

    public OtherpeopleDiary_data(String my_massage, Bitmap my_profile, String comments_id, String profile_nickname) {
        this.my_massage = my_massage;
        this.my_profile = my_profile;
        this.comments_id = comments_id;
        this.profile_nickname = profile_nickname;
    }

    public String getProfile_nickname() {
        return profile_nickname;
    }

    public void setProfile_nickname(String profile_nickname) {
        this.profile_nickname = profile_nickname;
    }

    public String getComments_id() {
        return comments_id;
    }

    public void setComments_id(String comments_id) {
        this.comments_id = comments_id;
    }

    public String getMy_massage() {
        return my_massage;
    }

    public void setMy_massage(String my_massage) {
        this.my_massage = my_massage;
    }

    public Bitmap getMy_profile() {
        return my_profile;
    }

    public void setMy_profile(Bitmap my_profile) {
        this.my_profile = my_profile;
    }

}
