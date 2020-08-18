package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    private ImageView my_profileimage;
    private ImageButton profile_setup, imageButton;
    private TextView my_profile_nickname, my_profile_heightnumber, my_profile_weightnumber, my_target_weightnumber, my_profile_gender;
    private Bitmap image;

    private String nickname;
    private String choicegender;
    private String heightnumber;
    private String profile_weightnumber;
    private String target_weightnumber;
    private String profile_image;

    private static int temporary_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        profile_setup = findViewById(R.id.profile_setup);
        my_profile_nickname = findViewById(R.id.my_profile_nickname);
        my_profile_heightnumber = findViewById(R.id.my_profile_heightnumber);
        my_profile_weightnumber = findViewById(R.id.my_profile_weightnumber);
        my_target_weightnumber = findViewById(R.id.my_target_weightnumber);
        imageButton = findViewById(R.id.imageButton);
        my_profile_gender = findViewById(R.id.my_profile_gender);
        my_profileimage = findViewById(R.id.my_profileimage);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.e("onStart","확인");


        //작성한 프로필이 있다면 보여주기
        SharedPreferences shared = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //프로필 데이터를 받아서 나열한다

        String receive_profile_id = shared.getString("profile_id", "");
        String receive_profile_nickname = shared.getString("profile_nickname", "");
        String receive_profile_choicegender = shared.getString("profile_choicegender", "");
        String receive_profile_heightnumber = shared.getString("profile_heightnumber", "");
        String receive_profile_weightnumber = shared.getString("profile_weightnumber", "");
        String receive_profile_target_weightnumber = shared.getString("profile_target_weightnumber", "");
        String receive_profile_image = shared.getString("profile_image", "");

        String[] temporary_profile_id = receive_profile_id.split("@"); // 프로필 저장한 아이디 배열에 데이터 저장
        String[] temporary_profile_nickname = receive_profile_nickname.split("@");
        String[] temporary_profile_choicegender = receive_profile_choicegender.split("@");
        String[] temporary_profile_heightnumber = receive_profile_heightnumber.split("@");
        String[] temporary_profile_weightnumber = receive_profile_weightnumber.split("@");
        String[] temporary_profile_target_weightnumber = receive_profile_target_weightnumber.split("@");
        String[] temporary_profile_image = receive_profile_image.split("@");

        for (int i=0; i < temporary_profile_id.length; i++) { //프로필을 저장한 id갯수 만큼 반복한다
            if (temporary_profile_id[i].equals(logInActivity.my_id)) { // 현재 나의 아이디와 저장된 프로필 데이터와 비교 했을 때 일치 하는 값이 있다면

                if (temporary_profile_nickname[i].equals("null")){
                    my_profile_nickname.setText("");
                } else {
                    my_profile_nickname.setText(temporary_profile_nickname[i]);
                }

                if (temporary_profile_choicegender[i].equals("null")){
                    my_profile_gender.setText("");
                } else {
                    my_profile_gender.setText(temporary_profile_choicegender[i]);
                }

                if (temporary_profile_heightnumber[i].equals("null")){
                    my_profile_heightnumber.setText("");
                } else {
                    my_profile_heightnumber.setText(temporary_profile_heightnumber[i]);
                }

                if (temporary_profile_weightnumber[i].equals("null")){
                    my_profile_weightnumber.setText("");
                } else {
                    my_profile_weightnumber.setText(temporary_profile_weightnumber[i]);
                }

                if (temporary_profile_target_weightnumber[i].equals("null")){
                    my_target_weightnumber.setText("");
                } else {
                    my_target_weightnumber.setText(temporary_profile_target_weightnumber[i]);
                }

                if (!temporary_profile_image[i].equals("null")){
                    byte[] encodeByte = Base64.decode(temporary_profile_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                    Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
                    my_profileimage.setImageBitmap(bitmapimage); //프로필 이미지 저장
                }

                break; //하나라도 내 아이디로 작성한 프로필이 있으면 반복문 종료
            }
        }



















//        SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
//        String receive_profile_nickname = sharedPreferences.getString("nickname", ""); //받아온 데이터 String 변수 안에 넣기
//        String receive_profile_choicegender = sharedPreferences.getString("choicegender", "");
//        String receive_profile_heightnumber = sharedPreferences.getString("heightnumber", "");
//        String receive_profile_profile_weightnumber = sharedPreferences.getString("profile_weightnumber", "");
//        String receive_profile_target_weightnumber = sharedPreferences.getString("target_weightnumber", "");
//
//        String receive_profile_profile_image = sharedPreferences.getString("profile_image", ""); //받아온 이미지 데이터 String 변수 안에 넣기
//
//        byte[] encodeByte = Base64.decode(receive_profile_profile_image, Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
//        Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
//
//        my_profile_nickname.setText(receive_profile_nickname);
//        my_profile_gender.setText(receive_profile_choicegender);
//        my_profile_heightnumber.setText(receive_profile_heightnumber);
//        my_profile_weightnumber.setText(receive_profile_profile_weightnumber);
//        my_target_weightnumber.setText(receive_profile_target_weightnumber);
//        my_profileimage.setImageBitmap(bitmapimage); //프로필 이미지 저장
//
//
//        if (ProfileEditActivity.profile_checkcount == 1) { //프로필 수정을 했다면 아래 코드 진행
//
//            Log.e("profile_checkcount", "확인");
//
//            Intent intent = getIntent();
//            nickname = intent.getStringExtra("nickname");
//            choicegender = intent.getStringExtra("choicegender");
//            heightnumber = intent.getStringExtra("heightnumber");
//            profile_weightnumber = intent.getStringExtra("profile_weightnumber");
//            target_weightnumber = intent.getStringExtra("target_weightnumber");
//
//            byte[] arr = getIntent().getByteArrayExtra("image");
//            if (arr != null) {
//                image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
//                my_profileimage.setImageBitmap(image);
//            }
//            if (nickname != null) {
//                my_profile_nickname.setText(nickname);
//            }
//            if (choicegender != null) {
//                my_profile_gender.setText(choicegender);
//            }
//            if (heightnumber != null) {
//                my_profile_heightnumber.setText(heightnumber);
//            }
//            if (profile_weightnumber != null) {
//                my_profile_weightnumber.setText(profile_weightnumber);
//            }
//            if (target_weightnumber != null) {
//                my_target_weightnumber.setText(target_weightnumber);
//            }
//
//        }








        profile_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //프로필 수정하기
                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
                 finish(); // 화면 종료하기
            }
        });

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { // 뒤로가기버튼 누르기
//                Intent intent = new Intent(ProfileActivity.this, MainpageHomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("onResume","확인");


//        if(ProfileEditActivity.profile_checkcount == 1) {
//            ProfileActivity.temporary_save = 1; // 화면 전환이 되면 프로필이 저장 되는 것을 기억하는 변수
//            ByteArrayOutputStream stream = new ByteArrayOutputStream(); //이미지를 바이트로 만들기 위해 스트림 객체 만들기
//
//            if (image != null) {
//                image.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 값이 있다면 이미지 비트맵으로 바꾸기
//            } else if (image == null) {
//                image = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); // 프로필 값이 없다면 이미지에 기본 이미지 설정하기
//                image.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 이미지 비트맵으로 바꾸기
//            }
//
//            byte[] bytes = stream.toByteArray(); //stream 이미지 바이트형식으로
//            profile_image = Base64.encodeToString(bytes, Base64.DEFAULT); // 바이트 형식 string으로 바꾸기
//
//            SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("nickname", nickname); //profile 안에 있는 데이터 파일에 저장하기
//            editor.putString("choicegender", choicegender);
//            editor.putString("heightnumber", heightnumber);
//            editor.putString("profile_weightnumber", profile_weightnumber);
//            editor.putString("target_weightnumber", target_weightnumber);
//
//            editor.putString("profile_image", profile_image);
//
////        editor.clear(); //sharedPreferences 저장되어 있는 파일 지우기
//            editor.apply(); //동기,세이브를 완료 해라
//        }
    }


    @Override
    public void onBackPressed() { //적용 할 지 안할지 고민
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, MainpageHomeActivity.class);
        startActivity(intent);
        finish();
    }
}