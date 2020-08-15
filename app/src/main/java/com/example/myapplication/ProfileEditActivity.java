package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.data.Result;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton profile_backward, setup_my_profileimage;
    private Button profile_save_button;
    private EditText setup_profile_nickname, setup_profile_heightnumber, setup_profile_weightnumber, setup_target_weightnumber;
    private final int get_gallery_image = 100;
    private RadioGroup profile_RadioGroup_gender;
    private RadioButton profile_RadioButton_female,profile_RadioButton_male;
    private String choicegender;
    private Bitmap profile_bitmap;
    public static int profile_checkcount = 0;
    public static String my_profile_nickname;
    public static String my_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        profile_backward = findViewById(R.id.profile_backward);
        profile_save_button = findViewById(R.id.profile_save_button);
        setup_profile_nickname = findViewById(R.id.setup_profile_nickname);
        setup_profile_heightnumber = findViewById(R.id.setup_profile_heightnumber);
        setup_profile_weightnumber = findViewById(R.id.setup_profile_weightnumber);
        setup_target_weightnumber = findViewById(R.id.setup_target_weightnumber);
        setup_my_profileimage = findViewById(R.id.setup_my_profileimage);
        profile_RadioGroup_gender = findViewById(R.id.profile_RadioGroup_gender);
        profile_RadioButton_female = findViewById(R.id.profile_RadioButton_female);
        profile_RadioButton_male = findViewById(R.id.profile_RadioButton_male);








        // 나의 아이디에 따라 저장되는 값이 달라야 한다!!!!!!!!!!!!!!!!!!!!!!!!!!!!







        SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
        String receive_profile_nickname = sharedPreferences.getString("nickname", ""); //받아온 데이터 String 변수 안에 넣기
        String receive_profile_choicegender = sharedPreferences.getString("choicegender", "");
        String receive_profile_heightnumber = sharedPreferences.getString("heightnumber", "");
        String receive_profile_profile_weightnumber = sharedPreferences.getString("profile_weightnumber", "");
        String receive_profile_target_weightnumber = sharedPreferences.getString("target_weightnumber", "");

        String receive_profile_profile_image = sharedPreferences.getString("profile_image", ""); //받아온 이미지 데이터 String 변수 안에 넣기
        byte[] encodeByte = Base64.decode(receive_profile_profile_image, Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
        profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기

//        Log.e("onCreate",receive_profile_nickname);


        setup_profile_nickname.setText(receive_profile_nickname);
        setup_profile_heightnumber.setText(receive_profile_heightnumber);
        setup_profile_weightnumber.setText(receive_profile_profile_weightnumber);
        setup_target_weightnumber.setText(receive_profile_target_weightnumber);
        setup_my_profileimage.setImageBitmap(profile_bitmap); //바뀐 비트맵 데이터를 뷰에 넣기

        //라디오 버튼 체크 해야해!!!!!!!!!!!!
        //저장이 안돼!!!!!!!!!!!!!!!!!!


    }


    @Override
    protected void onStart() {
        super.onStart();

//        Log.e("onStart","확인");
        profile_RadioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) { //성별 선택하기
                if ( i == R.id.profile_RadioButton_female){
                    //Toast.makeText(ProfileEditActivity.this,"여자 선택",Toast.LENGTH_LONG).show();
                    choicegender = profile_RadioButton_female.getText().toString();
                } else if ( i == R.id.profile_RadioButton_male){
                    //Toast.makeText(ProfileEditActivity.this,"남자 선택",Toast.LENGTH_LONG).show();
                    choicegender = profile_RadioButton_male.getText().toString();
                }
            }
        });


        profile_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //뒤로가기
                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                profile_checkcount = 0; //profile_checkcount가 0이면 프로필 수정이 없던 ,것 1이면 수정 한 것
                startActivity(intent);
            }
        });


        profile_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //프로필 저장하기

                // if -> 회원가입 페이지에서 왔으면 static으로 조건을 받자





                my_profile_nickname = setup_profile_nickname.getText().toString();//닉네임을 다른 클래스에서 사용하기 위해서 받아둠






                String nickname = setup_profile_nickname.getText().toString();
                String target_weightnumber = setup_target_weightnumber.getText().toString();
                String heightnumber = setup_profile_heightnumber.getText().toString();
                String profile_weightnumber = setup_profile_weightnumber.getText().toString();

                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class); //프로필 ProfileActivity에 보내기
                intent.putExtra("nickname", nickname); //닉네임 보내기
                intent.putExtra("choicegender",choicegender);
                intent.putExtra("target_weightnumber", target_weightnumber);
                intent.putExtra("heightnumber", heightnumber);
                intent.putExtra("profile_weightnumber", profile_weightnumber);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (profile_bitmap != null) {
                    profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 값이 있다면 이미지 비트맵으로 바꾸기
                } else {
                    profile_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); // 프로필 값이 없다면 이미지에 기본 이미지 설정하기
                    profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 이미지 비트맵으로 바꾸기
                }
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image",byteArray);

                startActivity(intent);
                profile_checkcount = 1; //profile_checkcount가 0이면 프로필 수정이 없던 ,것 1이면 수정 한 것



            }
        });

        ImageButton setup_my_profileimage = (ImageButton) findViewById(R.id.setup_my_profileimage);
        setup_my_profileimage.setOnClickListener(this); //프로필 이미지 선택 버튼 누르기

//        setup_my_profileimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,get_gallery_image);
//            }
//        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setup_my_profileimage:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //이미지 불러오기
                intent.setType("image/*");
                startActivityForResult(intent, get_gallery_image); //결과값 onActivityResult에 넘기기
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == get_gallery_image && resultCode == RESULT_OK && data != null) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData()); //선택한 이미지 데이터 가져오기
                profile_bitmap = BitmapFactory.decodeStream(in); //이미지 데이터를 비트맵으로 바꾸기
                setup_my_profileimage.setImageBitmap(profile_bitmap); //바뀐 비트맵 데이터를 뷰에 넣기

            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
            //Uri uri = data.getData();
            //ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), pictureUri);
        }
    }




}