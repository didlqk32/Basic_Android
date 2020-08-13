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

    private String save_bundlediary_title = ""; //title를 save 하기 위한 변수 생성
    private String save_bundlediary_content = ""; //content를 save 하기 위한 변수 생성
    private String save_bundlediary_date = ""; //date를 save 하기 위한 변수 생성
    private String save_bundlediary_day = ""; //day를 save 하기 위한 변수 생성
    private String save_bundlediary_month = ""; //month를 save 하기 위한 변수 생성
    private String save_bundlediary_year = ""; //year를 save 하기 위한 변수 생성

    private ImageButton todaydairy_circlepen, imageButton, search_button, calendar_button;
    private int clickcount = 1; //검색 버튼 눌렀을 때 반응하기 위한 변수
    private int year, month, day;
    private TextView calendar_view;
    Calendar calendar;
    androidx.constraintlayout.widget.ConstraintLayout training_search_bar;



    private ImageView todaydairy_delete2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_diary);

//        todaydairy_delete2 = findViewById(R.id.todaydairy_delete2);

        imageButton = findViewById(R.id.imageButton);
        todaydairy_circlepen = findViewById(R.id.todaydairy_circlepen);
        search_button = findViewById(R.id.search_button);
        training_search_bar = findViewById(R.id.training_search_bar);
        calendar_button = findViewById(R.id.calendar_button);
        calendar_view = findViewById(R.id.calendar_view);


        todaydairy_circlepen.setOnClickListener(new View.OnClickListener() { //글쓰기로 넘어감
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BundleDiaryActivity.this, TodayDiaryActivity.class);
                startActivity(intent);
            }
        });


        diary_bundle_recyclerview = findViewById(R.id.diary_bundle_recyclerview); //다이어리 번들 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(this);
        diary_bundle_recyclerview.setLayoutManager(linearLayoutManager);

        diaryArrayList = new ArrayList<>();
        bundle_diary_adapter = new Bundle_diary_adapter(diaryArrayList, this);
        diary_bundle_recyclerview.setAdapter(bundle_diary_adapter);

            Intent intent1 = getIntent(); //값을 받아 온다
            String strtitle = intent1.getStringExtra("content_title");
            String strcontent = intent1.getStringExtra("content");
            String strdate = intent1.getStringExtra("dairy_date");
//        String strweek = intent1.getStringExtra("weekday");
            String strday = intent1.getStringExtra("day");
            String strmonth = intent1.getStringExtra("month");
            String stryear = intent1.getStringExtra("year");

