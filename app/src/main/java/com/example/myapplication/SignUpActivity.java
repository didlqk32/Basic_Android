package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private Button login, btn_sign_up, btn_sign_up_overlapcheck;
    private EditText btn_sign_up_text, btn_password, btn_passwordcheck;
    ArrayList<String> passwordArrayList, idArrayList;
    private String save_signup_id = ""; //sharedpreference 데이터 담는 변수
    private String save_signup_password = "";
    private String current_id = "";
    private String current_password = "";
    private int overlap = 0 ;//중복 여부 파악하기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        btn_sign_up = findViewById(R.id.btn_sign_up); //회원가입 버튼
        login = findViewById(R.id.login); //로그인 버튼
        btn_sign_up_overlapcheck = findViewById(R.id.btn_sign_up_overlapcheck);

        btn_sign_up_text = findViewById(R.id.btn_sign_up_text); // 이메일 기입란
        btn_password = findViewById(R.id.btn_password); // 패스워드 기입란
        btn_passwordcheck = findViewById(R.id.btn_passwordcheck); // 패스워드 확인 기입란

        passwordArrayList = new ArrayList<>();
        idArrayList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();


        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //회원가입 버튼 누르기

                current_id = btn_sign_up_text.getText().toString();
                current_password = btn_password.getText().toString();

//                Log.e("current_id",current_id);
//                Log.e("current_password",current_password);
//                Log.e("idArrayList.size",String.valueOf(idArrayList.size()));


                if (overlap == 2) { //중복 확인 하고, 중복된 아이디가 없는 경우

                    if (btn_sign_up_text.getText().toString().equals("")) {
                        Toast.makeText(SignUpActivity.this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    } else if (btn_password.getText().toString().equals("")) {
                        Toast.makeText(SignUpActivity.this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                    } else if (btn_password.getText().toString().equals(btn_passwordcheck.getText().toString())) {// 비밀번호와 비밀번호 확인이 같을 때 + 아이디가 중복이 아닐 때 조건 추가
//
//                    Log.e("리스트 크기",String.valueOf(idArrayList.size()));
                        SharedPreferences sharedPreferences = getSharedPreferences("signup_file", MODE_PRIVATE); //"signup_file"파일의 데이터 받아오기
                        String receive_signup_id = sharedPreferences.getString("save_signup_id", ""); //받아온 데이터 String 변수 안에 넣기
                        String receive_signup_password = sharedPreferences.getString("save_signup_password", ""); //받아온 데이터 String 변수 안에 넣기

                        String[] temporary_idList = receive_signup_id.split("/");
                        String[] temporary_passwordList = receive_signup_password.split("/");


//                        Log.e("리스트 크기", String.valueOf(temporary_idList.length));



                        if (temporary_idList.length != 0) {
                            for (int i = 0; i < temporary_idList.length; i++) { //기록 되어 있는 아이디 나열하기
                                save_signup_id = save_signup_id + temporary_idList[i] + "/";
                            }
                        }
                        save_signup_id = save_signup_id + current_id + "/";

                        if (temporary_passwordList.length != 0) { //기록 되어 있는 패스워드 나열하기
                            for (int i = 0; i < temporary_passwordList.length; i++) {
                                save_signup_password = save_signup_password + temporary_passwordList[i] + "/";
                            }
                        }
                        save_signup_password = save_signup_password + current_password + "/";



//                        Log.e("아이디 값", save_signup_id);
//                        Log.e("패스워드 값", save_signup_password);
//                        Log.e("리스트 크기", String.valueOf(temporary_idList.length));

//                    SharedPreferences sharedPreferences = getSharedPreferences("signup_file", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("save_signup_id", save_signup_id); //diaryArrayList.size 안에 있는 데이터 파일에 저장하기
                        editor.putString("save_signup_password", save_signup_password); //패스워드 저장하기

//                    editor.clear(); //sharedPreferences 저장되어 있는 파일 지우기
                        editor.apply(); //동기,세이브를 완료 해라

                        Intent intent = new Intent(SignUpActivity.this, logInActivity.class);
                        startActivity(intent);
                        Toast.makeText(SignUpActivity.this, "회원 가입 되었습니다", Toast.LENGTH_SHORT).show();
                        overlap = 0;//화면에서 나가면 값을 초기화 한다



                    } else { // 비밀번호가 비밀번호 확인하고 다를 때
                        Toast.makeText(SignUpActivity.this, "비밀 번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "중복 여부를 먼저 확인 하십시오", Toast.LENGTH_SHORT).show();
                }






            }
        });




        btn_sign_up_overlapcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //중복 확인 버튼 누르기
                overlap = 0; //중복 확인 계속 할 수 있도록 초기화 시킴

                SharedPreferences sharedPreferences = getSharedPreferences("signup_file",MODE_PRIVATE); //"signup_file"파일의 데이터 받아오기
                String receive_signup_id = sharedPreferences.getString("save_signup_id",""); //받아온 데이터 String 변수 안에 넣기

//                Log.e("아이디 중복 확인",receive_signup_id);
//                Log.e("overlap",String.valueOf(overlap));
                String[] Array_signup_id = receive_signup_id.split("/"); //receive_signup_id 내용물을 split "/"으로 쪼개고 String 배열에 넣음

                if (!btn_sign_up_text.getText().toString().equals("")) { // 아이디 텍스트가 비어있지 않을 때 비교한다
                   for (int i = 0; i < Array_signup_id.length; i++) {
                        if (Array_signup_id[i].equals(btn_sign_up_text.getText().toString())) {
                            overlap = 1;
                        }
                    }

//                    Log.e("overlap2",String.valueOf(overlap));

                    if (overlap == 1) {
                        Toast.makeText(SignUpActivity.this, "중복된 아이디가 있습니다", Toast.LENGTH_SHORT).show();
                        overlap = 0; //중복 확인 다시 할 수 있도록 초기화 시킴
                    } else if (overlap == 0) {
                        Toast.makeText(SignUpActivity.this, "사용 가능한 아이디 입니다", Toast.LENGTH_SHORT).show();
                        overlap = 2; //회원 가입 할 수 있도록 값 변경
                    }


                } else {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                }




            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //로그인 버튼 누르기
                Intent intent = new Intent(SignUpActivity.this, logInActivity.class);
                startActivity(intent); //액티비티 이동
                overlap = 0;//화면에서 나가면 값을 초기화 한다

            }
        });





    }
}