package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BundleDiaryActivity extends AppCompatActivity {

    private ArrayList<Bundle_diary_data> diaryArrayList;
    private Bundle_diary_adapter bundle_diary_adapter;
    private RecyclerView diary_bundle_recyclerview;
    private LinearLayoutManager linearLayoutManager;


    private ImageButton todaydairy_circlepen, imageButton, search_button, calendar_button;
    private int clickcount = 1; //검색 버튼 눌렀을 때 반응하기 위한 변수
    private int year, month, day;
    private TextView calendar_view;
    Calendar calendar;
    androidx.constraintlayout.widget.ConstraintLayout training_search_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_diary);


        imageButton = findViewById(R.id.imageButton);
        todaydairy_circlepen = findViewById(R.id.todaydairy_circlepen);
        search_button = findViewById(R.id.search_button);
        training_search_bar = findViewById(R.id.training_search_bar);
        calendar_button = findViewById(R.id.calendar_button);
        calendar_view = findViewById(R.id.calendar_view);





        diary_bundle_recyclerview = findViewById(R.id.diary_bundle_recyclerview); //다이어리 번들 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(this);
        diary_bundle_recyclerview.setLayoutManager(linearLayoutManager);

        diaryArrayList = new ArrayList<>();
        bundle_diary_adapter = new Bundle_diary_adapter(diaryArrayList, this);
        diary_bundle_recyclerview.setAdapter(bundle_diary_adapter);

    }




    @Override
    protected void onStart() {
        super.onStart();

        todaydairy_circlepen.setOnClickListener(new View.OnClickListener() { //글쓰기로 넘어감
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BundleDiaryActivity.this, TodayDiaryActivity.class);
                startActivity(intent);
            }
        });


        search_button.setOnClickListener(new View.OnClickListener() { //검색 버튼 누름
            @Override
            public void onClick(View view) { // 검색 아이콘을 누른다
                if (clickcount == 1) {
                    training_search_bar.setVisibility(View.VISIBLE);
                    clickcount = 2;
                } else if (clickcount == 2) {
                    training_search_bar.setVisibility(View.GONE);
                    clickcount = 1;
                }
            }
        });

        calendar = Calendar.getInstance();
        calendar_button.setOnClickListener(new View.OnClickListener() { //달력 버튼 누름
            @Override
            public void onClick(View view) { //캘린더 아이콘을 누른다
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

//                Toast.makeText(BundleDiaryActivity.this,year,Toast.LENGTH_LONG).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(BundleDiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) { //달력을 보여준다
                        calendar_view.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        //shared에 작성한 일기 모두 보여주기
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_diary_id = shared.getString("diary_id",""); // 작성한 일기들 데이터 받아서 문자열로 받기
        String receive_diary_title = shared.getString("diary_title","");
        String receive_diary_content = shared.getString("diary_content","");
        String receive_diary_date = shared.getString("diary_date","");
        String receive_diary_day = shared.getString("diary_day","");
        String receive_diary_month = shared.getString("diary_month","");
        String receive_diary_year = shared.getString("diary_year","");

        String[] temporary_diary_id = receive_diary_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        String[] temporary_diary_title = receive_diary_title.split("@");
        String[] temporary_diary_content = receive_diary_content.split("@");
        String[] temporary_diary_date = receive_diary_date.split("@");
        String[] temporary_diary_day = receive_diary_day.split("@");
        String[] temporary_diary_month = receive_diary_month.split("@");
        String[] temporary_diary_year = receive_diary_year.split("@");


        for (int i=0; i < temporary_diary_id.length; i++){ //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_diary_id[i].equals(logInActivity.my_id)){ //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                Bundle_diary_data bundle_diary_data = new Bundle_diary_data(temporary_diary_title[i], temporary_diary_content[i], temporary_diary_date[i], temporary_diary_day[i], temporary_diary_month[i], temporary_diary_year[i]); //내용들을 bundle_diary_data에 담는다
                diaryArrayList.add(0,bundle_diary_data);
            }
            bundle_diary_adapter.notifyDataSetChanged(); // adapter에 들어간 데이터 정리하여 새로 고침 한다
        }


        if (TodayDiaryCompleteActivity.delete_boolean == true){ //TodayDiaryCompleteActivity 에서 삭제 버튼 눌렀을 때 해당 아이템 삭제






            Intent intent = getIntent();
            int position2 = intent.getIntExtra("position1",0);









            diaryArrayList.remove(position2);











            bundle_diary_adapter.notifyDataSetChanged();
            TodayDiaryCompleteActivity.delete_boolean = false;
        }
    }

    @Override
    public void onBackPressed() { //스마트폰 기능 뒤로가기 버튼 눌렀을 때
        super.onBackPressed();
        Intent intent = new Intent(BundleDiaryActivity.this, MainpageHomeActivity.class); //메인 페이지로 이동
        startActivity(intent);
        finish();
    }

}