package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeTrainingActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView2;
    private ArrayList<HomeTraining_data> HomeTrainingArrayList, HomeTrainingArrayList2;
    private LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    private HomeTraining_adapter homeTraining_adapter;
    private HomeTraining_adapter2 homeTraining_adapter2;

    private ImageButton imageButton, training_search;
    private int clickcount = 1;
    private int bookmarkboolean1, bookmarkboolean2, bookmarkboolean3, bookmarkboolean4;
    androidx.constraintlayout.widget.ConstraintLayout training_search_bar;
    LinearLayout squat;


    //    private ArrayList<ImageView> stararrayList;
//    private ImageView homet_level_star1_item,homet_level_star2_item,homet_level_star3_item,homet_level_star4_item,homet_level_star5_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_training);

        recyclerView2 = findViewById(R.id.homet_recyclerview_bookmark); // homet_recyclerview_bookmark recyclerview 화면이랑 연결
        recyclerView = findViewById(R.id.homet_recyclerview); //homet_recyclerview recyclerview 화면이랑 연결

        linearLayoutManager2 = new LinearLayoutManager(this); // linearLayoutManager 객체 만들고 적용 화면은 this
        linearLayoutManager = new LinearLayoutManager(this); // linearLayoutManager 객체 만들고 적용 화면은 this

        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView.setLayoutManager(linearLayoutManager); //recyclerview 안에 linearLayoutManager정보 넣기

        HomeTrainingArrayList2 = new ArrayList<>(); //리스트 객체 만들기
        HomeTrainingArrayList = new ArrayList<>(); //리스트 객체 만들기
        homeTraining_adapter2 = new HomeTraining_adapter2(HomeTrainingArrayList2, this); //adapter 객체 만들고 리스트 정보 넣기
        homeTraining_adapter = new HomeTraining_adapter(HomeTrainingArrayList, this); //adapter 객체 만들고 리스트 정보 넣기

        recyclerView2.setAdapter(homeTraining_adapter2); //homet_recyclerview_bookmark  recyclerview 안에 adapter정보 넣기
        recyclerView.setAdapter(homeTraining_adapter); //homet_recyclerview  recyclerview 안에 adapter정보 넣기


        Intent intent = getIntent(); //값을 받아 온다
        String title_for_bookmark = intent.getStringExtra("homet_title_item");// 운동 명칭

        bookmarkboolean1 = R.drawable.bookmark; //기본 세팅은 북마크가 표시 X
        bookmarkboolean2 = R.drawable.bookmark; //기본 세팅은 북마크가 표시 X
        bookmarkboolean3 = R.drawable.bookmark; //기본 세팅은 북마크가 표시 X
        bookmarkboolean4 = R.drawable.bookmark; //기본 세팅은 북마크가 표시 X

//        if (HomeTraining_adapter.checkcount == 1) {
//            if (title_for_bookmark.equals("스쿼트")) { // 북마크 표시가 되어 있는 운동에 북마크 체크표시 그림
//                bookmarkboolean1 = R.drawable.bookmark_check;
//            } else if (title_for_bookmark.equals("푸쉬업")) {
//                bookmarkboolean2 = R.drawable.bookmark_check;
//            } else if (title_for_bookmark.equals("런지")) {
//                bookmarkboolean3 = R.drawable.bookmark_check;
//            } else if (title_for_bookmark.equals("사이드 프랭크")) {
//                bookmarkboolean4 = R.drawable.bookmark_check;
//            }
//        }

        HomeTraining_data homeTraining_data_squat = new HomeTraining_data(bookmarkboolean1, "스쿼트", "난이도", "4", "분", "", "", R.drawable.squat, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click); //dm_text내용을 dm_data에 담는다
        HomeTrainingArrayList.add(homeTraining_data_squat); //리스트에 dm_data내용을 추가 한다
        HomeTraining_data homeTraining_data_pushup = new HomeTraining_data(bookmarkboolean2, "푸쉬업", "난이도", "3", "분", "20", "초", R.drawable.push_up, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click); //dm_text내용을 dm_data에 담는다
        HomeTrainingArrayList.add(homeTraining_data_pushup); //리스트에 dm_data내용을 추가 한다
        HomeTraining_data homeTraining_data_lunge = new HomeTraining_data(bookmarkboolean3, "런지", "난이도", "3", "분", "40", "초", R.drawable.lunge, R.drawable.clock, R.drawable.star_click, R.drawable.star_click); //dm_text내용을 dm_data에 담는다
        HomeTrainingArrayList.add(homeTraining_data_lunge); //리스트에 dm_data내용을 추가 한다
        HomeTraining_data homeTraining_data_sideplank = new HomeTraining_data(bookmarkboolean4, "사이드 프랭크", "난이도", "2", "분", "40", "초", R.drawable.side_plank, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click); //dm_text내용을 dm_data에 담는다
        HomeTrainingArrayList.add(homeTraining_data_sideplank); //리스트에 dm_data내용을 추가 한다

        homeTraining_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리

