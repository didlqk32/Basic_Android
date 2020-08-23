package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
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
import java.util.ArrayList;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton profile_backward, setup_my_profileimage;
    private Button profile_save_button;
    private EditText setup_profile_nickname, setup_profile_heightnumber, setup_profile_weightnumber, setup_target_weightnumber;
    private final int get_gallery_image = 100;
    private RadioGroup profile_RadioGroup_gender;
    private RadioButton profile_RadioButton_female,profile_RadioButton_male;
    private String choicegender = "";
    private Bitmap profile_bitmap;
    public static int profile_checkcount = 0;
    public static String my_profile_nickname;
    public static String my_profile_image;

    private boolean profile_comparison = true;

    private String temporary_nickname = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_choicegender = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_heightnumber = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_weightnumber = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_target_weightnumber = ""; // shared 값을 임시적으로 담기 위한 변수
    private String temporary_image = ""; // shared 값을 임시적으로 담기 위한 변수

    private String temporary_nickname_change = ""; // 변한 닉네임을 shared 에 임시적으로 담기 위한 변수

    private boolean image_setup = false; // 프로필 수정 했을 때 이미지를 수정 했는지 여부




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

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


//        SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
//        String receive_profile_nickname = sharedPreferences.getString("nickname", ""); //받아온 데이터 String 변수 안에 넣기
//        String receive_profile_choicegender = sharedPreferences.getString("choicegender", "");
//        String receive_profile_heightnumber = sharedPreferences.getString("heightnumber", "");
//        String receive_profile_profile_weightnumber = sharedPreferences.getString("profile_weightnumber", "");
//        String receive_profile_target_weightnumber = sharedPreferences.getString("target_weightnumber", "");
//
//        String receive_profile_profile_image = sharedPreferences.getString("profile_image", ""); //받아온 이미지 데이터 String 변수 안에 넣기
//        byte[] encodeByte = Base64.decode(receive_profile_profile_image, Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
//        profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기

//        Log.e("onCreate",receive_profile_nickname);


//        setup_profile_nickname.setText(receive_profile_nickname);
//        setup_profile_heightnumber.setText(receive_profile_heightnumber);
//        setup_profile_weightnumber.setText(receive_profile_profile_weightnumber);
//        setup_target_weightnumber.setText(receive_profile_target_weightnumber);
//        setup_my_profileimage.setImageBitmap(profile_bitmap); //바뀐 비트맵 데이터를 뷰에 넣기




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

        //작성한 프로필이 있다면 보여주기
        SharedPreferences shared = getSharedPreferences("profile_edit_file", MODE_PRIVATE);

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
                    setup_profile_nickname.setText("");
                } else {
                    setup_profile_nickname.setText(temporary_profile_nickname[i]);
                }

                if (temporary_profile_choicegender[i].equals("null")){
                } else {
                    if (temporary_profile_choicegender[i].equals("여자")) {
                        profile_RadioButton_female.setChecked(true);
                    } else if ((temporary_profile_choicegender[i].equals("남자"))){
                        profile_RadioButton_male.setChecked(true);
                    }
                }

                if (temporary_profile_heightnumber[i].equals("null")){
                    setup_profile_heightnumber.setText("");
                } else {
                    setup_profile_heightnumber.setText(temporary_profile_heightnumber[i]);
                }

                if (temporary_profile_weightnumber[i].equals("null")){
                    setup_profile_weightnumber.setText("");
                } else {
                    setup_profile_weightnumber.setText(temporary_profile_weightnumber[i]);
                }

                if (temporary_profile_target_weightnumber[i].equals("null")){
                    setup_target_weightnumber.setText("");
                } else {
                    setup_target_weightnumber.setText(temporary_profile_target_weightnumber[i]);
                }






                if (!temporary_profile_image[i].equals("null")){
                    byte[] encodeByte = Base64.decode(temporary_profile_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                    Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
                    setup_my_profileimage.setImageBitmap(bitmapimage); //프로필 이미지 저장
                }








                break; //하나라도 내 아이디로 작성한 프로필이 있으면 반복문 종료
            }
        }


