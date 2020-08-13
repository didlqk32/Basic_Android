package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( getApplicationContext(), logInActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onpause","확인");
        finish();
    }
}