//        Toast.makeText(this,String.valueOf(HomeTraining_adapter.bookmarkcount),Toast.LENGTH_SHORT).show();

        if (HomeTraining_adapter.checkcount == 1) {
            String strtitle = intent.getStringExtra("homet_title_item");// 운동 명칭

            if (strtitle.equals("스쿼트")) { //받아온 타이틀이 "스쿼트" 이면
                HomeTraining_data homeTraining_data_bookmark_squat = new HomeTraining_data(R.drawable.bookmark_check, "스쿼트", "난이도", "4", "분", "", "", R.drawable.squat, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click);
                HomeTrainingArrayList2.add(homeTraining_data_bookmark_squat); //리스트2에 homeTraining_data_bookmark_squat 추가 한다
            } else if (strtitle.equals("푸쉬업")) { //받아온 타이틀이 "푸쉬업" 이면
                HomeTraining_data homeTraining_data_bookmark_pushup = new HomeTraining_data(R.drawable.bookmark_check, "푸쉬업", "난이도", "3", "분", "20", "초", R.drawable.push_up, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click);
                HomeTrainingArrayList2.add(homeTraining_data_bookmark_pushup); //리스트2에 homeTraining_data_bookmark_squat 추가 한다
            } else if (strtitle.equals("런지")) { //받아온 타이틀이 "푸쉬업" 이면
                HomeTraining_data homeTraining_data_bookmark_lunge = new HomeTraining_data(R.drawable.bookmark_check, "런지", "난이도", "3", "분", "40", "초", R.drawable.lunge, R.drawable.clock, R.drawable.star_click, R.drawable.star_click);
                HomeTrainingArrayList2.add(homeTraining_data_bookmark_lunge); //리스트2에 homeTraining_data_bookmark_squat 추가 한다
            } else if (strtitle.equals("사이드 프랭크")) { //받아온 타이틀이 "푸쉬업" 이면
                HomeTraining_data homeTraining_data_bookmark_sideplank = new HomeTraining_data(R.drawable.bookmark_check, "사이드 프랭크", "난이도", "2", "분", "40", "초", R.drawable.side_plank, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click);
                HomeTrainingArrayList2.add(homeTraining_data_bookmark_sideplank); //리스트2에 homeTraining_data_bookmark_squat 추가 한다
            }
            homeTraining_adapter2.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
            HomeTraining_adapter.checkcount = 0;
//            finish();



//            String strleveltext = intent.getStringExtra("homet_level_text_item");// 운동 난이도 텍스트
//            String strtimenum = intent.getStringExtra("homet_timenum_item");// 운동 시간 분
//            String strtimetext = intent.getStringExtra("homet_timetext_item");// 운동 시간 분 텍스트
//            String strtimenum2 = intent.getStringExtra("homet_timenum_item2");// 운동 시간 초
//            String strtimetext2 = intent.getStringExtra("homet_timetext_item2");// 운동 시간 초 텍스트
//
//            byte[] arr = getIntent().getByteArrayExtra("Thumbnail_image"); //이미지 데이터를 바이트로 받는다
//            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length); //바이트로 받은 이미지를 비트맵으로 바꾼다
//    //        home_training_start_image.setImageBitmap(image); //비트맵으로 바뀐 이미지를 imageview에 넣는다
//            HomeTraining_data homeTraining_data_bookmark = new HomeTraining_data(strtitle, strleveltext, strtimenum, strtimetext, strtimenum2, strtimetext2, R.drawable.clock, R.drawable.clock, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click, R.drawable.star_click);
//            HomeTrainingArrayList2.add(homeTraining_data_bookmark); //리스트에 dm_data내용을 추가 한다

        }


        training_search_bar = findViewById(R.id.training_search_bar); //검색 바 연결
        imageButton = findViewById(R.id.imageButton);
//        squat = findViewById(R.id.squat);
//
//        squat.setOnClickListener(new View.OnClickListener() { // 운동 화면으로 전환
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeTrainingActivity.this, HomeTrainingDetailActivity.class);
//                startActivity(intent);
//            }
//        });

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //화면 뒤로 가기
//                Intent intent = new Intent(HomeTrainingActivity.this, MainpageHomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        training_search = findViewById(R.id.training_search);
        training_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //검색바 생성
                if (clickcount == 1) {
                    training_search_bar.setVisibility(View.VISIBLE);
                    clickcount = 2;
                } else if (clickcount == 2) {
                    training_search_bar.setVisibility(View.GONE);
                    clickcount = 1;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeTrainingActivity.this, MainpageHomeActivity.class);
        startActivity(intent);
        finish();
    }
}