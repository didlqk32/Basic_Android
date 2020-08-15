package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TodayDiaryCompleteActivity extends AppCompatActivity {

    private ImageView todaydairy_setup, todaydairy_image;
    private TextView todaydairy_content_title, todaydairy_content, todaydairy_date;
    private ImageButton imageButton, shared_button;
    private String content_title, content, dairy_date, weekday, day, month, year;
    private Spinner diary_choice_spinner2;
    private TextView diary_choice_text2; //나의 일기 인지, 고융 일기 인지
    public static int checkcount = 0;
    public byte[] arr;

    private ImageView todaydairy_delete; //삭제 버튼
    public static boolean delete_boolean = false;
    public static int edit_from_adapter = 0; //adapter 로 부터 수정 요청 왔을 때

    private String save_mydiary_content_title = ""; //sharedpreference 데이터 담는 변수
    private String save_mydiary_content = ""; //sharedpreference 데이터 담는 변수
    private String save_mydiary_dairy_date = ""; //sharedpreference 데이터 담는 변수
    private String save_mydiary_day = ""; //sharedpreference 데이터 담는 변수
    private String save_mydiary_month = ""; //sharedpreference 데이터 담는 변수
    private String save_mydiary_year = ""; //sharedpreference 데이터 담는 변수
//    private int thisposition;

    private String temporary_content_title = "";
    private String temporary_content = "";
    private String temporary_dairy_date = "";
    private String temporary_day = "";
    private String temporary_month = "";
    private String temporary_year = "";

    private String temporary_content_title2 = "";
    private String temporary_content2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_diary_complete);

        todaydairy_setup = findViewById(R.id.todaydairy_setup);
        todaydairy_content_title = findViewById(R.id.todaydairy_content_title);
        todaydairy_content = findViewById(R.id.todaydairy_content);
        imageButton = findViewById(R.id.imageButton);
        todaydairy_image = findViewById(R.id.todaydairy_image);
        todaydairy_date = findViewById(R.id.todaydairy_date);
        shared_button = findViewById(R.id.shared_button);

        diary_choice_spinner2 = findViewById(R.id.diary_choice_spinner2); //스피너 연결
        diary_choice_text2 = findViewById(R.id.diary_choice_text2); //스피너 선택 적용될 텍스트 //나의 일기 인지, 공유 일기 인지
        todaydairy_delete = findViewById(R.id.todaydairy_delete); // 삭제 버튼(이미지 뷰)

    }


    @Override
    protected void onStart() {
        super.onStart();


        //방금 등록된 일기 보여주기
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_diary_title = shared.getString("diary_title","");
        String receive_diary_content = shared.getString("diary_content","");
        String receive_diary_date = shared.getString("diary_date","");
        String receive_diary_image = shared.getString("diary_image", "");
        // 이미지 있는지 없는지에 대한 예외 처리 해줘야 한다
        // 현재 다이어리가 몇번 째 다이어리인지 찾아야 한다
        // if 다이어리가 작성에서 넘어 왔다면(포지션 값이 없다면) 마지막 다이어리 보여주면 되고 번들에서 넘어 왔으면 포지션 값 받으면 된다....
        // 포지션 값이 없으면 -1로 설정

//        Log.e("이미지 내용",receive_diary_image);

        String[] temporary_diary_title = receive_diary_title.split("@");
        String[] temporary_diary_content = receive_diary_content.split("@");
        String[] temporary_diary_date = receive_diary_date.split("@");
        String[] temporary_diary_image = receive_diary_image.split("@");

        final Intent intent = getIntent(); //intent 값을 Bundle_diary_adapter 에서 받아온다
        final int position = intent.getIntExtra("position", -1); //Bundle_diary에서 선택한 일기의 포지션 값을 받고 처음 작성한 일기라면 초기값으로 -1을 받는다

//        Log.e("포지션 값",String.valueOf(position));

        if (position == -1){ //position 값이 -1 이면 일기를 처음 작성 했다는 뜻

            todaydairy_content_title.setText(temporary_diary_title[temporary_diary_title.length-1]); //배열 길이는 1부터 시작하고 배열 인덱스 값은 0부터 시작하기 때문에 길이에 -1 해야 가장 최근 등록 된 것
            todaydairy_content.setText(temporary_diary_content[temporary_diary_content.length-1]);
            todaydairy_date.setText(temporary_diary_date[temporary_diary_date.length-1]);

//            String 이미지르 bitmap으로 바꾸는 작업
            byte[] encodeByte = Base64.decode(temporary_diary_image[temporary_diary_image.length-1], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기

            if (!temporary_diary_image[temporary_diary_image.length-1].equals("null")) { //방금 작성한 일기의 이미지가 "null"이 아니면 이미지 보이도록 한다
                todaydairy_image.setVisibility(View.VISIBLE);
                todaydairy_image.setImageBitmap(bitmapimage);
            }


        } else { //일기를 수정하는 경우
            //포지션 값은 -1 해야한다 (두번째 다이어리 클릭하면 1, 세번째 클릭하면 2 나옴)

            todaydairy_content_title.setText(temporary_diary_title[temporary_diary_title.length-1-position]); //배열 길이는 1부터 시작하고 배열 인덱스 값은 0부터 시작하기 때문에 길이에 -1 해야 가장 최근 등록 된 것
            todaydairy_content.setText(temporary_diary_content[temporary_diary_content.length-1-position]);
            todaydairy_date.setText(temporary_diary_date[temporary_diary_date.length-1-position]);

            //String 이미지르 bitmap으로 바꾸는 작업
            byte[] encodeByte = Base64.decode(temporary_diary_image[temporary_diary_image.length-1-position], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기

            Log.e("포지션 값",String.valueOf(position));
            Log.e("이미지",temporary_diary_image[temporary_diary_image.length-1-position]);
            if (!temporary_diary_image[temporary_diary_image.length-1-position].equals("null")) { //선택한 일기의 이미지가 "null"이 아니면 이미지 보이도록 한다
                todaydairy_image.setVisibility(View.VISIBLE);
                todaydairy_image.setImageBitmap(bitmapimage);
            }
        }


// 지울때 arraylist로 바꿔서 지워야 한다




















//        final Intent intent = getIntent(); //intent 값을 받아온다
//        content_title = intent.getStringExtra("content_title");
//        content = intent.getStringExtra("content");
//        dairy_date = intent.getStringExtra("todaydairy_date");
////        weekday = intent.getStringExtra("weekday");
//        day = intent.getStringExtra("day");
//        month = intent.getStringExtra("month");
//        year = intent.getStringExtra("year");
//
////        String diary_choice = intent.getStringExtra("diary_choice_text");//TodayDiaryActivity 에서 받은 텍스트 그대로 적용시키기
////        diary_choice_text2.setText(diary_choice); //나의 일기, 공유 일기선택
//
//        arr = getIntent().getByteArrayExtra("image"); //이미지 데이터 받는 소스, 값이 없을 수 도 있다
//
//        if (arr != null) { //arr로 받는 값이 있으면 todaydairy_image에 이미지를 넣는다
//            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//            if (image != null) { //image로 받는 값이 있으면 todaydairy_image를 보이게 한다
//                todaydairy_image.setVisibility(View.VISIBLE);
//            }
//            todaydairy_image.setImageBitmap(image);
//        }
//
//        todaydairy_date.setText(dairy_date);
//        todaydairy_content_title.setText(content_title);
//        todaydairy_content.setText(content);


//        int position = intent.getIntExtra("position", -1);
//        thisposition = position;
////        Log.e("포지션",String.valueOf(position));
//        //받아온 포지션이 있다면!! 포지션 값 비교 하기!!!
//        if (position != -1) {
//
//            SharedPreferences sharedPreferences = getSharedPreferences("TodayDiaryComplete_file", MODE_PRIVATE); //"signup_file"파일의 데이터 받아오기
//            String receive_mydiary_content_title = sharedPreferences.getString("mydiary_content_title", ""); //받아온 데이터 String 변수 안에 넣기
//            String receive_mydiary_content = sharedPreferences.getString("mydiary_content", ""); //받아온 데이터 String 변수 안에 넣기
//            String receive_mydiary_dairy_date = sharedPreferences.getString("mydiary_dairy_date", "");
//            String receive_mydiary_day = sharedPreferences.getString("mydiary_day", "");
//            String receive_mydiary_month = sharedPreferences.getString("mydiary_month", "");
//            String receive_mydiary_year = sharedPreferences.getString("mydiary_year", "");
//
//
//            String[] temporary_mydiary_content_title = receive_mydiary_content_title.split("@");
//            String[] temporary_mydiary_content = receive_mydiary_content.split("@");
//            String[] temporary_mydiary_dairy_date = receive_mydiary_dairy_date.split("@");
//            String[] temporary_mydiary_day = receive_mydiary_day.split("@");
//            String[] temporary_mydiary_month = receive_mydiary_month.split("@");
//            String[] temporary_mydiary_year = receive_mydiary_year.split("@");
//
//
////            Log.e("배열 길이",String.valueOf(temporary_mydiary_content_title.length));
////            Log.e("포지션",String.valueOf(thisposition));
////            Log.e("receive",receive_mydiary_content_title);
//
//
//            todaydairy_content_title.setText(temporary_mydiary_content_title[temporary_mydiary_content_title.length - position - 1]); //처음에 receive_mydiary_content_title 안에 빈 공간이 더해져서 position에 +1 해줌
//            todaydairy_content.setText(temporary_mydiary_content[temporary_mydiary_content.length - position - 1]);
//            todaydairy_date.setText(temporary_mydiary_dairy_date[temporary_mydiary_dairy_date.length - position - 1]);
//
//
////            Log.e("타이틀",String.valueOf(Bundle_diary_adapter.check_diary));
//            // 일기 삭제 하면 쉐어드에서도 삭제 해야 한다
//
//
//            //item 선택 일기 수정후에 다시 값이 반영되도록
//            content_title = temporary_mydiary_content_title[temporary_mydiary_content_title.length - position - 1];
//            content = temporary_mydiary_content[temporary_mydiary_content.length - position - 1];
//            dairy_date = temporary_mydiary_dairy_date[temporary_mydiary_dairy_date.length - position - 1];
//            day = temporary_mydiary_day[temporary_mydiary_day.length - position - 1];
//            month = temporary_mydiary_month[temporary_mydiary_month.length - position - 1];
//            year = temporary_mydiary_year[temporary_mydiary_year.length - position - 1];
//
//
//////            Log.e("content_title",content_title);
////
////            //bundlediary_file 가져와서 풀어서 세팅하고 set으로 다시 입력하는 방법밖에 없을 듯
////            SharedPreferences sharedPreferences2 = getSharedPreferences("bundlediary_file", MODE_PRIVATE); //"bundlediary_file"파일의 데이터 받아오기
////            SharedPreferences.Editor editor = sharedPreferences.edit();
////            //bundlediary recycler view를 담는 데이터
////            String receive_bundlediary_title = sharedPreferences2.getString("save_bundlediary_title", ""); //받아온 데이터 String 변수 안에 넣기
////            String receive_bundlediary_content = sharedPreferences2.getString("save_bundlediary_content", ""); //받아온 데이터 String 변수 안에 넣기
////
////
//////            Log.e("번들 타이틀",receive_bundlediary_title);
////
////            String[] Array_bundlediary_title = receive_bundlediary_title.split("/"); //receive_dm_content 내용물을 split "/"으로 쪼개고 String 배열에 넣음
////            String[] Array_bundlediary_content = receive_bundlediary_content.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음
////
////
////            ArrayList<String> Arraylist_content_title = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
////            for (String temp : Array_bundlediary_title) {
////                Arraylist_content_title.add(temp);
////            }
////            Arraylist_content_title.set(Arraylist_content_title.size() - thisposition - 1, content_title); // ArrayList 에서 해당 포지션 제거
////
////
////            ArrayList<String> Arraylist_content = new ArrayList<>();
////            for (String temp : Array_bundlediary_content) {
////                Arraylist_content.add(temp);
////            }
////            Arraylist_content.set(Arraylist_content.size() - thisposition - 1, content);
////
////
////
////
////            if (Arraylist_content_title.size() != 0) {
////                for (int j = 0; j < Arraylist_content_title.size(); j++) { //기록 되어 있는 아이디 나열하기
////                    temporary_content_title2 = temporary_content_title2 + Arraylist_content_title.get(j) + "/";
////                }
////            }
////
//////            Log.e("receive_mydiary2", temporary_content_title);
////
////            if (Arraylist_content.size() != 0) {
////                for (int j = 0; j < Arraylist_content.size(); j++) { //기록 되어 있는 아이디 나열하기
////                    temporary_content2 = temporary_content2 + Arraylist_content.get(j) + "/";
////                }
////            }
////
//////            Log.e("번들 타이틀2",temporary_content_title2);
////
////            editor.putString("save_bundlediary_title", temporary_content_title2); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
////            editor.putString("save_bundlediary_content", temporary_content2);
////            editor.apply(); //동기,세이브를 완료 해라
//        }


        todaydairy_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //일기 수정 버튼
                Intent intent = new Intent(TodayDiaryCompleteActivity.this, TodayDiaryActivity.class);
                if (position != -1) { //position 값이 있다면, 값이 -1이면 최근 작성한 글
                    intent.putExtra("position", position); // TodayDiaryActivity 로 포지션 값 보내기
                }
                startActivity(intent);
            }
        });