//        Log.e("strtitle 확인",bundle_diary_data.getDiary_title());
//        diary_bundle_recyclerview.setVisibility(View.VISIBLE); // diary_bundle_recyclerview 보이도록

        if (TodayDiaryCompleteActivity.checkcount == 1) { //나의 일기에서 글을 작성 완료 했을 때, TodayDiaryCompleteActivity 에서 자료가 왔을 때에만
            Bundle_diary_data bundle_diary_data = new Bundle_diary_data(strtitle, strcontent, strdate, strday, strmonth, stryear);
            diaryArrayList.add(0, bundle_diary_data);
            bundle_diary_adapter.notifyDataSetChanged();
            TodayDiaryCompleteActivity.checkcount = 0; //TodayDiaryCompleteActivity.checkcount 값 초기화
//            TodayDiaryActivity.check_diary = 0; //TodayDiaryActivity.check_diary 값 초기화
        }

    }




    @Override
    protected void onStart() {
        super.onStart();
        search_button.setOnClickListener(new View.OnClickListener() { //검색 버튼 누름
            @Override
            public void onClick(View view) {
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
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

//                Toast.makeText(BundleDiaryActivity.this,year,Toast.LENGTH_LONG).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(BundleDiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar_view.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });




        SharedPreferences sharedPreferences = getSharedPreferences("bundlediary_file",MODE_PRIVATE); //"bundlediary_file"파일의 데이터 받아오기
        //bundlediary recycler view를 담는 데이터
        String receive_bundlediary_title = sharedPreferences.getString("save_bundlediary_title",""); //받아온 데이터 String 변수 안에 넣기
        String receive_bundlediary_content = sharedPreferences.getString("save_bundlediary_content",""); //받아온 데이터 String 변수 안에 넣기
        String receive_bundlediary_date = sharedPreferences.getString("save_bundlediary_date",""); //받아온 데이터 String 변수 안에 넣기
        String receive_bundlediary_day = sharedPreferences.getString("save_bundlediary_day",""); //받아온 데이터 String 변수 안에 넣기
        String receive_bundlediary_month = sharedPreferences.getString("save_bundlediary_month",""); //받아온 데이터 String 변수 안에 넣기
        String receive_bundlediary_year = sharedPreferences.getString("save_bundlediary_year",""); //받아온 데이터 String 변수 안에 넣기
//        Log.e("receive_bundlediary_title",receive_bundlediary_title);



        if (!receive_bundlediary_title.equals("")&&!receive_bundlediary_content.equals("")&&!receive_bundlediary_date.equals("")&&!receive_bundlediary_day.equals("")&&!receive_bundlediary_month.equals("")&&!receive_bundlediary_year.equals("")){
            String[] Array_bundlediary_title = receive_bundlediary_title.split("/"); //receive_dm_content 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_bundlediary_content = receive_bundlediary_content.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_bundlediary_date = receive_bundlediary_date.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_bundlediary_day = receive_bundlediary_day.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_bundlediary_month = receive_bundlediary_month.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_bundlediary_year = receive_bundlediary_year.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음

            for (int i=0; i < Array_bundlediary_title.length; i++) {
                if (Array_bundlediary_title[i]==null){ //Array_bundlediary_title[i] 값이 화면상에 null로 표현 안되도록
                    Array_bundlediary_title[i] = "";
                }
                if (Array_bundlediary_content[i]==null){ //Array_bundlediary_content[i] 값이 화면상에 null로 표현 안되도록
                    Array_bundlediary_content[i] = "";
                }
                Bundle_diary_data bundle_diary_data = new Bundle_diary_data(Array_bundlediary_title[i], Array_bundlediary_content[i], Array_bundlediary_date[i], Array_bundlediary_day[i], Array_bundlediary_month[i], Array_bundlediary_year[i]); //내용들을 bundle_diary_data에 담는다
                diaryArrayList.add(bundle_diary_data); //리스트에 bundle_diary_data 내용을 추가 한다
            }
            bundle_diary_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
        }




//        Log.e("리스트 길이",String.valueOf(diaryArrayList.size()));
        if (TodayDiaryCompleteActivity.delete_boolean == true){ //TodayDiaryCompleteActivity 에서 삭제 버튼 눌렀을 때 해당 아이템 삭제

            Intent intent = getIntent();
            int position2 = intent.getIntExtra("position1",0);

//            Log.e("position 2",String.valueOf(position2));

            diaryArrayList.remove(position2);
            bundle_diary_adapter.notifyDataSetChanged();
            TodayDiaryCompleteActivity.delete_boolean = false;
        }



//        todaydairy_delete2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                diaryArrayList.remove(0);
////                Log.e("리스트 길이",String.valueOf(diaryArrayList.size()));
//                bundle_diary_adapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    public void onBackPressed() { //스마트폰 기능 뒤로가기 버튼 눌렀을 때
        super.onBackPressed();
        Intent intent = new Intent(BundleDiaryActivity.this, MainpageHomeActivity.class); //메인 페이지로 이동
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

//        Log.e("리스트 길이",String.valueOf(diaryArrayList.size()));

        for (int i=0; i < diaryArrayList.size(); i++){
            //타이틀, 컨텐츠, 데이트, 데이, 먼스 ,이어
            save_bundlediary_title = save_bundlediary_title + diaryArrayList.get(i).getDiary_title() + "/"; //diaryArrayList.size 안에 있는 데이터 변수에 넣기
            save_bundlediary_content = save_bundlediary_content + diaryArrayList.get(i).getDiary_content() + "/";
            save_bundlediary_date = save_bundlediary_date + diaryArrayList.get(i).getDiary_date() + "/";
            save_bundlediary_day = save_bundlediary_day + diaryArrayList.get(i).getDiary_day() + "/";
            save_bundlediary_month = save_bundlediary_month + diaryArrayList.get(i).getDiary_month() + "/";
            save_bundlediary_year = save_bundlediary_year + diaryArrayList.get(i).getDiary_year() + "/";
        }
//        Log.e("onstop","나갔다");

        SharedPreferences sharedPreferences = getSharedPreferences("bundlediary_file",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("save_bundlediary_title",save_bundlediary_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
        editor.putString("save_bundlediary_content",save_bundlediary_content);
        editor.putString("save_bundlediary_date",save_bundlediary_date);
        editor.putString("save_bundlediary_day",save_bundlediary_day);
        editor.putString("save_bundlediary_month",save_bundlediary_month);
        editor.putString("save_bundlediary_year",save_bundlediary_year);

//        editor.clear(); //sharedPreferences 저장되어 있는 파일 지우기
        editor.apply(); //동기,세이브를 완료 해라
    }
}