//        profile_backward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { //뒤로가기
//                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
//                profile_checkcount = 0; //profile_checkcount가 0이면 프로필 수정이 없던 ,것 1이면 수정 한 것
//                startActivity(intent);
//            }
//        });


        profile_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //프로필 저장하기

                my_profile_nickname = setup_profile_nickname.getText().toString();//닉네임을 다른 클래스에서 사용하기 위해서 받아둠

                SharedPreferences shared = getSharedPreferences("profile_edit_file", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

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



                for (int i=0; i < temporary_profile_id.length; i++){ //프로필을 저장한 id갯수 만큼 반복한다,  프로필 수정
                    if (temporary_profile_id[i].equals(logInActivity.my_id)) { // 현재 나의 아이디와 저장된 프로필 데이터와 비교 했을 때 일치 하는 값이 있다면
                        // 여기서 프로필 수정이 일어 난다
                        profile_comparison = true; //profile_comparison true면 작성한 프로필이 있다는 것
                        // 수정은 이 안에서 해야지 같은 i값 으로 (같은 인덱스 값) 수정할 수 있다
                        // 닉네임이 바뀌면 일기에 있는 닉네임도 바꿔야 한다(비교해서 모든 닉네임 다 바꾸기)


                        ArrayList<String> Arraylist_profile_nickname = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                        for (String temp : temporary_profile_nickname) {
                            Arraylist_profile_nickname.add(temp);
                        }
                        if (setup_profile_nickname.getText().toString().equals("")) {
                            Arraylist_profile_nickname.set(i, "null"); //닉네임을 새롭게 바꾼다
                        } else {
                            Arraylist_profile_nickname.set(i, setup_profile_nickname.getText().toString()); //닉네임을 새롭게 바꾼다
                        }



                        // 닉네임 수정이 일어나면 다 바껴야 한다
                        // 닉네임 설정했다가 없애는 경우도 생각해야 한다
                        SharedPreferences shared_diary = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                        SharedPreferences.Editor editor_diary = shared_diary.edit();

                        String receive_diary_id = shared_diary.getString("diary_id", ""); // 작성한 일기들 데이터 받아서 문자열로 받기
                        String receive_diary_profile_nickname = shared_diary.getString("diary_profile_nickname", "");
                        String receive_diary_profile_image = shared.getString("diary_profile_image", "");


                        String[] temporary_diary_id = receive_diary_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
                        String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@"); // 문자열 데어터들 각각의 배열에 넣기
                        String[] temporary_diary_profile_image = receive_diary_profile_image.split("@");





                        ArrayList<String> Arraylist_diary_profile_nickname = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                        for (String temp : temporary_diary_profile_nickname) {
                            Arraylist_diary_profile_nickname.add(temp);
                        }

                        ArrayList<String> Arraylist_diary_profile_image = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                        for (String temp : temporary_diary_profile_image) {
                            Arraylist_diary_profile_image.add(temp);
                        }



                        for (int j = 0; j < temporary_diary_id.length; j++) { // 모든 일기 다 반복문으로 검사한다
                            if (temporary_diary_id[j].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기




                                Arraylist_diary_profile_nickname.set(j, Arraylist_profile_nickname.get(i)); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
//                                Arraylist_diary_profile_image.set(j,);




                            }
                        }
                        for (int j = 0; j < Arraylist_diary_profile_nickname.size(); j++) { //기록 되어 있는 아이디 나열하기
                            temporary_nickname_change = temporary_nickname_change + Arraylist_diary_profile_nickname.get(j) + "@";
                        }



                        //!!!!!!!!!!!!!!!!!
                        // 프로필 이미지 바뀐것에 대해서도!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!수정 적용 하자!!!!!!!!!!!!!!!!!!!!!!!!!!





                        editor_diary.putString("diary_profile_nickname", temporary_nickname_change);
                        editor_diary.apply(); //동기,세이브를 완료 해라





















                        ArrayList<String> Arraylist_profile_choicegender = new ArrayList<>();
                        for (String temp : temporary_profile_choicegender) {
                            Arraylist_profile_choicegender.add(temp);
                        }
                        if (choicegender.equals("")) {
                            Arraylist_profile_choicegender.set(i, "null"); //닉네임을 새롭게 바꾼다
                        } else {
                            Arraylist_profile_choicegender.set(i, choicegender);
                        }


                        ArrayList<String> Arraylist_profile_heightnumber = new ArrayList<>();
                        for (String temp : temporary_profile_heightnumber) {
                            Arraylist_profile_heightnumber.add(temp);
                        }
                        if (setup_profile_heightnumber.getText().toString().equals("")) {
                            Arraylist_profile_heightnumber.set(i, "null"); //닉네임을 새롭게 바꾼다
                        } else {
                            Arraylist_profile_heightnumber.set(i, setup_profile_heightnumber.getText().toString());
                        }


                        ArrayList<String> Arraylist_profile_weightnumber = new ArrayList<>();
                        for (String temp : temporary_profile_weightnumber) {
                            Arraylist_profile_weightnumber.add(temp);
                        }
                        if (setup_profile_weightnumber.getText().toString().equals("")) {
                            Arraylist_profile_weightnumber.set(i, "null"); //닉네임을 새롭게 바꾼다
                        } else {
                            Arraylist_profile_weightnumber.set(i, setup_profile_weightnumber.getText().toString());
                        }


                        ArrayList<String> Arraylist_profile_target_weightnumber = new ArrayList<>();
                        for (String temp : temporary_profile_target_weightnumber) {
                            Arraylist_profile_target_weightnumber.add(temp);
                        }
                        if (setup_target_weightnumber.getText().toString().equals("")) {
                            Arraylist_profile_target_weightnumber.set(i, "null"); //닉네임을 새롭게 바꾼다
                        } else {
                            Arraylist_profile_target_weightnumber.set(i, setup_target_weightnumber.getText().toString());
                        }


                        ArrayList<String> Arraylist_profile_image = new ArrayList<>(); // 배열에 저장된 string값의 이미지를 arraylist에 다시 담는 과정
                        for (String temp : temporary_profile_image) {
                            Arraylist_profile_image.add(temp);
                        }



                        // 이미지 수정 부분

                        if (temporary_profile_image[i].equals("null")) {
                            if (image_setup == false) { //이미지 수정을 안했다면
                                String diary_image = "null";
                                Arraylist_profile_image.set(i, diary_image); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
                            } else { //이미지 수정을 했다면
                                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //이미지를 바이트로 만들기 위해 스트림 객체 만들기
                                profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //비트맵 이미지 stream형식으로
                                byte[] bytes = stream.toByteArray(); //stream 이미지 바이트형식으로
                                String diary_image = Base64.encodeToString(bytes, Base64.DEFAULT); // 바이트 형식 string으로 바꾸기
                                Arraylist_profile_image.set(i, diary_image); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
                            }
                        } else if (profile_bitmap != null) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream(); //이미지를 바이트로 만들기 위해 스트림 객체 만들기
                            profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //비트맵 이미지 stream형식으로
                            byte[] bytes = stream.toByteArray(); //stream 이미지 바이트형식으로
                            String diary_image = Base64.encodeToString(bytes, Base64.DEFAULT); // 바이트 형식 string으로 바꾸기
                            Arraylist_profile_image.set(i, diary_image); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈

                        } else if (!temporary_profile_image[i].equals("null")){
                            break; //이미 이미지가 있는 상태에서 프로필 편집에서 이미지 수정 안하고 save 눌렀을 경우 상태 변화 없다
                        } else {
                            String diary_image = "null";
                            Arraylist_profile_image.set(i,diary_image); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
                        }


                        for (int j = 0; j < Arraylist_profile_nickname.size(); j++) { //기록 되어 있는 아이디 나열하기
                            temporary_nickname = temporary_nickname + Arraylist_profile_nickname.get(j) + "@";
                        }

                        for (int j = 0; j < Arraylist_profile_choicegender.size(); j++) {
                            temporary_choicegender = temporary_choicegender + Arraylist_profile_choicegender.get(j) + "@";
                        }

                        for (int j = 0; j < Arraylist_profile_heightnumber.size(); j++) {
                            temporary_heightnumber = temporary_heightnumber + Arraylist_profile_heightnumber.get(j) + "@";
                        }

                        for (int j = 0; j < Arraylist_profile_weightnumber.size(); j++) {
                            temporary_weightnumber = temporary_weightnumber + Arraylist_profile_weightnumber.get(j) + "@";
                        }

                        for (int j = 0; j < Arraylist_profile_target_weightnumber.size(); j++) {
                            temporary_target_weightnumber = temporary_target_weightnumber + Arraylist_profile_target_weightnumber.get(j) + "@";
                        }

                        for (int j = 0; j < Arraylist_profile_image.size(); j++) {
                            temporary_image = temporary_image + Arraylist_profile_image.get(j) + "@";
                        }

                        editor.putString("profile_nickname", temporary_nickname); //profile 안에 있는 데이터 파일에 저장하기
                        editor.putString("profile_choicegender", temporary_choicegender);
                        editor.putString("profile_heightnumber", temporary_heightnumber);
                        editor.putString("profile_weightnumber", temporary_weightnumber);
                        editor.putString("profile_target_weightnumber", temporary_target_weightnumber);
                        editor.putString("profile_image", temporary_image);

                        editor.apply(); //동기,세이브를 완료 해라

                        break; //하나라도 내 아이디로 작성한 프로필이 있으면 반복문 종료
                    } else {
                        //이 코드 안에서 프로필 추가 하면 for문 이기 때문에 계속 추가됨
                        profile_comparison = false; //profile_comparison false면 작성한 프로필이 없다는 것
                    }
                }

                if (profile_comparison == false){ //프로필 생성

                    // 프로필 생성 했을 때에도 닉네임이나 프로필 이미지가 바뀌면 공유 일기에 적용 시켜야 한다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


                    receive_profile_id = receive_profile_id + logInActivity.my_id + "@"; //String로 받은 id 데이터에 현재 id 값을 넣는다


                    if (setup_profile_nickname.getText().toString().equals("")) { //값이 아무것도 저장되지 않으면 ""를 저장한다
                        receive_profile_nickname = receive_profile_nickname + "null" + "@";
                    } else {
                        receive_profile_nickname = receive_profile_nickname + setup_profile_nickname.getText().toString() + "@";
                    }








                    // 닉네임 수정이 일어나면 다 바껴야 한다
                    // 닉네임 설정했다가 없애는 경우도 생각해야 한다
                    SharedPreferences shared_diary = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기
                    SharedPreferences.Editor editor_diary = shared_diary.edit();

                    String receive_diary_id = shared_diary.getString("diary_id",""); // 작성한 일기들 데이터 받아서 문자열로 받기
                    String receive_diary_profile_nickname = shared_diary.getString("diary_profile_nickname","");

                    String[] temporary_diary_id = receive_diary_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
                    String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@"); // 문자열 데어터들 각각의 배열에 넣기

                    ArrayList<String> Arraylist_diary_profile_nickname = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                    for (String temp : temporary_diary_profile_nickname) {
                        Arraylist_diary_profile_nickname.add(temp);
                    }

                    for (int j=0; j < temporary_diary_id.length; j++) { // 모든 일기 다 반복문으로 검사한다
                        if (temporary_diary_id[j].equals(logInActivity.my_id)){ //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기

                            if (setup_profile_nickname.getText().toString().equals("")) { //값이 아무것도 저장되지 않으면 ""를 저장한다
                                Arraylist_diary_profile_nickname.set(j, "null"); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
                            } else {
                                Arraylist_diary_profile_nickname.set(j, setup_profile_nickname.getText().toString()); // ArrayList 에서 해당 포지션의 일기를 작성한 일기로 바꿈
                            }
                        }
                    }
                    for (int j = 0; j < Arraylist_diary_profile_nickname.size(); j++) { //기록 되어 있는 아이디 나열하기
                        temporary_nickname_change = temporary_nickname_change + Arraylist_diary_profile_nickname.get(j) + "@";
                    }

                    editor_diary.putString("diary_profile_nickname", temporary_nickname_change);
                    editor_diary.apply(); //동기,세이브를 완료 해라











                    if (choicegender.equals("")) {
                        receive_profile_choicegender = receive_profile_choicegender + "null" + "@";
                    } else {
                        receive_profile_choicegender = receive_profile_choicegender  + choicegender + "@";
                    }

                    if (setup_profile_heightnumber.getText().toString().equals("")) {
                        receive_profile_heightnumber = receive_profile_heightnumber + "null" + "@";
                    } else {
                        receive_profile_heightnumber = receive_profile_heightnumber + setup_profile_heightnumber.getText().toString() + "@";
                    }

                    if (setup_profile_weightnumber.getText().toString().equals("")) {
                        receive_profile_weightnumber = receive_profile_weightnumber + "null" + "@";
                    } else {
                        receive_profile_weightnumber = receive_profile_weightnumber + setup_profile_weightnumber.getText().toString() + "@";
                    }

                    if (setup_target_weightnumber.getText().toString().equals("")) {
                        receive_profile_target_weightnumber = receive_profile_target_weightnumber + "null" + "@";
                    } else {
                        receive_profile_target_weightnumber = receive_profile_target_weightnumber + setup_target_weightnumber.getText().toString() + "@";
                    }





                    if (profile_bitmap != null) { // 포
                        ByteArrayOutputStream stream = new ByteArrayOutputStream(); //이미지를 바이트로 만들기 위해 스트림 객체 만들기
                        profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //비트맵 이미지 stream형식으로
                        byte[] bytes = stream.toByteArray(); //stream 이미지 바이트형식으로
                        String diary_image = Base64.encodeToString(bytes, Base64.DEFAULT); // 바이트 형식 string으로 바꾸기
                        receive_profile_image = receive_profile_image + diary_image + "@";
                    } else {
                        String diary_image = "null";
                        receive_profile_image = receive_profile_image + diary_image + "@";
                    }

                    profile_comparison = true; // 현재 아이디로 작성했던 프로필이 있는지에 대한 변수 초기화

                    editor.putString("profile_id", receive_profile_id); //profile 안에 있는 데이터 파일에 저장하기
                    editor.putString("profile_nickname", receive_profile_nickname); //profile 안에 있는 데이터 파일에 저장하기
                    editor.putString("profile_choicegender", receive_profile_choicegender);
                    editor.putString("profile_heightnumber", receive_profile_heightnumber);
                    editor.putString("profile_weightnumber", receive_profile_weightnumber);
                    editor.putString("profile_target_weightnumber", receive_profile_target_weightnumber);
                    editor.putString("profile_image", receive_profile_image);

                    editor.apply(); //동기,세이브를 완료 해라

                }

                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class); //프로필 ProfileActivity에 보내기
                startActivity(intent);


















                //밑에 코드 지울 예정



                // if -> 회원가입 페이지에서 왔으면 static으로 조건을 받자


