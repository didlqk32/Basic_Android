package com.example.myapplication;

import androidx.annotation.NonNull;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private TextView todaydairy_content_title, todaydairy_content, todaydairy_date,diary_share_text;
    private ImageButton imageButton, shared_button;
    private Button todaydairy_share;
    private String content_title, content, dairy_date, weekday, day, month, year;
//    private Spinner diary_choice_spinner2;
    private TextView diary_choice_text2; //나의 일기 인지, 고융 일기 인지
    public static int checkcount = 0;

    private ImageView todaydairy_delete; //삭제 버튼
    public static boolean delete_boolean = false;

    private String temporary_id = "";
    private String temporary_title = "";
    private String temporary_content = "";
    private String temporary_dairy_date = "";
    private String temporary_day = "";
    private String temporary_month = "";
    private String temporary_year = "";
    private String temporary_image = "";
    private String temporary_heart_count = "";
    private String temporary_comment_count = "";
    private String temporary_profile_nickname = "";
    private String temporary_profile_image = "";
    private String temporary_share = "";


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
        todaydairy_share = findViewById(R.id.todaydairy_share);
        diary_share_text = findViewById(R.id.diary_share_text);
//        shared_button = findViewById(R.id.shared_button);

//        diary_choice_spinner2 = findViewById(R.id.diary_choice_spinner2); //스피너 연결
        diary_choice_text2 = findViewById(R.id.diary_choice_text2); //스피너 선택 적용될 텍스트 //나의 일기 인지, 공개 일기 인지
        todaydairy_delete = findViewById(R.id.todaydairy_delete); // 삭제 버튼(이미지 뷰)

    }


    @Override
    protected void onStart() {
        super.onStart();


        //방금 등록된 일기 보여주기
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_diary_id = shared.getString("diary_id", ""); //파일에서 받은 데이터 string 변수에 넣는다
        String receive_diary_title = shared.getString("diary_title", "");
        String receive_diary_content = shared.getString("diary_content", "");
        String receive_diary_date = shared.getString("diary_date", "");
        String receive_diary_image = shared.getString("diary_image", "");
        String receive_diary_share = shared.getString("diary_share", "");

        // 이미지 있는지 없는지에 대한 예외 처리 해줘야 한다
        // 현재 다이어리가 몇번 째 다이어리인지 찾아야 한다
        // if 다이어리가 작성에서 넘어 왔다면(포지션 값이 없다면) 마지막 다이어리 보여주면 되고 번들에서 넘어 왔으면 포지션 값 받으면 된다....
        // 포지션 값이 없으면 -1로 설정

//        Log.e("이미지 내용",receive_diary_image);

        String[] temporary_diary_id = receive_diary_id.split("@"); //string으로 되어 있는 데이터 split을 잘라서 배열에 하나씩 넣는다
        String[] temporary_diary_title = receive_diary_title.split("@");
        String[] temporary_diary_content = receive_diary_content.split("@");
        String[] temporary_diary_date = receive_diary_date.split("@");
        String[] temporary_diary_image = receive_diary_image.split("@");
        String[] temporary_diary_share = receive_diary_share.split("@");



        final Intent intent = getIntent(); //intent 값을 Bundle_diary_adapter 에서 받아온다
        final int position = intent.getIntExtra("position", -1); //Bundle_diary에서 선택한 일기의 포지션 값을 받고 처음 작성한 일기라면 초기값으로 -1을 받는다
        final String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        final String date = intent.getStringExtra("date");






//        Log.e("받은 포지션 값",String.valueOf(position));

        if (position == -1 || position == -2) { //position 값이 -1 이면 일기를 처음 작성 했다는 뜻




            String abc = temporary_diary_image[0];
//            Log.e("다이어리쪽 이미지 길이",String.valueOf(abc.length()));



//            Log.e("위치","확인");

            todaydairy_content_title.setText(temporary_diary_title[temporary_diary_title.length - 1]); //배열 길이는 1부터 시작하고 배열 인덱스 값은 0부터 시작하기 때문에 길이에 -1 해야 가장 최근 등록 된 것
            todaydairy_content.setText(temporary_diary_content[temporary_diary_content.length - 1]);
            todaydairy_date.setText(temporary_diary_date[temporary_diary_date.length - 1]);






//            String 이미지를 bitmap으로 바꾸는 작업
            byte[] encodeByte = Base64.decode(temporary_diary_image[temporary_diary_image.length - 1], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기




            if (!temporary_diary_image[temporary_diary_image.length - 1].equals("null")) { //방금 작성한 일기의 이미지가 "null"이 아니면 이미지 보이도록 한다
                todaydairy_image.setVisibility(View.VISIBLE);
                todaydairy_image.setImageBitmap(bitmapimage);
            }









        } else { //일기를 수정하는 경우


//            Log.e("위치2","확인");


            for (int i = 0; i < temporary_diary_id.length; i++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
                if (temporary_diary_id[i].equals(logInActivity.my_id) && temporary_diary_title[i].equals(title) && temporary_diary_content[i].equals(content) && temporary_diary_date[i].equals(date)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기

//                    Log.e("위치3","확인");

                    todaydairy_content_title.setText(temporary_diary_title[i]); //배열 길이는 1부터 시작하고 배열 인덱스 값은 0부터 시작하기 때문에 길이에 -1 해야 가장 최근 등록 된 것
                    todaydairy_content.setText(temporary_diary_content[i]);
                    todaydairy_date.setText(temporary_diary_date[i]);













                    //String 이미지를 bitmap으로 바꾸는 작업
                    byte[] encodeByte = Base64.decode(temporary_diary_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                    Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기













//            Log.e("포지션 값",String.valueOf(position));
                    if (!temporary_diary_image[i].equals("null")) { //선택한 일기의 이미지가 "null"이 아니면 이미지 보이도록 한다
                        todaydairy_image.setVisibility(View.VISIBLE);
                        todaydairy_image.setImageBitmap(bitmapimage);
                    }


                    if (temporary_diary_share[i].equals("share")) { //현재 일기가 공개 하기 이면
                        diary_share_text.setText("(공개중)");
                    }



                }
            }


        }




        todaydairy_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //일기 수정 버튼
                Intent intent = new Intent(TodayDiaryCompleteActivity.this, TodayDiaryActivity.class);
                if (position != -1) { //position 값이 있다면, 값이 -1이면 최근 작성한 글
                    intent.putExtra("position", position); // TodayDiaryActivity 로 포지션 값 보내기
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("date", date);


                } else {
                    intent.putExtra("position", -2); // TodayDiaryActivity 로 포지션 값 보내기, 방금 작성했던 일기를 뜻 함
                }
                startActivity(intent);
            }
        });




        todaydairy_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 일기를 삭제하는 경우

                AlertDialog.Builder diary_delete = new AlertDialog.Builder(TodayDiaryCompleteActivity.this); //삭제 여부 다이얼로그 생성
                diary_delete.setIcon(R.drawable.trash);
                diary_delete.setTitle("삭제 여부");
                diary_delete.setMessage("일기를 정말 지우시겠습니까?");

                diary_delete.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { //일기를 삭제한다

                        //선택한 일기 지우는 코드, 모든 파일 해당하는 포지션에서 하나씩 지워야 한다
                        if (Bundle_diary_adapter.check_diary == 1) { //해당 일기가 bundle 일기에서 선택 했다면 check_diary = 1, 최근 작성 되었다면 0 이다
                            Bundle_diary_adapter.check_diary = 0; // 사용한 후에는 초기값으로 돌려야 한다?

                            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
                            startActivity(intent);

                            SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                            SharedPreferences.Editor editor = shared.edit();

                            String receive_diary_id = shared.getString("diary_id", ""); //파일에서 받은 데이터 string 변수에 넣는다
                            String receive_diary_title = shared.getString("diary_title", "");
                            String receive_diary_content = shared.getString("diary_content", "");
                            String receive_diary_date = shared.getString("diary_date", "");
                            String receive_diary_day = shared.getString("diary_day", "");
                            String receive_diary_month = shared.getString("diary_month", "");
                            String receive_diary_year = shared.getString("diary_year", "");
                            String receive_diary_image = shared.getString("diary_image", "");
                            String receive_diary_heart_count = shared.getString("diary_heart_count", "");
                            String receive_diary_comment_count = shared.getString("diary_comment_count", "");
                            String receive_diary_profile_nickname = shared.getString("diary_profile_nickname", "");
                            String receive_diary_profile_image = shared.getString("diary_profile_image", "");
                            String receive_diary_share = shared.getString("diary_share", "");


                            String[] temporary_diary_id = receive_diary_id.split("@"); //string으로 되어 있는 데이터 split을 잘라서 배열에 하나씩 넣는다
                            String[] temporary_diary_title = receive_diary_title.split("@");
                            String[] temporary_diary_content = receive_diary_content.split("@");
                            String[] temporary_diary_date = receive_diary_date.split("@");
                            String[] temporary_diary_day = receive_diary_day.split("@");
                            String[] temporary_diary_month = receive_diary_month.split("@");
                            String[] temporary_diary_year = receive_diary_year.split("@");
                            String[] temporary_diary_image = receive_diary_image.split("@");
                            String[] temporary_diary_heart_count = receive_diary_heart_count.split("@");
                            String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");
                            String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@");
                            String[] temporary_diary_profile_image = receive_diary_profile_image.split("@");
                            String[] temporary_diary_share = receive_diary_share.split("@");


                            ArrayList<String> Arraylist_id = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_diary_id) {
                                Arraylist_id.add(temp);
                            }


                            ArrayList<String> Arraylist_title = new ArrayList<>();
                            for (String temp : temporary_diary_title) {
                                Arraylist_title.add(temp);
                            }


                            ArrayList<String> Arraylist_content = new ArrayList<>();
                            for (String temp : temporary_diary_content) {
                                Arraylist_content.add(temp);
                            }


                            ArrayList<String> Arraylist_date = new ArrayList<>();
                            for (String temp : temporary_diary_date) {
                                Arraylist_date.add(temp);
                            }


                            ArrayList<String> Arraylist_day = new ArrayList<>();
                            for (String temp : temporary_diary_day) {
                                Arraylist_day.add(temp);
                            }


                            ArrayList<String> Arraylist_month = new ArrayList<>();
                            for (String temp : temporary_diary_month) {
                                Arraylist_month.add(temp);
                            }


                            ArrayList<String> Arraylist_year = new ArrayList<>();
                            for (String temp : temporary_diary_year) {
                                Arraylist_year.add(temp);
                            }


                            ArrayList<String> Arraylist_image = new ArrayList<>();
                            for (String temp : temporary_diary_image) {
                                Arraylist_image.add(temp);
                            }


                            ArrayList<String> Arraylist_heart_count = new ArrayList<>();
                            for (String temp : temporary_diary_heart_count) {
                                Arraylist_heart_count.add(temp);
                            }


                            ArrayList<String> Arraylist_comment_count = new ArrayList<>();
                            for (String temp : temporary_diary_comment_count) {
                                Arraylist_comment_count.add(temp);
                            }


                            ArrayList<String> Arraylist_profile_nickname = new ArrayList<>();
                            for (String temp : temporary_diary_profile_nickname) {
                                Arraylist_profile_nickname.add(temp);
                            }


                            ArrayList<String> Arraylist_profile_image = new ArrayList<>();
                            for (String temp : temporary_diary_profile_image) {
                                Arraylist_profile_image.add(temp);
                            }


                            ArrayList<String> Arraylist_share = new ArrayList<>();
                            for (String temp : temporary_diary_share) {
                                Arraylist_share.add(temp);
                            }



                            for (int h = 0; h < temporary_diary_id.length; h++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
                                if (temporary_diary_id[h].equals(logInActivity.my_id) && temporary_diary_title[h].equals(title) && temporary_diary_content[h].equals(content) && temporary_diary_date[h].equals(date)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기

                                    Arraylist_id.remove(h); // ArrayList 에서 해당 포지션 제거
                                    Arraylist_title.remove(h);
                                    Arraylist_content.remove(h);
                                    Arraylist_date.remove(h);
                                    Arraylist_day.remove(h);
                                    Arraylist_month.remove(h);
                                    Arraylist_year.remove(h);
                                    Arraylist_image.remove(h);
                                    Arraylist_heart_count.remove(h);
                                    Arraylist_comment_count.remove(h);
                                    Arraylist_profile_nickname.remove(h);
                                    Arraylist_profile_image.remove(h);
                                    Arraylist_share.remove(h);

                                }
                            }





                            if (Arraylist_id.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_id.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_id = temporary_id + Arraylist_id.get(j) + "@";
                                }
                            }

                            if (Arraylist_title.size() != 0) {
                                for (int j = 0; j < Arraylist_title.size(); j++) {
                                    temporary_title = temporary_title + Arraylist_title.get(j) + "@";
                                }
                            }

                            if (Arraylist_content.size() != 0) {
                                for (int j = 0; j < Arraylist_content.size(); j++) {
                                    temporary_content = temporary_content + Arraylist_content.get(j) + "@";
                                }
                            }

                            if (Arraylist_date.size() != 0) {
                                for (int j = 0; j < Arraylist_date.size(); j++) {
                                    temporary_dairy_date = temporary_dairy_date + Arraylist_date.get(j) + "@";
                                }
                            }

                            if (Arraylist_day.size() != 0) {
                                for (int j = 0; j < Arraylist_day.size(); j++) {
                                    temporary_day = temporary_day + Arraylist_day.get(j) + "@";
                                }
                            }

                            if (Arraylist_month.size() != 0) {
                                for (int j = 0; j < Arraylist_month.size(); j++) {
                                    temporary_month = temporary_month + Arraylist_month.get(j) + "@";
                                }
                            }

                            if (Arraylist_year.size() != 0) {
                                for (int j = 0; j < Arraylist_year.size(); j++) {
                                    temporary_year = temporary_year + Arraylist_year.get(j) + "@";
                                }
                            }

                            if (Arraylist_image.size() != 0) {
                                for (int j = 0; j < Arraylist_image.size(); j++) {
                                    temporary_image = temporary_image + Arraylist_image.get(j) + "@";
                                }
                            }

                            if (Arraylist_heart_count.size() != 0) {
                                for (int j = 0; j < Arraylist_heart_count.size(); j++) {
                                    temporary_heart_count = temporary_heart_count + Arraylist_heart_count.get(j) + "@";
                                }
                            }

                            if (Arraylist_comment_count.size() != 0) {
                                for (int j = 0; j < Arraylist_comment_count.size(); j++) {
                                    temporary_comment_count = temporary_comment_count + Arraylist_comment_count.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_nickname.size() != 0) {
                                for (int j = 0; j < Arraylist_profile_nickname.size(); j++) {
                                    temporary_profile_nickname = temporary_profile_nickname + Arraylist_profile_nickname.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_image.size() != 0) {
                                for (int j = 0; j < Arraylist_profile_image.size(); j++) {
                                    temporary_profile_image = temporary_profile_image + Arraylist_profile_image.get(j) + "@";
                                }
                            }

                            if (Arraylist_share.size() != 0) {
                                for (int j = 0; j < Arraylist_share.size(); j++) {
                                    temporary_share = temporary_share + Arraylist_share.get(j) + "@";
                                }
                            }


                            editor.putString("diary_id", temporary_id); // 해당 포지션 일기 삭제하고 문자열로 만든 데이터들 다시 저장하기
                            editor.putString("diary_title", temporary_title);
                            editor.putString("diary_content", temporary_content);
                            editor.putString("diary_date", temporary_dairy_date);
                            editor.putString("diary_day", temporary_day);
                            editor.putString("diary_month", temporary_month);
                            editor.putString("diary_year", temporary_year);
                            editor.putString("diary_image", temporary_image);
                            editor.putString("diary_heart_count", temporary_heart_count);
                            editor.putString("diary_comment_count", temporary_comment_count);
                            editor.putString("diary_profile_nickname", temporary_profile_nickname);
                            editor.putString("diary_profile_image", temporary_profile_image);
                            editor.putString("diary_share", temporary_share);

                            editor.apply(); //동기화,세이브를 완료 해라
                        } else {
                            //방금 작성한 일기 지우기
                            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
                            startActivity(intent);


                            SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                            SharedPreferences.Editor editor = shared.edit();

                            String receive_diary_id = shared.getString("diary_id", ""); //파일에서 받은 데이터 string 변수에 넣는다
                            String receive_diary_title = shared.getString("diary_title", "");
                            String receive_diary_content = shared.getString("diary_content", "");
                            String receive_diary_date = shared.getString("diary_date", "");
                            String receive_diary_day = shared.getString("diary_day", "");
                            String receive_diary_month = shared.getString("diary_month", "");
                            String receive_diary_year = shared.getString("diary_year", "");
                            String receive_diary_image = shared.getString("diary_image", "");
                            String receive_diary_heart_count = shared.getString("diary_heart_count", "");
                            String receive_diary_comment_count = shared.getString("diary_comment_count", "");
                            String receive_diary_profile_nickname = shared.getString("diary_profile_nickname", "");
                            String receive_diary_profile_image = shared.getString("diary_profile_image", "");
                            String receive_diary_share = shared.getString("diary_share", "");


                            String[] temporary_diary_id = receive_diary_id.split("@"); //string으로 되어 있는 데이터 split을 잘라서 배열에 하나씩 넣는다
                            String[] temporary_diary_title = receive_diary_title.split("@");
                            String[] temporary_diary_content = receive_diary_content.split("@");
                            String[] temporary_diary_date = receive_diary_date.split("@");
                            String[] temporary_diary_day = receive_diary_day.split("@");
                            String[] temporary_diary_month = receive_diary_month.split("@");
                            String[] temporary_diary_year = receive_diary_year.split("@");
                            String[] temporary_diary_image = receive_diary_image.split("@");
                            String[] temporary_diary_heart_count = receive_diary_heart_count.split("@");
                            String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");
                            String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@");
                            String[] temporary_diary_profile_image = receive_diary_profile_image.split("@");
                            String[] temporary_diary_share = receive_diary_share.split("@");

                            ArrayList<String> Arraylist_id = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_diary_id) {
                                Arraylist_id.add(temp);
                            }
                            Arraylist_id.remove(Arraylist_id.size() - 1); // ArrayList 에서 해당 포지션 제거

                            ArrayList<String> Arraylist_title = new ArrayList<>();
                            for (String temp : temporary_diary_title) {
                                Arraylist_title.add(temp);
                            }
                            Arraylist_title.remove(Arraylist_title.size() - 1);

                            ArrayList<String> Arraylist_content = new ArrayList<>();
                            for (String temp : temporary_diary_content) {
                                Arraylist_content.add(temp);
                            }
                            Arraylist_content.remove(Arraylist_content.size() - 1);

                            ArrayList<String> Arraylist_date = new ArrayList<>();
                            for (String temp : temporary_diary_date) {
                                Arraylist_date.add(temp);
                            }
                            Arraylist_date.remove(Arraylist_date.size() - 1);

                            ArrayList<String> Arraylist_day = new ArrayList<>();
                            for (String temp : temporary_diary_day) {
                                Arraylist_day.add(temp);
                            }
                            Arraylist_day.remove(Arraylist_day.size() - 1);

                            ArrayList<String> Arraylist_month = new ArrayList<>();
                            for (String temp : temporary_diary_month) {
                                Arraylist_month.add(temp);
                            }
                            Arraylist_month.remove(Arraylist_month.size() - 1);

                            ArrayList<String> Arraylist_year = new ArrayList<>();
                            for (String temp : temporary_diary_year) {
                                Arraylist_year.add(temp);
                            }
                            Arraylist_year.remove(Arraylist_year.size() - 1);

                            ArrayList<String> Arraylist_image = new ArrayList<>();
                            for (String temp : temporary_diary_image) {
                                Arraylist_image.add(temp);
                            }
                            Arraylist_image.remove(Arraylist_image.size() - 1);

                            ArrayList<String> Arraylist_heart_count = new ArrayList<>();
                            for (String temp : temporary_diary_heart_count) {
                                Arraylist_heart_count.add(temp);
                            }
                            Arraylist_heart_count.remove(Arraylist_heart_count.size() - 1);

                            ArrayList<String> Arraylist_comment_count = new ArrayList<>();
                            for (String temp : temporary_diary_comment_count) {
                                Arraylist_comment_count.add(temp);
                            }
                            Arraylist_comment_count.remove(Arraylist_comment_count.size() - 1);

                            ArrayList<String> Arraylist_profile_nickname = new ArrayList<>();
                            for (String temp : temporary_diary_profile_nickname) {
                                Arraylist_profile_nickname.add(temp);
                            }
                            Arraylist_profile_nickname.remove(Arraylist_profile_nickname.size() - 1);

                            ArrayList<String> Arraylist_profile_image = new ArrayList<>();
                            for (String temp : temporary_diary_profile_image) {
                                Arraylist_profile_image.add(temp);
                            }
                            Arraylist_profile_image.remove(Arraylist_profile_image.size() - 1);

                            ArrayList<String> Arraylist_share = new ArrayList<>();
                            for (String temp : temporary_diary_share) {
                                Arraylist_share.add(temp);
                            }
                            Arraylist_share.remove(Arraylist_share.size() - 1);




                            if (Arraylist_id.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_id.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_id = temporary_id + Arraylist_id.get(j) + "@";
                                }
                            }

                            if (Arraylist_title.size() != 0) {
                                for (int j = 0; j < Arraylist_title.size(); j++) {
                                    temporary_title = temporary_title + Arraylist_title.get(j) + "@";
                                }
                            }

                            if (Arraylist_content.size() != 0) {
                                for (int j = 0; j < Arraylist_content.size(); j++) {
                                    temporary_content = temporary_content + Arraylist_content.get(j) + "@";
                                }
                            }

                            if (Arraylist_date.size() != 0) {
                                for (int j = 0; j < Arraylist_date.size(); j++) {
                                    temporary_dairy_date = temporary_dairy_date + Arraylist_date.get(j) + "@";
                                }
                            }

                            if (Arraylist_day.size() != 0) {
                                for (int j = 0; j < Arraylist_day.size(); j++) {
                                    temporary_day = temporary_day + Arraylist_day.get(j) + "@";
                                }
                            }

                            if (Arraylist_month.size() != 0) {
                                for (int j = 0; j < Arraylist_month.size(); j++) {
                                    temporary_month = temporary_month + Arraylist_month.get(j) + "@";
                                }
                            }

                            if (Arraylist_year.size() != 0) {
                                for (int j = 0; j < Arraylist_year.size(); j++) {
                                    temporary_year = temporary_year + Arraylist_year.get(j) + "@";
                                }
                            }

                            if (Arraylist_image.size() != 0) {
                                for (int j = 0; j < Arraylist_image.size(); j++) {
                                    temporary_image = temporary_image + Arraylist_image.get(j) + "@";
                                }
                            }

                            if (Arraylist_heart_count.size() != 0) {
                                for (int j = 0; j < Arraylist_heart_count.size(); j++) {
                                    temporary_heart_count = temporary_heart_count + Arraylist_heart_count.get(j) + "@";
                                }
                            }

                            if (Arraylist_comment_count.size() != 0) {
                                for (int j = 0; j < Arraylist_comment_count.size(); j++) {
                                    temporary_comment_count = temporary_comment_count + Arraylist_comment_count.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_nickname.size() != 0) {
                                for (int j = 0; j < Arraylist_profile_nickname.size(); j++) {
                                    temporary_profile_nickname = temporary_profile_nickname + Arraylist_profile_nickname.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_image.size() != 0) {
                                for (int j = 0; j < Arraylist_profile_image.size(); j++) {
                                    temporary_profile_image = temporary_profile_image + Arraylist_profile_image.get(j) + "@";
                                }
                            }

                            if (Arraylist_share.size() != 0) {
                                for (int j = 0; j < Arraylist_share.size(); j++) {
                                    temporary_share = temporary_share + Arraylist_share.get(j) + "@";
                                }
                            }


                            editor.putString("diary_id", temporary_id); // 해당 포지션 일기 삭제하고 문자열로 만든 데이터들 다시 저장하기
                            editor.putString("diary_title", temporary_title);
                            editor.putString("diary_content", temporary_content);
                            editor.putString("diary_date", temporary_dairy_date);
                            editor.putString("diary_day", temporary_day);
                            editor.putString("diary_month", temporary_month);
                            editor.putString("diary_year", temporary_year);
                            editor.putString("diary_image", temporary_image);
                            editor.putString("diary_heart_count", temporary_heart_count);
                            editor.putString("diary_comment_count", temporary_comment_count);
                            editor.putString("diary_profile_nickname", temporary_profile_nickname);
                            editor.putString("diary_profile_image", temporary_profile_image);
                            editor.putString("diary_share", temporary_share);

                            editor.apply(); //동기화,세이브를 완료 해라
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


        todaydairy_share.setOnClickListener(new View.OnClickListener() { //공개 하기 버튼 눌렀을 경우
            @Override
            public void onClick(View view) { //일기 공개 하기

                AlertDialog.Builder diary_share = new AlertDialog.Builder(TodayDiaryCompleteActivity.this); //삭제 여부 다이얼로그 생성
                diary_share.setIcon(R.drawable.share);
                diary_share.setTitle("공개 여부");
                diary_share.setMessage("일기를 다른 사람들에게 공개 하시겠습니까?");

                diary_share.setPositiveButton("공개 하기", new DialogInterface.OnClickListener() { //선택한 일기 공개하기
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                        SharedPreferences.Editor editor = shared.edit();

                        String receive_diary_id = shared.getString("diary_id", ""); //파일에서 받은 데이터 string 변수에 넣는다
                        String receive_diary_title = shared.getString("diary_title", "");
                        String receive_diary_content = shared.getString("diary_content", "");
                        String receive_diary_date = shared.getString("diary_date", "");
                        String receive_diary_share = shared.getString("diary_share", "");

                        String[] temporary_diary_id = receive_diary_id.split("@"); //string으로 되어 있는 데이터 split을 잘라서 배열에 하나씩 넣는다
                        String[] temporary_diary_title = receive_diary_title.split("@");
                        String[] temporary_diary_content = receive_diary_content.split("@");
                        String[] temporary_diary_date = receive_diary_date.split("@");
                        String[] temporary_diary_share = receive_diary_share.split("@");


                        ArrayList<String> Arraylist_share = new ArrayList<>(); // 배열을 ArrayList로 담는 과정
                        for (String temp : temporary_diary_share) {
                            Arraylist_share.add(temp);
                        }

                        for (int h = 0; h < temporary_diary_id.length; h++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
                            if (temporary_diary_id[h].equals(logInActivity.my_id) && temporary_diary_title[h].equals(title) && temporary_diary_content[h].equals(content) && temporary_diary_date[h].equals(date)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                                Arraylist_share.set(h,"share");
                            }
                        }

                        if (Arraylist_share.size() != 0) {
                            for (int j = 0; j < Arraylist_share.size(); j++) {
                                temporary_share = temporary_share + Arraylist_share.get(j) + "@";
                            }
                        }

                        editor.putString("diary_share", temporary_share);
                        editor.apply(); //동기화,세이브를 완료 해라


                        if (diary_share_text.getText().toString().equals("")){
                            Toast.makeText(TodayDiaryCompleteActivity.this,"일기가 공개 되었습니다",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TodayDiaryCompleteActivity.this,MainpageActivity.class);
                            startActivity(intent);
                        } else if (diary_share_text.getText().toString().equals("(공개중)")) {
                            Toast.makeText(TodayDiaryCompleteActivity.this,"일기가 이미 공개중 입니다",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                diary_share.setNegativeButton("비공개 하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (diary_share_text.getText().toString().equals("")){
                            Toast.makeText(TodayDiaryCompleteActivity.this,"일기가 공개중이 아닙니다",Toast.LENGTH_SHORT).show();
                        } else if (diary_share_text.getText().toString().equals("(공개중)")) {
                            Toast.makeText(TodayDiaryCompleteActivity.this,"일기가 비공개 되었습니다",Toast.LENGTH_SHORT).show();



                            SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                            SharedPreferences.Editor editor = shared.edit();

                            String receive_diary_id = shared.getString("diary_id", ""); //파일에서 받은 데이터 string 변수에 넣는다
                            String receive_diary_title = shared.getString("diary_title", "");
                            String receive_diary_content = shared.getString("diary_content", "");
                            String receive_diary_date = shared.getString("diary_date", "");
                            String receive_diary_share = shared.getString("diary_share", "");
                            String receive_diary_comment_count = shared.getString("diary_comment_count", "");

                            String[] temporary_diary_id = receive_diary_id.split("@"); //string으로 되어 있는 데이터 split을 잘라서 배열에 하나씩 넣는다
                            String[] temporary_diary_title = receive_diary_title.split("@");
                            String[] temporary_diary_content = receive_diary_content.split("@");
                            String[] temporary_diary_date = receive_diary_date.split("@");
                            String[] temporary_diary_share = receive_diary_share.split("@");
                            String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");


                            ArrayList<String> Arraylist_share = new ArrayList<>(); // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_diary_share) {
                                Arraylist_share.add(temp);
                            }

                            ArrayList<String> Arraylist_comment_count = new ArrayList<>(); // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_diary_comment_count) {
                                Arraylist_comment_count.add(temp);
                            }


                            for (int h = 0; h < temporary_diary_id.length; h++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
                                if (temporary_diary_id[h].equals(logInActivity.my_id) && temporary_diary_title[h].equals(title) && temporary_diary_content[h].equals(content) && temporary_diary_date[h].equals(date)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기
                                    Arraylist_share.set(h,"null");
                                    Arraylist_comment_count.set(h,"0");
                                }
                            }

                            if (Arraylist_share.size() != 0) {
                                for (int j = 0; j < Arraylist_share.size(); j++) {
                                    temporary_share = temporary_share + Arraylist_share.get(j) + "@";
                                }
                            }

                            String temporary_comment_count_change = "";
                            if (Arraylist_comment_count.size() != 0) {
                                for (int j = 0; j < Arraylist_comment_count.size(); j++) {
                                    temporary_comment_count_change = temporary_comment_count_change + Arraylist_comment_count.get(j) + "@";
                                }
                            }

                            editor.putString("diary_comment_count", temporary_comment_count_change);
                            editor.putString("diary_share", temporary_share);
                            editor.apply(); //동기화,세이브를 완료 해라












                            //일기를 다시 비공개 하면 해당 댓글은 모두 사라진다

                            //일기를 다시 비공개 하면 해당 댓글은 모두 사라진다

                            SharedPreferences sharedPreferences = getSharedPreferences("Comments_data_file", MODE_PRIVATE);
                            SharedPreferences.Editor editorshared = sharedPreferences.edit();

                            String receive_Comments_compare_id = sharedPreferences.getString("Comments_compare_id", "");
                            String receive_Comments_compare_title = sharedPreferences.getString("Comments_compare_title", "");
                            String receive_Comments_compare_content = sharedPreferences.getString("Comments_compare_content", "");
                            String receive_Comments_compare_date = sharedPreferences.getString("Comments_compare_date", "");
                            String receive_Comments_comment = sharedPreferences.getString("Comments_comment", "");
                            String receive_Comments_profile_nickname = sharedPreferences.getString("Comments_profile_nickname", "");
                            String receive_Comments_profile_image = sharedPreferences.getString("Comments_profile_image", "");

                            String[] temporary_Comments_compare_id = receive_Comments_compare_id.split("@");
                            String[] temporary_Comments_compare_title = receive_Comments_compare_title.split("@");
                            String[] temporary_Comments_compare_content = receive_Comments_compare_content.split("@");
                            String[] temporary_Comments_compare_date = receive_Comments_compare_date.split("@");
                            String[] temporary_Comments_comment = receive_Comments_comment.split("@");
                            String[] temporary_Comments_profile_nickname = receive_Comments_profile_nickname.split("@");
                            String[] temporary_Comments_profile_image = receive_Comments_profile_image.split("@");


                            ArrayList<String> Arraylist_compare_id = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                            for (String temp : temporary_Comments_compare_id) {
                                Arraylist_compare_id.add(temp);
                            }

                            ArrayList<String> Arraylist_compare_title = new ArrayList<>();
                            for (String temp : temporary_Comments_compare_title) {
                                Arraylist_compare_title.add(temp);
                            }

                            ArrayList<String> Arraylist_compare_content = new ArrayList<>();
                            for (String temp : temporary_Comments_compare_content) {
                                Arraylist_compare_content.add(temp);
                            }

                            ArrayList<String> Arraylist_compare_date = new ArrayList<>();
                            for (String temp : temporary_Comments_compare_date) {
                                Arraylist_compare_date.add(temp);
                            }

                            ArrayList<String> Arraylist_Comments_comment = new ArrayList<>();
                            for (String temp : temporary_Comments_comment) {
                                Arraylist_Comments_comment.add(temp);
                            }

                            ArrayList<String> Arraylist_profile_nickname = new ArrayList<>();
                            for (String temp : temporary_Comments_profile_nickname) {
                                Arraylist_profile_nickname.add(temp);
                            }

                            ArrayList<String> Arraylist_profile_image = new ArrayList<>();
                            for (String temp : temporary_Comments_profile_image) {
                                Arraylist_profile_image.add(temp);
                            }




                            for (int j=temporary_Comments_compare_id.length-1; j >= 0; j --) {
                                if (temporary_Comments_compare_title[j].equals(title) && temporary_Comments_compare_content[j].equals(content) && temporary_Comments_compare_date[j].equals(date)) {

                                    Arraylist_compare_id.remove(j); // ArrayList 에서 해당 포지션 제거
                                    Arraylist_compare_title.remove(j);
                                    Arraylist_compare_content.remove(j);
                                    Arraylist_compare_date.remove(j);
                                    Arraylist_Comments_comment.remove(j);
                                    Arraylist_profile_nickname.remove(j);
                                    Arraylist_profile_image.remove(j);

                                }
                            }




                            String temporary_compare_id="",temporary_compare_title="",temporary_compare_content="",temporary_compare_date="",temporary_comment="",temporary_profile_nickname="",temporary_profile_image="";


                            if (Arraylist_compare_id.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_compare_id.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_compare_id = temporary_compare_id + Arraylist_compare_id.get(j) + "@";
                                }
                            }

                            if (Arraylist_compare_title.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_compare_title.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_compare_title = temporary_compare_title + Arraylist_compare_title.get(j) + "@";
                                }
                            }

                            if (Arraylist_compare_content.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_compare_content.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_compare_content = temporary_compare_content + Arraylist_compare_content.get(j) + "@";
                                }
                            }

                            if (Arraylist_compare_date.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_compare_date.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_compare_date = temporary_compare_date + Arraylist_compare_date.get(j) + "@";
                                }
                            }

                            if (Arraylist_Comments_comment.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_Comments_comment.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_comment = temporary_comment + Arraylist_Comments_comment.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_nickname.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_profile_nickname.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_profile_nickname = temporary_profile_nickname + Arraylist_profile_nickname.get(j) + "@";
                                }
                            }

                            if (Arraylist_profile_image.size() != 0) { //Arraylist 에서 값을 지우고 다시 하나의 (string)문자열로 합치는 과정
                                for (int j = 0; j < Arraylist_profile_image.size(); j++) { //기록 되어 있는 아이디 나열하기
                                    temporary_profile_image = temporary_profile_image + Arraylist_profile_image.get(j) + "@";
                                }
                            }


                            editorshared.putString("Comments_compare_id", temporary_compare_id);
                            editorshared.putString("Comments_compare_title", temporary_compare_title);
                            editorshared.putString("Comments_compare_content", temporary_compare_content);
                            editorshared.putString("Comments_compare_date", temporary_compare_date);
                            editorshared.putString("Comments_comment", temporary_comment);

                            editorshared.putString("Comments_profile_nickname", temporary_profile_nickname);
                            editorshared.putString("Comments_profile_image", temporary_profile_image);
                            editorshared.apply(); //동기,세이브를 완료 해라
















                        }




                        Intent intent = new Intent(TodayDiaryCompleteActivity.this,MainpageActivity.class);
                        startActivity(intent);

                    }
                });

                diary_share.setNeutralButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                diary_share.show(); //다이얼로그 보여주기기





            }
        });