//        imageButton.setOnClickListener(new View.OnClickListener() { //뒤로 가기 버튼 삭제 하자!
//            @Override
//            public void onClick(View view) { //뒤로 가기 버튼
//
//                if (diary_choice_text2.getText().equals("나의 일기")) { //나의 일기 선택 했을 때 데이터를 전달
//
//                    Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
//                    if (!content_title.equals("")) {
//                        intent.putExtra("content_title", content_title);
//                    }
//                    if (!content.equals("")) {
//                        intent.putExtra("content", content);
//                    }
//                    if (!dairy_date.equals("")) {
//                        intent.putExtra("dairy_date", dairy_date);
////                } if (!weekday.equals("")) {
////                    intent.putExtra("weekday", weekday);
//                    }
//                    if (!day.equals("")) {
//                        intent.putExtra("day", day);
//                    }
//                    if (!month.equals("")) {
//                        intent.putExtra("month", month);
//                    }
//                    if (!year.equals("")) {
//                        intent.putExtra("year", year);
//                    }
//
//                    checkcount = 1;
//                    startActivity(intent);
//                } else if (diary_choice_text2.getText().equals("공유 일기")){  //공유 일기 선택 했을 때 데이터를 전달
//                    Intent intent = new Intent(TodayDiaryCompleteActivity.this, MainpageActivity.class);
//                    if (!content_title.equals("")) {//content_title에 내용이 없지 않다면 실행
//                        intent.putExtra("content_title", content_title);
//                    }
//                    if (!content.equals("")) { //content에 내용이 없지 않다면 실행
//                        intent.putExtra("content", content);
//                    }
//                    if (arr != null) { //arr로 받는 값이 있으면 image를 보낸다
//                        intent.putExtra("image",arr);
//                    }
//
//
//
//                    checkcount = 1;
//                    startActivity(intent);
//                }
//
//                TodayDiaryActivity.checkcount = 0;
//                finish();
//            }
//        });



        todaydairy_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder diary_delete = new AlertDialog.Builder(TodayDiaryCompleteActivity.this); //삭제 여부 다이얼로그 생성
                diary_delete.setIcon(R.drawable.trash);
                diary_delete.setTitle("삭제 여부");
                diary_delete.setMessage("일기를 정말 지우시겠습니까?");

                diary_delete.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { //일기를 삭제한다

                        //선택한 일기 지우는 코드










                        if (Bundle_diary_adapter.check_diary == 1) {
                            //adapter에서 포지션 값 받아오기
                            delete_boolean = true; //여기서 삭제하면 Bundle_diary_adapter에서 해당 아이템 일기 삭제됨
                            int position1 = intent.getIntExtra("position", 0);
                            Bundle_diary_adapter.check_diary = 0;
                            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);

                            intent.putExtra("position1", position1); // BundleDiaryActivity 로 포지션 값 보내기
                            startActivity(intent);


                            // 해당 포지션에서 일기 안에 내용물들 삭제 하는 과정

                            SharedPreferences sharedPreferences = getSharedPreferences("TodayDiaryComplete_file", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            String receive_mydiary_content_title = sharedPreferences.getString("mydiary_content_title", ""); //받아온 데이터 String 변수 안에 넣기
                            String receive_mydiary_content = sharedPreferences.getString("mydiary_content", ""); //받아온 데이터 String 변수 안에 넣기
                            String receive_mydiary_dairy_date = sharedPreferences.getString("mydiary_dairy_date", "");
                            String receive_mydiary_day = sharedPreferences.getString("mydiary_day", "");
                            String receive_mydiary_month = sharedPreferences.getString("mydiary_month", "");
                            String receive_mydiary_year = sharedPreferences.getString("mydiary_year", "");

                            Log.e("전체 타이틀", receive_mydiary_content_title);

                            String[] temporary_mydiary_content_title = receive_mydiary_content_title.split("@"); //String 데이터 "@" 문자 기준으로 스플릿 해서 배열에 담기
                            String[] temporary_mydiary_content = receive_mydiary_content.split("@");
                            String[] temporary_mydiary_dairy_date = receive_mydiary_dairy_date.split("@");
                            String[] temporary_mydiary_day = receive_mydiary_day.split("@");
                            String[] temporary_mydiary_month = receive_mydiary_month.split("@");
                            String[] temporary_mydiary_year = receive_mydiary_year.split("@");


                            ArrayList<String> Arraylist_content_title = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_mydiary_content_title) {
                                Arraylist_content_title.add(temp);
                            }
                            Arraylist_content_title.remove(Arraylist_content_title.size() - position1 - 1); // ArrayList 에서 해당 포지션 제거


                            ArrayList<String> Arraylist_content = new ArrayList<>();
                            for (String temp : temporary_mydiary_content) {
                                Arraylist_content.add(temp);
                            }
                            Arraylist_content.remove(Arraylist_content.size() - position1 - 1);


                            ArrayList<String> Arraylist_dairy_date = new ArrayList<>();
                            for (String temp : temporary_mydiary_dairy_date) {
                                Arraylist_dairy_date.add(temp);
                            }
                            Arraylist_dairy_date.remove(Arraylist_dairy_date.size() - position1 - 1);


                            ArrayList<String> Arraylist_day = new ArrayList<>();
                            for (String temp : temporary_mydiary_day) {
                                Arraylist_day.add(temp);
                            }
                            Arraylist_day.remove(Arraylist_day.size() - position1 - 1);


                            ArrayList<String> Arraylist_month = new ArrayList<>();
                            for (String temp : temporary_mydiary_month) {
                                Arraylist_month.add(temp);
                            }
                            Arraylist_month.remove(Arraylist_month.size() - position1 - 1);


                            ArrayList<String> Arraylist_year = new ArrayList<>();
                            for (String temp : temporary_mydiary_year) {
                                Arraylist_year.add(temp);
                            }
                            Arraylist_year.remove(Arraylist_year.size() - position1 - 1);


                            if (Arraylist_content_title.size() != 0) {
                                for (int j = 0; j < Arraylist_content_title.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_content_title = temporary_content_title + Arraylist_content_title.get(j) + "@";
                                }
                            }




                            if (Arraylist_content.size() != 0) {
                                for (int j = 0; j < Arraylist_content.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_content = temporary_content + Arraylist_content.get(j) + "@";
                                }
                            }


                            if (Arraylist_dairy_date.size() != 0) {
                                for (int j = 0; j < Arraylist_dairy_date.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_dairy_date = temporary_dairy_date + Arraylist_dairy_date.get(j) + "@";
                                }
                            }


                            if (Arraylist_day.size() != 0) {
                                for (int j = 0; j < Arraylist_day.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_day = temporary_day + Arraylist_day.get(j) + "@";
                                }
                            }


                            if (Arraylist_month.size() != 0) {
                                for (int j = 0; j < Arraylist_month.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_month = temporary_month + Arraylist_month.get(j) + "@";
                                }
                            }


                            if (Arraylist_year.size() != 0) {
                                for (int j = 0; j < Arraylist_year.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_year = temporary_year + Arraylist_year.get(j) + "@";
                                }
                            }


                            editor.putString("mydiary_content_title", temporary_content_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
                            editor.putString("mydiary_content", temporary_content);
                            editor.putString("mydiary_dairy_date", temporary_dairy_date);
                            editor.putString("mydiary_day", temporary_day);
                            editor.putString("mydiary_month", temporary_month);
                            editor.putString("mydiary_year", temporary_year);
                            editor.apply(); //동기,세이브를 완료 해라

                            //배열도 같이 삭제 해야한다!!!!!


                        } else {
                            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
                            startActivity(intent);
                        }







                        dialogInterface.dismiss(); //다이얼로그 사라지기
                    }

                });
                diary_delete.setNegativeButton("취소", new DialogInterface.OnClickListener() { //삭제를 취소 한다
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); //다이얼로그 사라지기
                    }
                });
                diary_delete.show(); //다이얼로그 보여주기기

            }
        });


        shared_button.setOnClickListener(new View.OnClickListener() { //공유하기 버튼 눌렀을 때
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(Intent.EXTRA_TEXT, todaydairy_date.getText().toString() + "\n" + "오늘의 다이어트 : " + todaydairy_content_title.getText().toString() + "\n" + "내용 : " + todaydairy_content.getText().toString());
                intent.putExtra(Intent.EXTRA_TITLE, "제목");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "앱을 선택해 주세요"));
            }
        });


        diary_choice_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //일기 선택 스피너 버튼 눌렀을 때 기능 구현
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diary_choice_text2.setText(adapterView.getItemAtPosition(i).toString()); //나의 일기 인지, 공유 일기 인지
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();

        //보여줄 때에는 포지션 값 있는지 확인 하기