//                my_profile_nickname = setup_profile_nickname.getText().toString();//닉네임을 다른 클래스에서 사용하기 위해서 받아둠
//
//                String nickname = setup_profile_nickname.getText().toString();
//                String target_weightnumber = setup_target_weightnumber.getText().toString();
//                String heightnumber = setup_profile_heightnumber.getText().toString();
//                String profile_weightnumber = setup_profile_weightnumber.getText().toString();
//
//                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class); //프로필 ProfileActivity에 보내기
//                intent.putExtra("nickname", nickname); //닉네임 보내기
//                intent.putExtra("choicegender",choicegender);
//                intent.putExtra("target_weightnumber", target_weightnumber);
//                intent.putExtra("heightnumber", heightnumber);
//                intent.putExtra("profile_weightnumber", profile_weightnumber);
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                if (profile_bitmap != null) {
//                    profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 값이 있다면 이미지 비트맵으로 바꾸기
//                } else {
//                    profile_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); // 프로필 값이 없다면 이미지에 기본 이미지 설정하기
//                    profile_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // 프로필 이미지 비트맵으로 바꾸기
//                }
//                byte[] byteArray = stream.toByteArray();
//                intent.putExtra("image",byteArray);
//
//                startActivity(intent);
//                profile_checkcount = 1; //profile_checkcount가 0이면 프로필 수정이 없던 ,것 1이면 수정 한 것



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
//                profile_bitmap = BitmapFactory.decodeStream(in); //이미지 데이터를 비트맵으로 바꾸기

                //갤러리에서 uri로 가져왔을 때 리사이즈 해준다
                profile_bitmap = resize(ProfileEditActivity.this,data.getData(),200);

                setup_my_profileimage.setImageBitmap(profile_bitmap); //바뀐 비트맵 데이터를 뷰에 넣기

                image_setup = true; // 이미지 수정을 했을 경우에는 true로 바뀜 수정 안하면 false

            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }
            //Uri uri = data.getData();
            //ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), pictureUri);
        }
    }



    //갤러리에서 uri로 가져왔을 때 리사이즈 해준다
    private Bitmap resize(Context context, Uri uri, int resize){
        Bitmap resizeBitmap=null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); // 1번

            int width = options.outWidth;
            int height = options.outHeight;
            int samplesize = 1;

            while (true) {//2번
                if (width / 2 < resize || height / 2 < resize)
                    break;
                width /= 2;
                height /= 2;
                samplesize *= 2;
            }

            options.inSampleSize = samplesize;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options); //3번
            resizeBitmap=bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return resizeBitmap;
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileEditActivity.this,ProfileActivity.class);
        profile_checkcount = 0; //profile_checkcount가 0이면 프로필 수정이 없던 ,것 1이면 수정 한 것
        startActivity(intent);
    }
}