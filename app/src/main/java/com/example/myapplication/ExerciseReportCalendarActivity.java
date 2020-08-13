package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ExerciseReportCalendarActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Button exercise_menubar_graphbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report_calendar);

        imageButton = findViewById(R.id.imageButton);
        exercise_menubar_graphbutton = findViewById(R.id.exercise_menubar_graphbutton);

        exercise_menubar_graphbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportCalendarActivity.this,ExerciseReportActivity.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportCalendarActivity.this,MainpageHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}