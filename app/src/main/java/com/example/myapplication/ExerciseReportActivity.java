package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ExerciseReportActivity extends AppCompatActivity {

    private ArrayList<ExerciseReport_data> exerciseReport_ArrayList;
    private ExerciseReport_adapter exerciseReport_adapter;
    private RecyclerView report_graph_recyclerview;
    private LinearLayoutManager linearLayoutManager;

    private Button exercise_menubar_calendarbutton;
    private ImageButton imageButton;

    private NestedScrollView report_graph_scrollview; //스크롤 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report);

        exercise_menubar_calendarbutton = findViewById(R.id.exercise_menubar_calendarbutton);
        imageButton = findViewById(R.id.imageButton);
        report_graph_scrollview = findViewById(R.id.report_graph_scrollview); // 스크롤 뷰

        report_graph_recyclerview = findViewById(R.id.report_graph_recyclerview); //다이어리 번들 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(this);
        report_graph_recyclerview.setLayoutManager(linearLayoutManager);

        exerciseReport_ArrayList = new ArrayList<>();
        exerciseReport_adapter = new ExerciseReport_adapter(exerciseReport_ArrayList, this);
        report_graph_recyclerview.setAdapter(exerciseReport_adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();

//        report_graph_scrollview.fullScroll(View.FOCUS_UP); //스크롤 뷰 가장 위쪽에서 시작하기
//        report_graph_scrollview.scrollTo(0,0);


        //shared에 기록된 운동 모두 보여주기
        SharedPreferences shared = getSharedPreferences("Exercise_report_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_report_id = shared.getString("Exercise_report_id", "");
        String receive_report_date = shared.getString("Exercise_report_date", "");
        String receive_report_title = shared.getString("Exercise_report_title", "");
        String receive_report_level = shared.getString("Exercise_report_level", "");


        String[] temporary_report_id = receive_report_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        String[] temporary_report_date = receive_report_date.split("@");
        String[] temporary_report_title = receive_report_title.split("@");
        String[] temporary_report_level = receive_report_level.split("@");


        for (int i=0; i < temporary_report_id.length; i++){ //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_report_id[i].equals(logInActivity.my_id)){ //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                ExerciseReport_data exerciseReport_graph_data = new ExerciseReport_data(temporary_report_date[i], temporary_report_title[i], temporary_report_level[i]); //내용들을 bundle_diary_data에 담는다
                exerciseReport_ArrayList.add(0,exerciseReport_graph_data);
            }
            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
        }




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