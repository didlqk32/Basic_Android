package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class logInActivity extends AppCompatActivity {

    private Button btn_loginpage_login;
    private TextView signup_text3;
    private EditText id_text,password_text;
    public static String my_id,my_password;
    private int wrong_password = 0, wrong_id = 0;
    private Boolean Login_ok = false;
    //LinearLayout btn_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        btn_loginpage_login = findViewById(R.id.btn_loginpage_login); //로그인 버튼
        signup_text3 = findViewById(R.id.signup_text3); //회원가입 텍스트
        id_text = findViewById(R.id.id_text); //회원가입 텍스트
        password_text = findViewById(R.id.password_text); //회원가입 텍스트
        //btn_sign_up = findViewById(R.id.btn_sign_up);


        btn_loginpage_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //로그인 버튼 누르기


                if (id_text.getText().toString().equals("")){
                    Toast.makeText(logInActivity.this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else if (password_text.getText().toString().equals("")) {
                    Toast.makeText(logInActivity.this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
//                Log.e("텍스트 확인",id_text.getText().toString());

                    SharedPreferences sharedPreferences = getSharedPreferences("signup_file", MODE_PRIVATE); //"signup_file"파일의 데이터 받아오기
                    String receive_signup_id = sharedPreferences.getString("save_signup_id", ""); //받아온 데이터 String 변수 안에 넣기
                    String receive_signup_password = sharedPreferences.getString("save_signup_password", ""); //받아온 데이터 String 변수 안에 넣기

                    String[] temporary_idList = receive_signup_id.split("/");
                    String[] temporary_passwordList = receive_signup_password.split("/");


                    for (int i = 0; i < temporary_idList.length; i++) { //기록 되어 있는 아이디 나열하기
                        if (id_text.getText().toString().equals(temporary_idList[i])) {
//                        Log.e("i 확인",String.valueOf(i));
                            if (password_text.getText().toString().equals(temporary_passwordList[i])) {
//                            Log.e("i 확인2", String.valueOf(i)); //같은 값인지 확인하기!
                                Toast.makeText(logInActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                                my_id = temporary_idList[i]; //현재 로그인 한 아이디가 변수로 들어간다 (내 일기인지 파악하기 위해서)
                                my_password = temporary_passwordList[i]; //현재 로그인 한 비밀번호가 변수로 들어간다
                                Intent intent = new Intent(logInActivity.this, MainpageHomeActivity.class);
                                startActivity(intent);





                                wrong_password = 0; // 패스워드 비교하는 변수 초기화
                                wrong_id = 0; // 아이디 비교하는 변수 초기화
                                Login_ok = true; //로그인이 되었다
                            } else {
                                wrong_password = 1; // 아이디는 있고 패스워드가 틀렸을 때


                            }
                        } else {
                            if (wrong_password != 1) { // 아이디 없을 때
                                wrong_id = 1; // 아이디 비교하는 변수 초기화
                            }

                        }
//                    Log.e("temporary_idList 확인",temporary_idList[i]);
                    }

                    if (wrong_password == 1) {
                        Toast.makeText(logInActivity.this, "입력이 잘못 되었습니다", Toast.LENGTH_SHORT).show();
                    } else if (wrong_id == 1 && Login_ok == false) {
                        Toast.makeText(logInActivity.this, "입력이 잘못 되었습니다", Toast.LENGTH_SHORT).show();
                    }

                    wrong_password = 0;
                    wrong_id = 0;
                    Login_ok = false;
                    id_text.setText("");
                    password_text.setText("");
                }

            }
        });





        signup_text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 회원가입 텍스트 누르기
                Intent intent = new Intent(logInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

//        btn_sign_up.setClickable(true);
//        btn_sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(logInActivity.this, SignUpActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }
}