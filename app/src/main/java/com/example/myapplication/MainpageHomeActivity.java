package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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


    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menubar_open;
    private Button logout; //로그아웃 버튼

    private Button diary,otherpeople_dairy_button,home_training;
    LinearLayout menubar_my_profile,menubar_exercise_report;

    private ImageView menubar_profile_image; //메뉴바 프로필 이미지
    private TextView menubar_profile_nickname; //메뉴바 프로필 닉네임3
    private Bitmap memu_profile_bitmap; //메뉴바 프로필 이미지 임시 저장
    private long backBtnTime = 0;

    private int total_exercise_count = 0;
    private int total_exercise_time = 0;
    private int total_reduce_kcal = 0;
    private int total_exercise_date = 0;
    private boolean date_comparison = false; //총 운동 일수를 구하기 위해 하루에 여러번 운동해도 값이 증가 되지 않도록 만든 변수

    private TextView exercise_total_timehour_num2;
    private TextView exercise_total_timeminute_num2;
    private TextView exercise_total_reducecalorienum2;
    private TextView exercise_total_datenum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage_home);


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

        exercise_total_timehour_num2 = findViewById(R.id.exercise_total_timehour_num2);
        exercise_total_timeminute_num2 = findViewById(R.id.exercise_total_timeminute_num2);
        exercise_total_reducecalorienum2 = findViewById(R.id.exercise_total_reducecalorienum2);
        exercise_total_datenum2 = findViewById(R.id.exercise_total_datenum2);

    }


    @Override
    protected void onStart() {
        super.onStart();


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



        for (int i = 0; i < temporary_Total_report_id.length; i++) { //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_Total_report_id[i].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기


                total_exercise_count++;
                total_exercise_time = total_exercise_time + Integer.parseInt(temporary_Total_report_time[i]); // 운동한 시간 모두 더하기 (단위 초)
                total_reduce_kcal = total_reduce_kcal + Integer.parseInt(temporary_Total_report_kcal[i]); // 소모한 칼로리 모두 더하기


                for (int j = i; j < temporary_Total_report_id.length; j++) {
                    if (temporary_Total_report_id[j].equals(logInActivity.my_id)) {
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
        }


        exercise_total_timehour_num2.setText(String.valueOf(total_exercise_time / 3600));
        exercise_total_timeminute_num2.setText(String.valueOf(total_exercise_time / 60));
        exercise_total_reducecalorienum2.setText(String.valueOf(total_reduce_kcal)); //총 소모 칼로리

//        Log.e("길이",String.valueOf(temporary_Total_report_date.length));
//        Log.e("길이",temporary_Total_report_date[0]);

        if(temporary_Total_report_date.length < 2){
            if (temporary_Total_report_date[0].equals("")){
                exercise_total_datenum2.setText("0");
            } else {
                exercise_total_datenum2.setText("1");
            }
        } else if (!temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            exercise_total_datenum2.setText(String.valueOf(total_exercise_date));
        } else if (temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            //운동 마지막 기록한 일자가 마지막 전 데이터와 같은 날이면 +1 해준다(마지막 배열은 위 코드로 비교할 수 없기 때문에)
            exercise_total_datenum2.setText(String.valueOf(total_exercise_date+1));
        }





        SharedPreferences shared = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기

        String receive_profile_id = shared.getString("profile_id", "");
        String receive_profile_nickname = shared.getString("nickname", "null"); //받아온 데이터 String 변수 안에 넣기
        String receive_profile_image = shared.getString("profile_image", "null"); //받아온 이미지 데이터 String 변수 안에 넣기

        String[] temporary_profile_id = receive_profile_id.split("@"); // 프로필 저장한 아이디 배열에 데이터 저장
        String[] temporary_profile_nickname = receive_profile_nickname.split("@");
        String[] temporary_profile_image = receive_profile_image.split("@");

        for (int i = 0; i < temporary_profile_id.length; i++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_profile_id[i].equals(logInActivity.my_id)){ //프로필 데이터중에 내 아이디로 쓴 데이터 찾기

                if (!temporary_profile_image[i].equals("null")) {
                    byte[] encodeByte = Base64.decode(temporary_profile_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                    memu_profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
                    menubar_profile_image.setImageBitmap(memu_profile_bitmap);//닉네임 이미지에 저장해놓은 이미지 넣기
                }

//                if (temporary_profile_nickname[i].equals("null")){
//                    menubar_profile_nickname.setText("");
//                } else {
//                    menubar_profile_nickname.setText(temporary_profile_nickname[i]);//닉네임 텍스트에 저장해놓은 텍스트 넣기
//                }

            }
        }





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
        //뒤로가기 두 번 누르면 종료하는 기능
        long curtime = System.currentTimeMillis();
        long gaptime = curtime - backBtnTime;

        if (0<=gaptime && 2500 >= gaptime) {

            ActivityCompat.finishAffinity(this);
            System.exit(0);
//            finish();
        } else {
            backBtnTime = curtime;
            Toast.makeText(this,"한번 더 누르면 앱이 종료 됩니다",Toast.LENGTH_SHORT).show();
        }
    }
}