//        SharedPreferences sharedPreferences = getSharedPreferences("TodayDiaryComplete_file", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//
//        if (diary_choice_text2.getText().toString().equals("나의 일기")) {
//
//            if (thisposition == -1) {
//
//
//                String receive_mydiary_content_title = sharedPreferences.getString("mydiary_content_title", ""); //받아온 데이터 String 변수 안에 넣기
//                String receive_mydiary_content = sharedPreferences.getString("mydiary_content", ""); //받아온 데이터 String 변수 안에 넣기
//                String receive_mydiary_dairy_date = sharedPreferences.getString("mydiary_dairy_date", "");
//                String receive_mydiary_day = sharedPreferences.getString("mydiary_day", "");
//                String receive_mydiary_month = sharedPreferences.getString("mydiary_month", "");
//                String receive_mydiary_year = sharedPreferences.getString("mydiary_year", "");
//
//                receive_mydiary_content_title = receive_mydiary_content_title + content_title + "@";
//                receive_mydiary_content = receive_mydiary_content + content_title + "@";
//                receive_mydiary_dairy_date = receive_mydiary_dairy_date + content_title + "@";
//                receive_mydiary_day = receive_mydiary_day + content_title + "@";
//                receive_mydiary_month = receive_mydiary_month + content_title + "@";
//                receive_mydiary_year = receive_mydiary_year + content_title + "@";
//
////                Log.e("receive_mydiary_year", receive_mydiary_content_title);
//
//                editor.putString("mydiary_content_title", receive_mydiary_content_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
//                editor.putString("mydiary_content", receive_mydiary_content);
//                editor.putString("mydiary_dairy_date", receive_mydiary_dairy_date);
//                editor.putString("mydiary_day", receive_mydiary_day);
//                editor.putString("mydiary_month", receive_mydiary_month);
//                editor.putString("mydiary_year", receive_mydiary_year);



