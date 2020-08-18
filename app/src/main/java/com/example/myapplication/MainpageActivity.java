package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainpageActivity extends AppCompatActivity {

    private ArrayList<Mainpage_data> mainpage_dataArrayList;
    //    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Mainpage_adapter mainpage_adapter;

    private DrawerLayout drawerLayout; //메뉴바 레이아웃
    private View drawerView; //메뉴바 뷰
    private ImageButton menubar_open;
    private Button logout; //로그아웃 버튼
    LinearLayout menubar_my_profile, menubar_exercise_report; //네비게이션 레이아웃 안에 있는 요소들

    private Button home, diary, home_training;
    androidx.constraintlayout.widget.ConstraintLayout otherpeople_dairy_sumnail;

    private String save_mainpage_diary_title = ""; //mainpage_diary_title를 save 하기 위한 변수 생성
    private String save_mainpage_diary_content = ""; //mainpage_diary_content를 save 하기 위한 변수 생성
    private String save_mainpage_diary_heartcount = ""; //mainpage_diary_heartcount를 save 하기 위한 변수 생성
    private String save_mainpage_diary_bitmapimage = ""; //save_mainpage_diary_bitmapimage를 save 하기 위한 변수 생성
    private Bitmap temporary_bitmap;
    private String bitmaptostring;
    private ArrayList<Bitmap> Array_bitmap = null;

    private ImageView menubar_profile_image; //메뉴바 프로필 이미지
    private TextView menubar_profile_nickname; //메뉴바 프로필 닉네임
    private Bitmap memu_profile_bitmap; //메뉴바 프로필 이미지 임시 저장




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        recyclerView = findViewById(R.id.main_recyclerview); //recyclerview 연결
//        linearLayoutManager = new LinearLayoutManager(this);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        mainpage_dataArrayList = new ArrayList<>(); //데이터를 담을 리스트 객체 생성
        mainpage_adapter = new Mainpage_adapter(mainpage_dataArrayList, this); //adapter 객체 생성
        recyclerView.setAdapter(mainpage_adapter); // recyclerview와 adapter 연결


        drawerLayout = findViewById(R.id.drawer_layout2); //네비게이션 메뉴바 레이아웃 연결
        drawerView = findViewById(R.id.drawer);
        menubar_open = findViewById(R.id.menubar_open);
        menubar_exercise_report = findViewById(R.id.menubar_exercise_report); //네비게이션 메뉴바의 운동기록 버튼
        menubar_my_profile = findViewById(R.id.menubar_my_profile); //나의 프로필 연결
        logout = findViewById(R.id.logout); //로그아웃 버튼

        home = findViewById(R.id.home);
        diary = findViewById(R.id.diary);
        home_training = findViewById(R.id.home_training);
        otherpeople_dairy_sumnail = findViewById(R.id.otherpeople_dairy_sumnail);


        menubar_profile_nickname = findViewById(R.id.menubar_profile_nickname);//메뉴바에서 나의 닉네임 연결
        menubar_profile_image = findViewById(R.id.menubar_profile_image);//메뉴바에서 나의 닉네임 이미지 연결

        SharedPreferences sharedPreferences = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
        String receive_profile_nickname = sharedPreferences.getString("nickname", ""); //받아온 데이터 String 변수 안에 넣기

        String receive_profile_profile_image = sharedPreferences.getString("profile_image", "닉네임"); //받아온 이미지 데이터 String 변수 안에 넣기
        byte[] encodeByte = Base64.decode(receive_profile_profile_image, Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
        memu_profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
        menubar_profile_nickname.setText(receive_profile_nickname);//닉네임 텍스트에 저장해놓은 텍스트 넣기
        menubar_profile_image.setImageBitmap(memu_profile_bitmap);//닉네임 이미지에 저장해놓은 이미지 넣기



    }


    @Override
    protected void onStart() {
        super.onStart();


        Intent intent1 = getIntent(); //값을 받아 온다
        String strtitle = intent1.getStringExtra("content_title");
        String strcontent = intent1.getStringExtra("content");


        final byte[] arr = getIntent().getByteArrayExtra("image"); //이미지 데이터 받는 소스
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.profileimage); // 이미지에 기본 이미지 설정하기
        // image에 임시적으로 기본 이미지를 넣고 갤러리에서 넘어온 이미지가 있으면 아래 조건문에서 교체한다
        if (arr != null) { //arr로 받는 값이 있으면 todaydairy_image에 이미지를 넣는다
//            Log.e("arr",arr.toString());
            image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }


        if (TodayDiaryCompleteActivity.checkcount == 2) { //나의 일기가 공유 일기로 저장 했을 때
            Mainpage_data mainpage_data = new Mainpage_data(strtitle, strcontent, "0", image); //dm_text내용을 dm_data에 담는다
            mainpage_dataArrayList.add(mainpage_data); //리스트에 dm_data내용을 추가 한다
            mainpage_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
            TodayDiaryCompleteActivity.checkcount = 0;
        }




        home_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //홈트레이닝 화면으로 이동
                Intent intent = new Intent(MainpageActivity.this, HomeTrainingActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //홈 화면으로 이동
                Intent intent = new Intent(MainpageActivity.this, MainpageHomeActivity.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //오늘 일기 화면으로 이동
                Intent intent = new Intent(MainpageActivity.this, BundleDiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });





        otherpeople_dairy_sumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //다른 사람이 올린 다이어리로 이동
                Intent intent = new Intent(MainpageActivity.this, OtherpeopleDiaryActivity.class);
                startActivity(intent);
            }
        });
        // 지울 내용






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
            public void onClick(View view) { //네비게이션 메뉴에서 내 프로필 선택
                Intent intent = new Intent(MainpageActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menubar_exercise_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //네비게이션 메뉴에서 운동기록 선택
                Intent intent = new Intent(MainpageActivity.this, ExerciseReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //네비게이션 메뉴에서 로그아웃 선택
                Intent intent = new Intent(MainpageActivity.this, logInActivity.class);
                logInActivity.my_id = ""; //내 아이디 값 초기화
                startActivity(intent);
                finish();
            }
        });

        //공유한 일기 올리기
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //"TodayDiaryComplete_file"파일의 데이터 받아오기

        String receive_diary_title = shared.getString("diary_title","");
        String receive_diary_image = shared.getString("diary_image", "");
        String receive_diary_heart_count = shared.getString("diary_heart_count", "");
        String receive_diary_comment_count = shared.getString("diary_comment_count", "");
        String receive_diary_profile_nickname = shared.getString("diary_profile_nickname", "");
        //nickname 설정 안했으면 if(nickname==null){ nick = "nickname" } 이런식으로 하는데 처음에 nickname이 null 값이면 "nickname" 으로 만들었다


        String[] temporary_diary_title = receive_diary_title.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        String[] temporary_diary_image = receive_diary_image.split("@");
        String[] temporary_diary_heart_count = receive_diary_heart_count.split("@");
        String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");
        String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@");

        for (int i=0; i < temporary_diary_title.length; i++) { // 모든 일기를 다 반복문으로 돌려보고
//            if (temporary_diary_id[i].equals(logInActivity.my_id)){ // 나중에 공유 한 일기만 하도록 수정!!!!!! 꼭!!!!!!

            Bitmap bitmapimage;
            if (temporary_diary_image[i].equals("null")) {
                 bitmapimage = BitmapFactory.decodeResource(getResources(), R.drawable.exercise_thumbnail); //이미지가 없으면 임시 이미지를 넣자!
            } else {
                byte[] encodeByte = Base64.decode(temporary_diary_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                 bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
            }


            if (!temporary_diary_profile_nickname[i].equals("")) { //값이 "" 이면 아직 일기를 안만들었다는 뜻

                if (!temporary_diary_profile_nickname[i].equals("null")){ //닉네임 값이 "null" 이 아니면(값이 있다면) 닉네임을 보여주고
                    Mainpage_data mainpage_data = new Mainpage_data(temporary_diary_profile_nickname[i], temporary_diary_title[i], temporary_diary_heart_count[i], bitmapimage); //내용들을 bundle_diary_data에 담는다
                    mainpage_dataArrayList.add(0, mainpage_data); //차례대로 보일려면 0 값을 넣어야 한다
                } else if (temporary_diary_profile_nickname[i].equals("null")) { //닉네임 값이 "null"이면 (값이 없다면) "nickname"으로 보여준다
                    Mainpage_data mainpage_data = new Mainpage_data("nickname", temporary_diary_title[i], temporary_diary_heart_count[i], bitmapimage); //내용들을 bundle_diary_data에 담는다
                    mainpage_dataArrayList.add(0, mainpage_data); //차례대로 보일려면 0 값을 넣어야 한다
                }

            }

//            }
        }




        mainpage_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리

















//        SharedPreferences sharedPreferences = getSharedPreferences("mainpage_diary_file",MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
//        String receive_mainpage_diary_title = sharedPreferences.getString("save_mainpage_diary_title",""); //받아온 데이터 String 변수 안에 넣기
//        String receive_mainpage_diary_content = sharedPreferences.getString("save_mainpage_diary_content",""); //받아온 데이터 String 변수 안에 넣기
//        String receive_mainpage_diary_heartcount = sharedPreferences.getString("save_mainpage_diary_heartcount",""); //받아온 데이터 String 변수 안에 넣기
//        String receive_mainpage_diary_bitmapimage = sharedPreferences.getString("save_mainpage_diary_bitmapimage",""); //받아온 데이터 String 변수 안에 넣기
//
////        Log.e("이미지확인2",String.valueOf(receive_mainpage_diary_bitmapimage.contains("@")));
//
//        if (!receive_mainpage_diary_title.equals("")&&!receive_mainpage_diary_content.equals("")&&!receive_mainpage_diary_heartcount.equals("")){
//            //제목 또는 내용이 비어 있을 때!! 상황 오류 잡기
//
//            String[] Array_mainpage_diary_title = receive_mainpage_diary_title.split("@"); //receive_mainpage_diary_title 내용물을 split "/"으로 쪼개고 String 배열에 넣음
//            String[] Array_mainpage_diary_content = receive_mainpage_diary_content.split("@"); //receive_mainpage_diary_content 내용물을 split "/"으로 쪼개고 String 배열에 넣음
//            String[] Array_mainpage_diary_heartcount = receive_mainpage_diary_heartcount.split("@"); //receive_mainpage_diary_heartcount 내용물을 split "/"으로 쪼개고 String 배열에 넣음
//            String[] Array_mainpage_diary_bitmapimage = receive_mainpage_diary_bitmapimage.split("@"); //이미지인 String타입인 receive_mainpage_diary_bitmapimage 내용물을 split "/"으로 쪼개고 String 배열에 넣음
//
////            Log.e("이미지확인3",String.valueOf(Array_mainpage_diary_bitmapimage[0].contains("@")));
//
//            for (int i=0; i < Array_mainpage_diary_title.length; i++) {
////                Log.e("i",String.valueOf(i));
//                byte[] encodeByte = Base64.decode(Array_mainpage_diary_bitmapimage[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
//                Bitmap bitmapimage = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
//
//                Mainpage_data mainpage_data = new Mainpage_data(Array_mainpage_diary_title[i], Array_mainpage_diary_content[i], Array_mainpage_diary_heartcount[i], bitmapimage); //dm_text내용을 dm_data에 담는다
//                mainpage_dataArrayList.add(mainpage_data); //리스트에 dm_data내용을 추가 한다
//            }
//            mainpage_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
//        }







    }

    DrawerLayout.DrawerListener listner = new DrawerLayout.DrawerListener() { //네비게이션 메뉴바 만들기 위해 생성, 지금은 특별한 기능 X
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

//    @Override
//    protected void onStop() {
//        super.onStop();
//        //        Log.e("onstop","나갔다");
//
//
//        bitmaptostring = "";
//        // 다이어리 타이틀, 컨텐츠, 하트 수, 이미지(비트맵)!!!! 비트맵을 string으로 바꾸고 저장후에 다시 빼올 때에는 비트맵으로 바꾸기
//        for (int i=0; i < mainpage_dataArrayList.size(); i++){
//            save_mainpage_diary_title = save_mainpage_diary_title + mainpage_dataArrayList.get(i).getDiary_title() + "@";
//            save_mainpage_diary_content = save_mainpage_diary_content + mainpage_dataArrayList.get(i).getDiary_content() + "@";
//            save_mainpage_diary_heartcount = save_mainpage_diary_heartcount + mainpage_dataArrayList.get(i).getHeartcount() + "@";
//
//
//            temporary_bitmap = mainpage_dataArrayList.get(i).getDiary_image(); //임시적으로 이미지를 받아온다
//
////            if (temporary_bitmap == null){
////                temporary_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.profileimage);
////            }
//            ByteArrayOutputStream stream = new  ByteArrayOutputStream(); //이미지를 바이트로 만들기 위해 스트림 객체 만들기
//            temporary_bitmap.compress(Bitmap.CompressFormat.PNG,100, stream); //비트맵 이미지 stream형식으로
//            byte [] bytes = stream.toByteArray(); //stream 이미지 바이트형식으로
//            bitmaptostring = Base64.encodeToString(bytes, Base64.DEFAULT); // 바이트 형식 string으로 바꾸기
//
//            save_mainpage_diary_bitmapimage = save_mainpage_diary_bitmapimage + bitmaptostring + "@";
//
//            // 비트맵도 저장 해야 한다
//        }
//
////        Log.e("이미지확인",String.valueOf(save_mainpage_diary_bitmapimage.contains("@")));
//
//
//        SharedPreferences sharedPreferences = getSharedPreferences("mainpage_diary_file",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("save_mainpage_diary_title",save_mainpage_diary_title);
//        editor.putString("save_mainpage_diary_content",save_mainpage_diary_content);
//        editor.putString("save_mainpage_diary_heartcount",save_mainpage_diary_heartcount);
//        editor.putString("save_mainpage_diary_bitmapimage",save_mainpage_diary_bitmapimage);
//
////        editor.clear(); //sharedPreferences 저장되어 있는 모든 파일 지우기
//        editor.apply(); //동기,세이브를 완료 해라
//
//    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainpageActivity.this,MainpageHomeActivity.class);
        startActivity(intent);
    }
}