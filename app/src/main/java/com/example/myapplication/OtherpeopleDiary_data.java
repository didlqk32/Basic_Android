package com.example.myapplication;

public class OtherpeopleDiary_data {
    private String my_massage;
    private int my_profile;

    public OtherpeopleDiary_data(String my_massage, int my_profile) {
        this.my_massage = my_massage;
        this.my_profile = my_profile;
    }

    public String getMy_massage() {
        return my_massage;
    }

    public void setMy_massage(String my_massage) {
        this.my_massage = my_massage;
    }

    public int getMy_profile() {
        return my_profile;
    }

    public void setMy_profile(int my_profile) {
        this.my_profile = my_profile;
    }
}