//                String[] temporary_mydiary_content_title = receive_mydiary_content_title.split("@"); //String 데이터 "@" 문자 기준으로 스플릿 해서 배열에 담기
//                String[] temporary_mydiary_content = receive_mydiary_content.split("@");
//                String[] temporary_mydiary_dairy_date = receive_mydiary_dairy_date.split("@");
//                String[] temporary_mydiary_day = receive_mydiary_day.split("@");
//                String[] temporary_mydiary_month = receive_mydiary_month.split("@");
//                String[] temporary_mydiary_year = receive_mydiary_year.split("@");
//
//
//                if (temporary_mydiary_content_title.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_content_title.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_content_title = save_mydiary_content_title + temporary_mydiary_content_title[i] + "@";
//                   }
//                }
//                save_mydiary_content_title = save_mydiary_content_title + content_title + "@";
//
//                if (temporary_mydiary_content.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_content.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_content = save_mydiary_content + temporary_mydiary_content[i] + "@";
//                    }
//                }
//                save_mydiary_content = save_mydiary_content + content + "@";
//
//                if (temporary_mydiary_dairy_date.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_dairy_date.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_dairy_date = save_mydiary_dairy_date + temporary_mydiary_dairy_date[i] + "@";
//                    }
//                }
//                save_mydiary_dairy_date = save_mydiary_dairy_date + dairy_date + "@";
//
//                if (temporary_mydiary_day.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_day.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_day = save_mydiary_day + temporary_mydiary_day[i] + "@";
//                    }
//                }
//                save_mydiary_day = save_mydiary_day + day + "@";
//
//                if (temporary_mydiary_month.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_month.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_month = save_mydiary_month + temporary_mydiary_month[i] + "@";
//                    }
//                }
//                save_mydiary_month = save_mydiary_month + month + "@";
//
//                if (temporary_mydiary_year.length != 0) {
//                    for (int i = 0; i < temporary_mydiary_year.length; i++) { //기록 되어 있는 아이디 나열하기
//                        save_mydiary_year = save_mydiary_year + temporary_mydiary_year[i] + "@";
//                    }
//                }
//                save_mydiary_year = save_mydiary_year + year + "@";
//
//                Log.e("receive_mydiary_year2", save_mydiary_content_title);
//
//
//                editor.putString("mydiary_content_title", save_mydiary_content_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
//                editor.putString("mydiary_content", save_mydiary_content);
//                editor.putString("mydiary_dairy_date", save_mydiary_dairy_date);
//                editor.putString("mydiary_day", save_mydiary_day);
//                editor.putString("mydiary_month", save_mydiary_month);
//                editor.putString("mydiary_year", save_mydiary_year);