//        shared_button.setOnClickListener(new View.OnClickListener() { //공개하기 버튼 눌렀을 때
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.putExtra(Intent.EXTRA_TEXT, todaydairy_date.getText().toString() + "\n" + "오늘의 다이어트 : " + todaydairy_content_title.getText().toString() + "\n" + "내용 : " + todaydairy_content.getText().toString());
//                intent.putExtra(Intent.EXTRA_TITLE, "제목");
//                intent.setType("text/plain");
//                startActivity(Intent.createChooser(intent, "앱을 선택해 주세요"));
//            }
//        });


//        diary_choice_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //일기 선택 스피너 버튼 눌렀을 때 기능 구현
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                diary_choice_text2.setText(adapterView.getItemAtPosition(i).toString()); //나의 일기 인지, 공개 일기 인지
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });



    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.diary_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_share: // 일기 공개를 눌렀을 경우
//
//                break;
//            case R.id.menu_setup // 수정을 눌렀을 경우
//                    :
//                break;
//            case R.id.menu_delete: // 삭제를 눌렀을 경우
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
        finish();
        startActivity(intent);


//        if (diary_choice_text2.getText().equals("나의 일기")) { //나의 일기 선택 했을 때 데이터를 전달\
//
//            Intent intent = new Intent(TodayDiaryCompleteActivity.this, BundleDiaryActivity.class);
//            //Bundle_diary_adapter 에서 넘어 왔을 때에는 밑에 코드가 작동 안하게 한다
//            if (Bundle_diary_adapter.check_diary != 1) { //TodayDiaryCompleteActivity 에서 자료가 왔을 때에만 데이터를 BundleDiaryActivity로 보내서 추가 되도록 한다.
//
//                Log.e("확인", "위치 확인");
//
//                if (!content_title.equals("")) {  //뒤로가기 눌렀을 때 아무 값이 없어서 NullPointerException이 뜬다!!! 아무값 없으면 뭐라도 받을 수 있도록 하자!!!!!!
//                    intent.putExtra("content_title", content_title);
//                }
//                if (!content.equals("")) {
//                    intent.putExtra("content", content);
//                }
//                if (!dairy_date.equals("")) {
//                    intent.putExtra("dairy_date", dairy_date);
//                }
//                if (!day.equals("")) {
//                    intent.putExtra("day", day);
//                }
//                if (!month.equals("")) {
//                    intent.putExtra("month", month);
//                }
//                if (!year.equals("")) {
//                    intent.putExtra("year", year);
//                }
//
//                checkcount = 1; //BundleDiaryActivity 로 보내서 리스트에 추가 되도록 만들기
//            }
//            Bundle_diary_adapter.check_diary = 0;
//            finish();
//            startActivity(intent);
//
//
//        } else if (diary_choice_text2.getText().equals("공개 일기")) {  //공개 일기 선택 했을 때 데이터를 전달
//
//
//            Intent intent = new Intent(TodayDiaryCompleteActivity.this, MainpageActivity.class);
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
//            finish();
//            startActivity(intent);
//
//        }


    }
}