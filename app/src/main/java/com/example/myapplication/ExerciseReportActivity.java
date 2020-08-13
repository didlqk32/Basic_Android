package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ExerciseReportActivity extends AppCompatActivity {

    private Button exercise_menubar_calendarbutton;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report);

        exercise_menubar_calendarbutton = findViewById(R.id.exercise_menubar_calendarbutton);
        imageButton = findViewById(R.id.imageButton);

        exercise_menubar_calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportActivity.this,ExerciseReportCalendarActivity.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportActivity.this,MainpageHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ExerciseReportActivity.this,MainpageHomeActivity.class);
        startActivity(intent);
        finish();
    }
}