//            }
//        }


//        else if (diary_choice_text2.getText().toString().equals("공유 일기")) {
//            editor.putString("sharediary_content_title", content_title); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
//            editor.putString("sharediary_content", content);
//            editor.putString("sharediary_dairy_date", dairy_date);
//            editor.putString("sharediary_day", day);
//            editor.putString("sharediary_month", month);
//            editor.putString("sharediary_year", year);
//        }



//        editor.clear(); //sharedPreferences 저장되어 있는 파일 지우기
//        editor.apply(); //동기,세이브를 완료 해라


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if (diary_choice_text2.getText().equals("나의 일기")) { //나의 일기 선택 했을 때 데이터를 전달\

            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
            //Bundle_diary_adapter 에서 넘어 왔을 때에는 밑에 코드가 작동 안하게 한다
//            if (Bundle_diary_adapter.check_diary != 1) { //TodayDiaryCompleteActivity 에서 자료가 왔을 때에만 데이터를 BundleDiaryActivity로 보내서 추가 되도록 한다.
////
////                Log.e("확인", "위치 확인");
////
////                if (!content_title.equals("")) {  //뒤로가기 눌렀을 때 아무 값이 없어서 NullPointerException이 뜬다!!! 아무값 없으면 뭐라도 받을 수 있도록 하자!!!!!!
////                    intent.putExtra("content_title", content_title);
////                }
////                if (!content.equals("")) {
////                    intent.putExtra("content", content);
////                }
////                if (!dairy_date.equals("")) {
////                    intent.putExtra("dairy_date", dairy_date);
////                }
////                if (!day.equals("")) {
////                    intent.putExtra("day", day);
////                }
////                if (!month.equals("")) {
////                    intent.putExtra("month", month);
////                }
////                if (!year.equals("")) {
////                    intent.putExtra("year", year);
////                }
////
////                checkcount = 1; //BundleDiaryActivity 로 보내서 리스트에 추가 되도록 만들기
////            }
////            Bundle_diary_adapter.check_diary = 0;
            finish();
            startActivity(intent);


        } else if (diary_choice_text2.getText().equals("공유 일기")) {  //공유 일기 선택 했을 때 데이터를 전달


            Intent intent = new Intent(TodayDiaryCompleteActivity.this, MainpageActivity.class);
//            if (!content_title.equals("")) {//content_title에 내용이 없지 않다면 실행
//                intent.putExtra("content_title", content_title);
//            }
//            if (!content.equals("")) { //content에 내용이 없지 않다면 실행
//                intent.putExtra("content", content);
//            }
//            if (arr != null) { //arr로 받는 값이 있으면 image를 보낸다
//                intent.putExtra("image", arr);
//            }
//            checkcount = 2; //MainpageActivity 로 보내서 리스트에 추가 되도록 만들기
            finish();
            startActivity(intent);

        }


    }
}