package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExerciseReportCalendarActivity extends AppCompatActivity {

    private ArrayList<ExerciseReport_data> exerciseReport_ArrayList;
    private ExerciseReport_adapter exerciseReport_adapter;
    private RecyclerView report_calendar_recyclerview;
    private LinearLayoutManager linearLayoutManager;

    private ImageButton imageButton;
    private Button exercise_menubar_calendarbutton;
    private Button exercise_menubar_graphbutton;
    private CalendarView report_calendarview; //캘린더 뷰
    private String select_date = "";

    private NestedScrollView report_calendar_scrollview; // 스크롤 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report_calendar);

        imageButton = findViewById(R.id.imageButton);
        exercise_menubar_graphbutton = findViewById(R.id.exercise_menubar_graphbutton);
        exercise_menubar_calendarbutton = findViewById(R.id.exercise_menubar_calendarbutton);
        report_calendarview = findViewById(R.id.report_calendarview);
        report_calendar_scrollview = findViewById(R.id.report_calendar_scrollview);// 스크롤 뷰

        report_calendar_recyclerview = findViewById(R.id.report_calendar_recyclerview); //다이어리 번들 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(this);
        report_calendar_recyclerview.setLayoutManager(linearLayoutManager);

        exerciseReport_ArrayList = new ArrayList<>();
        exerciseReport_adapter = new ExerciseReport_adapter(exerciseReport_ArrayList, this);
        report_calendar_recyclerview.setAdapter(exerciseReport_adapter);





    }


    @Override
    protected void onStart() {
        super.onStart();

//        report_calendar_scrollview.scrollTo(0,0); //스크롤 뷰 가장 위쪽에서 시작하기


        Date currentTime = Calendar.getInstance().getTime(); //현재 날짜 구하기
        String date_text = new SimpleDateFormat("yyyy.M.dd", Locale.getDefault()).format(currentTime); //날짜 형태 정해서 변수에 넣기


        //shared에 기록된 운동 모두 보여주기
        SharedPreferences shared = getSharedPreferences("Exercise_report_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_report_id = shared.getString("Exercise_report_id", "");
        String receive_report_date = shared.getString("Exercise_report_date", "");
        String receive_report_title = shared.getString("Exercise_report_title", "");
        String receive_report_level = shared.getString("Exercise_report_level", "");


        final String[] temporary_report_id = receive_report_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        final String[] temporary_report_date = receive_report_date.split("@");
        final String[] temporary_report_title = receive_report_title.split("@");
        final String[] temporary_report_level = receive_report_level.split("@");



        //처음에 보여줄 때에는 오늘자랑 비교해서 오늘것만 보여주기!
        for (int i=0; i < temporary_report_id.length; i++){ //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_report_id[i].equals(logInActivity.my_id)){ //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                if (temporary_report_date[i].equals(date_text)) { //현재 날짜에 운동한 기록만 보여주기

                    ExerciseReport_data exerciseReport_graph_data = new ExerciseReport_data(temporary_report_date[i], temporary_report_title[i], temporary_report_level[i]); //내용들을 bundle_diary_data에 담는다
                    exerciseReport_ArrayList.add(0, exerciseReport_graph_data);
//                    Log.e("운동 기록 날짜", String.valueOf(temporary_report_date[i]));

                }
            }
            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
        }


//



        report_calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                exerciseReport_ArrayList.clear();
                exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
                select_date = String.valueOf(i)+"."+ (i1+1)+"."+ i2;




                for (int j=0; j < temporary_report_id.length; j++){ //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
                    if (temporary_report_id[j].equals(logInActivity.my_id)){ //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
//                        Log.e("조건문 안으로 들어 왔나요?!", "확인");
//                        Log.e("쉐어드에서 가져온 날짜", temporary_report_date[j]);
                        if (temporary_report_date[j].equals(String.valueOf(select_date))) {
//                            Log.e("두번째 조건문 들어 왔나요?", "확인");
                            ExerciseReport_data exerciseReport_graph_data = new ExerciseReport_data(temporary_report_date[j], temporary_report_title[j], temporary_report_level[j]); //내용들을 bundle_diary_data에 담는다
                            exerciseReport_ArrayList.add(0, exerciseReport_graph_data);
                            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
//                            Log.e("선택한 날짜?!! 문제인가", String.valueOf(temporary_report_date[j]));
                        } else { // 선택한 날짜로 작성한 일기가 없으면 안보여 준다
//                            exerciseReport_ArrayList.clear();
//                            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
                        }
                    } else { //내 아이디로 작성한 일기가 없으면 안보여 준다
//                        exerciseReport_ArrayList.clear();
//                        exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
                    }

                }




//                Log.e("날짜 확인",select_date);
            }
        });



        exercise_menubar_graphbutton.setOnClickListener(new View.OnClickListener() { // 상단 메뉴바에서 그래프 선택
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportCalendarActivity.this,ExerciseReportActivity.class);
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() { // 뒤로가기 선택
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportCalendarActivity.this,MainpageHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exercise_menubar_calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExerciseReportCalendarActivity.this,ExerciseReportCalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}