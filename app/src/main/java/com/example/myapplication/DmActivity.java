package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DmActivity extends AppCompatActivity {

    private ArrayList<Dm_data> dataArrayList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Dm_adapter dm_adapter;
    private EditText send_my_dm;
    private Button send_button;
    private ImageButton send_camera,send_picture,send_emoticon;

    private String save_dm_content = ""; //dm_content를 save 하기 위한 변수 생성
    private String save_dm_current_time = ""; //current_time를 save 하기 위한 변수 생성
    private Boolean save_dm_booleans = false; //m_booleans를 save 하기 위한 변수 생성

    private FirebaseDatabase firebaseDatabase; //데이터 베이스에 접근할 수 있도록 함
    private DatabaseReference databaseReference; //데이터 베이스의 주소를 저장
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private int position = 0;
    private String title = "";
    private String content = "";
    private String date ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dm);

        recyclerView = findViewById(R.id.dm_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
//        ((LinearLayoutManager) linearLayoutManager).setReverseLayout(true); //아이템이 역순으로 쌓인다 -> 가장 최근 글이 가장 위로 올라온다
        recyclerView.setLayoutManager(linearLayoutManager);

        dataArrayList = new ArrayList<>();
        dm_adapter = new Dm_adapter(dataArrayList);
        recyclerView.setAdapter(dm_adapter);


        send_my_dm = findViewById(R.id.send_my_dm);
        send_button = findViewById(R.id.send_button);

        send_camera = findViewById(R.id.send_camera);
        send_picture = findViewById(R.id.send_picture);
        send_emoticon = findViewById(R.id.send_emoticon);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final Intent intent = getIntent(); //intent 값을 Mainpage_adapter 에서 받아온다, Mainpage에서 클릭한 공유일기 보기 위해
        position = intent.getIntExtra("position", -1); //Bundle_diary에서 선택한 일기의 포지션 값을 받고 처음 작성한 일기라면 초기값으로 -1을 받는다
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");

        send_my_dm.addTextChangedListener(new TextWatcher() { //edit text의 값이 변화했을 때 기능 구현(텍스트가 입력 되면 기능을 한다)
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { //edit text에 텍스트가 들어 갔을 때 기능 구현
//                send_button.setVisibility(View.VISIBLE);
//                send_camera.setVisibility(View.GONE);
//                send_picture.setVisibility(View.GONE);
//                send_emoticon.setVisibility(View.GONE);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //보내기 버튼 누르면 기능 구현

                Date current_time = Calendar.getInstance().getTime(); //현재 날짜 구하기
                SimpleDateFormat current_dmtime_format = new SimpleDateFormat("a h시 m분", Locale.KOREA);// 현재 시간을 구화기 위한 객체 선언
                String dm_text = send_my_dm.getText().toString(); //dm_text에 edit text에서 썻던 내용을 받는다
                String dm_time = current_dmtime_format.format(current_time); //현재 시간을 string 타입으로 넣음

                Dm_data dm_data = new Dm_data(dm_text,dm_time,true); //dm_text내용을 dm_data에 담는다
                dataArrayList.add(dm_data); //리스트에 dm_data내용을 추가 한다
                dm_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
                recyclerView.scrollToPosition(dm_adapter.getItemCount()-1); //가장 최근 채팅에 스크롤 포지션 가도록 함

//                send_button.setVisibility(View.GONE);
//                send_camera.setVisibility(View.VISIBLE);
//                send_picture.setVisibility(View.VISIBLE);
//                send_emoticon.setVisibility(View.VISIBLE);


                send_my_dm.setText(null); //edit text 내용을 비게 만듦

//        String[] str = save_dm_content.split("/");
//        for (int i=0; i < str.length; i++) {
//            saveArrayListy.add(str[i]);
//        }
//
//        String ttt2 = null;
//        for (int i=0; i < saveArrayListy.size(); i++){
//            ttt2 = ttt2 + saveArrayListy.get(i)+"/";
//        }
//        Log.e("ttt",ttt2);


//                if (dataArrayList.get(1)!=null) {
//                    Toast.makeText(view.getContext(),"dd", Toast.LENGTH_SHORT).show();
//                }
//                send_button.setVisibility(View.GONE);
//                send_camera.setVisibility(View.VISIBLE);
//                send_picture.setVisibility(View.VISIBLE);
//                send_emoticon.setVisibility(View.VISIBLE);


//                Toast.makeText(DmActivity.this, String.valueOf(dataArrayList.size()), Toast.LENGTH_SHORT).show();
            }
        });

        send_camera.setOnClickListener(new View.OnClickListener() {   //임시로 만든 버튼 -> 상대방이 채팅할 경우
            @Override
            public void onClick(View view) { //임시적으로 카메라 버튼에 상대방이 보낸 메세지 버튼 연결함!!!!!나중에 지워야해!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                Date current_time = Calendar.getInstance().getTime(); //현재 날짜 구하기
                SimpleDateFormat current_dmtime_format = new SimpleDateFormat("a h시 m분", Locale.KOREA);// 현재 시간을 구화기 위한 객체 선언
                String dm_text = send_my_dm.getText().toString(); //dm_text에 edit text에서 썻던 내용을 받는다
                String dm_time = current_dmtime_format.format(current_time); //현재 시간을 string 타입으로 넣음

                Dm_data dm_data = new Dm_data(dm_text,dm_time,false); //dm_text내용을 dm_data에 담는다
                dataArrayList.add(dm_data); //리스트에 dm_data내용을 추가 한다
                dm_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
                send_my_dm.setText(null); //edit text 내용을 비게 만듦

//                Toast.makeText(DmActivity.this, String.valueOf(dataArrayList.size()), Toast.LENGTH_SHORT).show();
            }
        });





        SharedPreferences sharedPreferences = getSharedPreferences("dm_file",MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기
        String receive_dm_content = sharedPreferences.getString("save_dm_content",""); //받아온 데이터 String 변수 안에 넣기
        String receive_dm_current_time = sharedPreferences.getString("save_dm_current_time",""); //받아온 데이터 String 변수 안에 넣기

        Log.e("receive_dm_content",receive_dm_content);
        Log.e("receive_dm_current_time",receive_dm_current_time);
//        Toast.makeText(DmActivity.this,receive_dm_content,Toast.LENGTH_SHORT).show();

        if (!receive_dm_content.equals("")&&!receive_dm_current_time.equals("")){
            String[] Array_dm_content = receive_dm_content.split("/"); //receive_dm_content 내용물을 split "/"으로 쪼개고 String 배열에 넣음
            String[] Array_dm_content_time = receive_dm_current_time.split("/"); //receive_dm_current_time 내용물을 split "/"으로 쪼개고 String 배열에 넣음

            Log.e("Array_dm_content_time",Array_dm_content[0]);

            for (int i=0; i < Array_dm_content.length; i++) {
                Dm_data dm_data = new Dm_data(Array_dm_content[i],Array_dm_content_time[i], true); //dm_text내용을 dm_data에 담는다
                dataArrayList.add(dm_data); //리스트에 dm_data내용을 추가 한다
            }
            dm_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
            recyclerView.scrollToPosition(dm_adapter.getItemCount()-1); //가장 최근 채팅에 스크롤 포지션 가도록 함




        }

        //DB연동하기



    }



    @Override
    protected void onStop() {
        super.onStop();
        for (int i=0; i < dataArrayList.size(); i++){
            save_dm_content = save_dm_content + dataArrayList.get(i).getDm_content() + "/";
            save_dm_current_time = save_dm_current_time + dataArrayList.get(i).getDm_time() + "/";
//            save_dm_booleans = save_dm_booleans + dataArrayList.get(i).getItemViewType(); //booleans는 어떻게 값을 넣지??
        }

//        Log.e("onstop","나갔다");
//        Log.e("save_dm_content",save_dm_content);
//        Log.e("save_dm_current_time",save_dm_current_time);

        SharedPreferences sharedPreferences = getSharedPreferences("dm_file",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("save_dm_content",save_dm_content);
        editor.putString("save_dm_current_time",save_dm_current_time);

//        editor.remove("save_dm_content"); // sharedPreferences 저장되어 있는 키 값이 "save_dm_content" 지우기
//        editor.remove("save_dm_current_time"); // sharedPreferences 저장되어 있는 키 값이 "save_dm_content" 지우기
//        editor.clear(); //sharedPreferences 저장되어 있는 모든 파일 지우기
        editor.apply(); //동기,세이브를 완료 해라

//        for(Object object : dataArrayList) {
//            ttt = ttt + object + "/";
//        }
//        Toast.makeText(DmActivity.this, ttt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DmActivity.this,OtherpeopleDiaryActivity.class);

        intent.putExtra("position", position); //포지션값 넘기기
        intent.putExtra("title", title); //제목값 넘기기
        intent.putExtra("content", content); //내용값 넘기기
        intent.putExtra("date", date); //날짜 값 넘기기

        startActivity(intent);
    }
}