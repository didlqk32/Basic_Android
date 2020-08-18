package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainpageHomeActivity extends AppCompatActivity {
    private Button temb;

    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menubar_open;
    private Button logout; //로그아웃 버튼

    private Button diary,otherpeople_dairy_button,home_training;
    LinearLayout menubar_my_profile,menubar_exercise_report;

    private ImageView menubar_profile_image; //메뉴바 프로필 이미지
    private TextView menubar_profile_nickname; //메뉴바 프로필 닉네임3
    private Bitmap memu_profile_bitmap; //메뉴바 프로필 이미지 임시 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage_home);

        temb = findViewById(R.id.temb);
        temb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProfileEditActivity.my_profile_nickname==null) {
                    Log.e("메세지","null");
                } else if (ProfileEditActivity.my_profile_nickname.equals("")){
                    Log.e("메세지2", "없다");
                } else if (!ProfileEditActivity.my_profile_nickname.equals("")){
                    Log.e("메세지3", ProfileEditActivity.my_profile_nickname);
                }
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);
        menubar_open = findViewById(R.id.menubar_open);
        menubar_exercise_report = findViewById(R.id.menubar_exercise_report); //메뉴바의 운동기록 버튼
        menubar_my_profile = findViewById(R.id.menubar_my_profile); //나의 프로필 연결


        menubar_profile_nickname = findViewById(R.id.menubar_profile_nickname);//메뉴바에서 나의 닉네임 연결
        menubar_profile_image = findViewById(R.id.menubar_profile_image);//메뉴바에서 나의 닉네임 이미지 연결
        logout = findViewById(R.id.logout); //로그아웃 버튼

        diary = findViewById(R.id.diary);
        otherpeople_dairy_button = findViewById(R.id.otherpeople_dairy_button);
        home_training = findViewById(R.id.home_training);



        SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
        String receive_profile_nickname = sharedPreferences.getString("nickname", ""); //받아온 데이터 String 변수 안에 넣기


        String receive_profile_profile_image = sharedPreferences.getString("profile_image", "닉네임"); //받아온 이미지 데이터 String 변수 안에 넣기
        byte[] encodeByte = Base64.decode(receive_profile_profile_image, Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
        memu_profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
        menubar_profile_nickname.setText(receive_profile_nickname);//닉네임 텍스트에 저장해놓은 텍스트 넣기
        menubar_profile_image.setImageBitmap(memu_profile_bitmap);//닉네임 이미지에 저장해놓은 이미지 넣기



        menubar_open.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) { //네비게이션 메뉴 불러오기
                drawerLayout.openDrawer(drawerView);
            }
        });


        drawerLayout.setDrawerListener(listner);
        drawerView.setOnTouchListener(new View.OnTouchListener() { //네비게이션 메뉴 적용
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        menubar_my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 메뉴바에서 나의 프로필 선택
                Intent intent = new Intent(MainpageHomeActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menubar_exercise_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 메뉴바에서 운동 기록 선택
                Intent intent = new Intent(MainpageHomeActivity.this,ExerciseReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //네비게이션 메뉴에서 로그아웃 선택
                Intent intent = new Intent(MainpageHomeActivity.this, logInActivity.class);
                logInActivity.my_id = ""; //내 아이디 값 초기화
                startActivity(intent);
                finish();
            }
        });

        home_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //홈트레이닝 화면으로 이동
                Intent intent = new Intent(MainpageHomeActivity.this,HomeTrainingActivity.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() { //일기 쓰기 화면으로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainpageHomeActivity.this,BundleDiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        otherpeople_dairy_button.setOnClickListener(new View.OnClickListener() { //공유 일기 화면으로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainpageHomeActivity.this,MainpageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    DrawerLayout.DrawerListener listner = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //뒤로가기 두 번 누르면 종료하는 기능 만들기

    }
}
