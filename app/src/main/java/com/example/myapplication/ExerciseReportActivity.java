package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ExerciseReportActivity extends AppCompatActivity {

    private ArrayList<ExerciseReport_data> exerciseReport_ArrayList;
    private ExerciseReport_adapter exerciseReport_adapter;
    private RecyclerView report_graph_recyclerview;
    private LinearLayoutManager linearLayoutManager;

    private Button exercise_menubar_calendarbutton;
    private ImageButton imageButton;
    private TextView exercise_total_countnum; //총 운동 횟 수
    private TextView exercise_total_timehour_num; //총 운동 시간(시)
    private TextView exercise_total_timeminute_num; //총 운동 시간(분)
    private TextView exercise_total_datenum ; //총 운동 일 수
    private TextView exercise_week_average_datenum; //일주일 운동 횟 수
    private TextView exercise_total_reducecalorienum; //총 소모 칼로리
    private int total_exercise_count = 0;
    private int total_exercise_time = 0;
    private int total_reduce_kcal = 0;
    private int total_exercise_date = 0;
    private boolean date_comparison = false; //총 운동 일수를 구하기 위해 하루에 여러번 운동해도 값이 증가 되지 않도록 만든 변수
    private LineChart report_linechart;
    private int chart_count = 1; //내 아이디로 내가 몇번의 운동을 했는지에 대한 변수


    private NestedScrollView report_graph_scrollview; //스크롤 뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_report);

        report_linechart = findViewById(R.id.report_linechart); //라인 차트 연결



        exercise_menubar_calendarbutton = findViewById(R.id.exercise_menubar_calendarbutton);
        imageButton = findViewById(R.id.imageButton);
        report_graph_scrollview = findViewById(R.id.report_graph_scrollview); //스크롤 뷰
        exercise_total_countnum = findViewById(R.id.exercise_total_countnum); //총 운동 횟 수
        exercise_total_timehour_num = findViewById(R.id.exercise_total_timehour_num); //총 운동 시간(시)
        exercise_total_timeminute_num = findViewById(R.id.exercise_total_timeminute_num); //총 운동 시간(분)
        exercise_total_datenum = findViewById(R.id.exercise_total_datenum); //총 운동 일 수
        exercise_week_average_datenum = findViewById(R.id.exercise_week_average_datenum); //일주일 운동 횟 수
        exercise_total_reducecalorienum = findViewById(R.id.exercise_total_reducecalorienum); //총 소모 칼로리


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


        for (int i = 0; i < temporary_report_id.length; i++) { //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_report_id[i].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                ExerciseReport_data exerciseReport_graph_data = new ExerciseReport_data(temporary_report_date[i], temporary_report_title[i], temporary_report_level[i]); //내용들을 bundle_diary_data에 담는다
                exerciseReport_ArrayList.add(0, exerciseReport_graph_data);
            }
            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
        }


        SharedPreferences sharedPreferences = getSharedPreferences("Total_Exercise_report_file", MODE_PRIVATE); //운동이 끝나면 운동 내용을 기록한다

        String receive_Total_report_id = sharedPreferences.getString("Total_report_id", "");
        String receive_Total_report_date = sharedPreferences.getString("Total_report_date", "");
        String receive_Total_report_time = sharedPreferences.getString("Total_report_time", "");
        String receive_Total_report_kcal = sharedPreferences.getString("Total_report_kcal", "");

//        Log.e("시간",receive_Total_report_time);

        String[] temporary_Total_report_id = receive_Total_report_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        String[] temporary_Total_report_date = receive_Total_report_date.split("@");
        String[] temporary_Total_report_time = receive_Total_report_time.split("@");
        String[] temporary_Total_report_kcal = receive_Total_report_kcal.split("@");



        for (int i = 0; i < temporary_report_id.length; i++) { //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_report_id[i].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기

//                Log.e("뭐지?",temporary_Total_report_time[i]);
                total_exercise_count++;
                total_exercise_time = total_exercise_time + Integer.parseInt(temporary_Total_report_time[i]); // 운동한 시간 모두 더하기 (단위 초)
                total_reduce_kcal = total_reduce_kcal + Integer.parseInt(temporary_Total_report_kcal[i]); // 소모한 칼로리 모두 더하기


                for (int j = i; j < temporary_report_id.length; j++) {
                    if (temporary_report_id[j].equals(logInActivity.my_id)) {
                        if (temporary_Total_report_date[i].equals(temporary_Total_report_date[j]) && i != j) { //현재 비교하는 일자와 다음 데이터 날짜와 비교 해서 같은 날이면 false;
                            date_comparison = false; // 날짜가 한 번이라도 겹치면 다음 대상 비교로 넘어간다
                            break;
                        } else if (!temporary_Total_report_date[i].equals(temporary_Total_report_date[j]) && i != j) { //현재 비교하는 일자와 다음 데이터 날짜와 비교 해서 다른 날이면 false;
                            date_comparison = true;
                        }
                    }
                }

                if (date_comparison) {
                    total_exercise_date++;
                    date_comparison = false;
                }

            }
            exerciseReport_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
        }
        exercise_total_countnum.setText(String.valueOf(total_exercise_count)); //총 운동 횟 수
        exercise_total_timehour_num.setText(String.valueOf(total_exercise_time / 3600));
        exercise_total_timeminute_num.setText(String.valueOf(total_exercise_time / 60));
        exercise_total_reducecalorienum.setText(String.valueOf(total_reduce_kcal)); //총 소모 칼로리

//        Log.e("길이",String.valueOf(temporary_Total_report_date.length));
//        Log.e("길이",temporary_Total_report_date[0]);


        if(temporary_Total_report_date.length < 2){
            if (temporary_Total_report_date[0].equals("")){
                exercise_total_datenum.setText("0");
            } else {
                exercise_total_datenum.setText("1");
            }
        } else if (!temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            exercise_total_datenum.setText(String.valueOf(total_exercise_date));
        } else if (temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            //운동 마지막 기록한 일자가 마지막 전 데이터와 같은 날이면 +1 해준다(마지막 배열은 위 코드로 비교할 수 없기 때문에)
            exercise_total_datenum.setText(String.valueOf(total_exercise_date+1));
        }

//        exercise_week_average_datenum; //일주일 운동 횟 수










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






















        //차트 만들기에 대한 코드

        ArrayList<Entry> entry_chart = new ArrayList<>(); //깃허브 인포트한 엔트리
        LineData chartData = new LineData(); //깃허브 차트 데이터
        for (int i = 0; i < temporary_Total_report_id.length; i++) { //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_Total_report_id[i].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기

                entry_chart.add(new Entry(chart_count, Integer.parseInt(temporary_Total_report_kcal[i])));
                chart_count++;
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entry_chart, "소모 칼로리");

        lineDataSet.setColor(Color.BLUE); //LineChart에서 Line Color 설정
        lineDataSet.setCircleColor(Color.BLUE); // LineChart에서 Line Circle Color 설정
        lineDataSet.setCircleHoleColor(Color.BLUE); // LineChart에서 Line Hole Circle Color 설정
        lineDataSet.setLineWidth(3f);

        XAxis xAxis = report_linechart.getXAxis();// x 축 설정
//        xAxis.setGranularityEnabled(true); //중복값 안보이게 하기
//        xAxis.setValueFormatter(new );


        YAxis yAxisRight = report_linechart.getAxisRight(); //Y축의 오른쪽면 설정
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);
        report_linechart.setData(data);


    }














    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ExerciseReportActivity.this,MainpageHomeActivity.class);
        startActivity(intent);
        finish();
    }


}