package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_save;
    private EditText report_todaydairy_content_title, report_todaydairy_content;
    private ImageButton imageButton, todaydairy_camera, todaydairy_picture;
    private ImageView report_todaydairy_image;
    private TextView report_todaydairy_date;
    final String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 100;
    final static int get_gallery_image = 200;
    private Bitmap profile_bitmap;

    private Spinner diary_choice_spinner; //스피너
    private TextView diary_choice_text; //스피너 선택 적용될 텍스트
    private int thisposition = -1;

    private String temporary_content_title = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_content = ""; // shared 값을 임시적으로 담기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_diary);

        button_save = findViewById(R.id.button_save);
        report_todaydairy_content_title = findViewById(R.id.report_todaydairy_content_title);
        report_todaydairy_content = findViewById(R.id.report_todaydairy_content);
        imageButton = findViewById(R.id.imageButton);
        todaydairy_camera = findViewById(R.id.todaydairy_camera);
        todaydairy_picture = findViewById(R.id.todaydairy_picture);
        report_todaydairy_image = findViewById(R.id.report_todaydairy_image);
        todaydairy_picture = findViewById(R.id.todaydairy_picture);
        report_todaydairy_date = findViewById(R.id.report_todaydairy_date);

        diary_choice_spinner = findViewById(R.id.diary_choice_spinner); //스피너 연결
        diary_choice_text = findViewById(R.id.diary_choice_text); //스피너 선택 적용될 텍스트
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { //갤러리에 대한 퍼미션
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }


    // 버튼 onClick리스터 처리부분
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todaydairy_camera: // 카메라 앱을 여는 소스
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
                break;
            case R.id.todaydairy_picture: // 갤러리 앱을 여는 소스
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, get_gallery_image);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE: // 카메라로 촬영한 영상을 가져오는 부분
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    profile_bitmap = (Bitmap) intent.getExtras().get("data");
                    if (profile_bitmap != null) {
                        report_todaydairy_image.setVisibility(View.VISIBLE);
                        report_todaydairy_image.setImageBitmap(profile_bitmap);
                    }
                }
                break;
            case get_gallery_image: //이미지 가져오는 부분

                if (resultCode == RESULT_OK && intent != null) {
                    try {
                        InputStream in = getContentResolver().openInputStream(intent.getData());
                        profile_bitmap = BitmapFactory.decodeStream(in);
                        report_todaydairy_image.setVisibility(View.VISIBLE);
                        report_todaydairy_image.setImageBitmap(profile_bitmap);
                    } catch (FileNotFoundException e) {
                        //e.printStackTrace();
                    }
                }
        }
    }







    @Override
    protected void onStart() {
        super.onStart();
        // Log.e("onStart","확인");

//        SharedPreferences sharedPreferences = getSharedPreferences("today_file", MODE_PRIVATE); //데이터 담아두기
//        String title_value = sharedPreferences.getString("title_value", ""); //키값이 "title_value"인 데이터 가져오기
//        String content_value = sharedPreferences.getString("content_value", ""); //키값이 "content_value"인 데이터 가져오기
//
//        report_todaydairy_content_title.setText(title_value); //제목에 텍스트 입히기
//        report_todaydairy_content.setText(content_value); // 내용에 텍스트 입히기

        Date current_time = Calendar.getInstance().getTime(); //현재 날짜 구하기
//        SimpleDateFormat  weekday_format = new SimpleDateFormat("E", Locale.getDefault()); //현재 날짜 요일 구하기
        SimpleDateFormat day_format = new SimpleDateFormat("dd", Locale.getDefault()); //현재 날짜 일 구하기
        SimpleDateFormat month_format = new SimpleDateFormat("MM", Locale.getDefault()); //현재 날짜 달 구하기
        SimpleDateFormat year_format = new SimpleDateFormat("yyyy", Locale.getDefault()); //현재 날짜 연도 구하기


//        final String weekday = weekday_format.format(current_time);
        final String day = day_format.format(current_time);
        final String month = month_format.format(current_time);
        final String year = year_format.format(current_time);

        report_todaydairy_date.setText(year + "년 " + month + "월 " + day + "일 "); //현재 날짜 표시하기

        diary_choice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //일기 선택 스피너 버튼 눌렀을 때 기능 구현
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diary_choice_text.setText(adapterView.getItemAtPosition(i).toString()); //스피너 선택 내용 diary_choice_text에 담기
//                Toast.makeText(TodayDiaryActivity.this,diary_choice_text.getText(),Toast.LENGTH_SHORT).show(); //스피너 선택에 대한 토스트메세지
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //권한 설정하기
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        todaydairy_camera.setOnClickListener(this); //카메라 버튼 클릭
        todaydairy_picture.setOnClickListener(this); //갤러리 버튼 클릭



        Intent intent = getIntent();
        int position = intent.getIntExtra("thisposition", -1); //bundle diary에서 아이템 선택했을 때 포지션 값을 받는다
        thisposition = position;
//        Log.e("thisposition",String.valueOf(position));
        if (position != -1){ //bundle diary에서 아이템 선택했을 때 포지션 값 비교 (-1이면 포지션값 못받은 것)
            //나의 일기에서 저장 했던 데이터 받아오기
            SharedPreferences sharedPreferences = getSharedPreferences("TodayDiaryComplete_file", MODE_PRIVATE); //"TodayDiaryComplete_file"파일의 데이터 받아오기

            String receive_mydiary_content_title = sharedPreferences.getString("mydiary_content_title", ""); //받아온 데이터 String 변수 안에 넣기
            String receive_mydiary_content = sharedPreferences.getString("mydiary_content", ""); //받아온 데이터 String 변수 안에 넣기
            String receive_mydiary_dairy_date = sharedPreferences.getString("mydiary_dairy_date", "");
            String receive_mydiary_day = sharedPreferences.getString("mydiary_day", "");
            String receive_mydiary_month = sharedPreferences.getString("mydiary_month", "");
            String receive_mydiary_year = sharedPreferences.getString("mydiary_year", "");


            String[] temporary_mydiary_content_title = receive_mydiary_content_title.split("@");
            String[] temporary_mydiary_content = receive_mydiary_content.split("@");
            String[] temporary_mydiary_dairy_date = receive_mydiary_dairy_date.split("@");
            String[] temporary_mydiary_day = receive_mydiary_day.split("@");
            String[] temporary_mydiary_month = receive_mydiary_month.split("@");
            String[] temporary_mydiary_year = receive_mydiary_year.split("@");



            report_todaydairy_content_title.setText(temporary_mydiary_content_title[temporary_mydiary_content_title.length-position-1]);
            report_todaydairy_content.setText(temporary_mydiary_content[temporary_mydiary_content.length-position-1]);
            report_todaydairy_date.setText(temporary_mydiary_dairy_date[temporary_mydiary_dairy_date.length-position-1]);

        }






        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //일기 내용 전달, 세이브 버튼 클릭,     텍스트 임시저장?!-> 보류

                if (report_todaydairy_content_title.getText().toString().equals("")) {
                    Toast.makeText(TodayDiaryActivity.this, "제목을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (report_todaydairy_content.getText().toString().equals("")) {
                    Toast.makeText(TodayDiaryActivity.this, "내용을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {



                    if (thisposition != -1) { //bundle diary에서 아이템 선택했을 때 포지션 값 비교 (-1이면 포지션값 못받은 것)
                        SharedPreferences sharedPreferences = getSharedPreferences("TodayDiaryComplete_file", MODE_PRIVATE); //"TodayDiaryComplete_file"파일의 데이터 받아오기
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        String receive_mydiary_content_title = sharedPreferences.getString("mydiary_content_title", ""); //받아온 데이터 String 변수 안에 넣기
                        String receive_mydiary_content = sharedPreferences.getString("mydiary_content", ""); //받아온 데이터 String 변수 안에 넣기


                        String[] temporary_mydiary_content_title = receive_mydiary_content_title.split("@");
                        String[] temporary_mydiary_content = receive_mydiary_content.split("@");


                        Log.e("receive_mydiary", receive_mydiary_content_title);


                        ArrayList<String> Arraylist_content_title = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                        for (String temp : temporary_mydiary_content_title) {
                            Arraylist_content_title.add(temp);
                        }
                        Arraylist_content_title.set(Arraylist_content_title.size() - thisposition - 1, report_todaydairy_content_title.getText().toString()); // ArrayList 에서 해당 포지션 제거


                        ArrayList<String> Arraylist_content = new ArrayList<>();
                        for (String temp : temporary_mydiary_content) {
                            Arraylist_content.add(temp);
                        }
                        Arraylist_content.set(Arraylist_content.size() - thisposition - 1, report_todaydairy_content.getText().toString());



                        if (Arraylist_content_title.size() != 0) {
                            for (int j = 0; j < Arraylist_content_title.size(); j++) { //기록 되어 있는 아이디 나열하기
                                temporary_content_title = temporary_content_title + Arraylist_content_title.get(j) + "@";
                            }
                        }

                        Log.e("receive_mydiary2", temporary_content_title);

                        if (Arraylist_content.size() != 0) {
                            for (int j = 0; j < Arraylist_content.size(); j++) { //기록 되어 있는 아이디 나열하기
                                temporary_content = temporary_content + Arraylist_content.get(j) + "@";
                            }
                        }

                        editor.putString("mydiary_content_title", temporary_content_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
                        editor.putString("mydiary_content", temporary_content);
                        editor.apply(); //동기,세이브를 완료 해라
                    }






                    Intent intent = new Intent(TodayDiaryActivity.this, TodayDiaryCompleteActivity.class);
                    String content_title = report_todaydairy_content_title.getText().toString();
                    String content = report_todaydairy_content.getText().toString();
                    String todaydairy_date = report_todaydairy_date.getText().toString();

                    String diary_choice = diary_choice_text.getText().toString();

                    intent.putExtra("diary_choice_text", diary_choice); // 스피너 선택 텍스트 보내기

                    intent.putExtra("content_title", content_title);
                    intent.putExtra("content", content);
                    intent.putExtra("todaydairy_date", todaydairy_date);
                    intent.putExtra("day", day);
                    intent.putExtra("month", month);
                    intent.putExtra("year", year);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream(); //이미지 비트맵 -> 바꾸기 위한 준비

                    if (profile_bitmap != null) {
                        profile_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        intent.putExtra("image", byteArray);
                    }
//                check_diary = 1; // TodayDiaryCompleteActivity 로 보내서 다른곳에서 온 데이터에 영향 안받도록
                    startActivity(intent);







                }
            }
        });


//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //뒤로 가기 버튼
//                Intent intent = new Intent(TodayDiaryActivity.this, BundleDiaryActivity.class);
//                startActivity(intent);
//
//                SharedPreferences sharedPreferences = getSharedPreferences("today_file",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                String title_value = report_todaydairy_content_title.getText().toString();
//                String content_value = report_todaydairy_content.getText().toString();
//                //임시 저장하기?!!! -> 다이얼로그 사용?!
//                editor.putString("title_value", title_value);
//                editor.putString("content_value", content_value);
//                editor.apply(); //동기,세이브를 완료 해라
//            }
//        });

    }

    @Override
    public void onBackPressed() { //뒤로가기 눌렀을 경우 (버튼 말고 핸드폰 뒤로가기)
        super.onBackPressed();


        Intent intent = new Intent(TodayDiaryActivity.this, BundleDiaryActivity.class);
        startActivity(intent);



    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.e("onStop", "확인");
//        SharedPreferences sharedPreferences = getSharedPreferences("today_file",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        String title_value = report_todaydairy_content_title.getText().toString();
//        String content_value = report_todaydairy_content.getText().toString();
//        //임시 저장하기?!!! -> 다이얼로그 사용?!
//        editor.putString("title_value", title_value);
//        editor.putString("content_value", content_value);
//        editor.apply(); //동기,세이브를 완료 해라

//        button_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = getSharedPreferences(shared, 0); //데이터 객체 생성 (저장 버튼 누르면 임시저장 데이터 소멸)
//                SharedPreferences.Editor editor = sharedPreferences.edit(); //데이터 편집 하기 위한 객체 생성
//
//                editor.clear();
//                editor.apply(); //동기,세이브를 완료 해라
////                editor.remove("title_value"); //키 값이 "title_value" 데이터 삭제
////                editor.remove("content_value"); //키 값이 "content_value" 데이터 삭제
//            }
//        });
    }


}