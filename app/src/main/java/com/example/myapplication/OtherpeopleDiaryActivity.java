package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OtherpeopleDiaryActivity extends AppCompatActivity {

    private ArrayList<OtherpeopleDiary_data> otherArrayList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private OtherpeopleDiary_adapter OtherpeopleDiary_adapter;

    private ImageButton imageButton,otherpeople_letter;
    private Button otherpeople_dairy_post;
    private EditText otherpeople_dairy_wrightingcomment;
    private TextView other_diary_title,other_diary_content,other_diary_heart_count,other_diary_comment_count,other_diary_nickname;
    private ImageView otherpeople_dairy_image,otherpeople_dairy_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherpeople_diary);

        recyclerView = findViewById(R.id.otherpeople_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        otherArrayList = new ArrayList<>();
        OtherpeopleDiary_adapter = new OtherpeopleDiary_adapter(otherArrayList,this);
        recyclerView.setAdapter(OtherpeopleDiary_adapter);

        imageButton = findViewById(R.id.imageButton);
        otherpeople_letter = findViewById(R.id.otherpeople_letter);
        otherpeople_dairy_post = findViewById(R.id.otherpeople_dairy_post);
        otherpeople_dairy_wrightingcomment = findViewById(R.id.otherpeople_dairy_wrightingcomment);

        other_diary_title = findViewById(R.id.other_diary_title);
        other_diary_content = findViewById(R.id.other_diary_content);
        otherpeople_dairy_image = findViewById(R.id.otherpeople_dairy_image);
        other_diary_heart_count = findViewById(R.id.other_diary_heart_count);
        other_diary_comment_count = findViewById(R.id.other_diary_comment_count);
        other_diary_nickname = findViewById(R.id.other_diary_nickname);
        otherpeople_dairy_profile_image = findViewById(R.id.otherpeople_dairy_profile_image);

    }


    @Override
    protected void onStart() {
        super.onStart();

        otherpeople_letter.setVisibility(View.VISIBLE);
        final Intent intent = getIntent(); //intent 값을 Mainpage_adapter 에서 받아온다, Mainpage에서 클릭한 공유일기 보기 위해
        final int position = intent.getIntExtra("position", -1); //Bundle_diary에서 선택한 일기의 포지션 값을 받고 처음 작성한 일기라면 초기값으로 -1을 받는다

        // 내 일기랑 다른 사람 일기랑 차이점 있어야 한다
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_diary_id = shared.getString("diary_id", "");
        String receive_diary_title = shared.getString("diary_title", "");
        String receive_diary_content = shared.getString("diary_content", "");
        String receive_diary_heart_count = shared.getString("diary_heart_count", ""); //-1은 값이 없다는 것을 표현할 때 사용
        String receive_diary_comment_count = shared.getString("diary_comment_count", "");
        String receive_diary_profile_nickname = shared.getString("diary_profile_nickname", "");

        String receive_diary_image = shared.getString("diary_image", "");
        String receive_diary_profile_image = shared.getString("diary_profile_image", "");


        String[] temporary_diary_id = receive_diary_id.split("@");
        String[] temporary_diary_title = receive_diary_title.split("@");
        String[] temporary_diary_content = receive_diary_content.split("@");
        String[] temporary_diary_heart_count = receive_diary_heart_count.split("@");
        String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");
        String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@");

        String[] temporary_diary_image = receive_diary_image.split("@");
        String[] temporary_diary_profile_image = receive_diary_profile_image.split("@");

        //adapter에서 받은 포지션값하고 쉐어드 배열 값하고 단순히 비교하면 안된다, 리싸이클러뷰에서 내 아이디가 아닌 일기는 안보이기 때문에!!!!!!!!!!!!!!!!!!!



        //나중에  temporary_diary_id.length-1-position 이부분 포지션값 하고 다시 계산해서 다시 반영해야한다!!!!!!!!!!!! //나의 일기에서도 마찬가지!!!!!!!!!!!
        if (temporary_diary_id[temporary_diary_id.length-1-position].equals(logInActivity.my_id)){ //선택한 일기가 내 아이디로 쓴 일기라면
            otherpeople_letter.setVisibility(View.GONE);
        }

        other_diary_title.setText(temporary_diary_title[temporary_diary_title.length-1-position]);
        other_diary_content.setText(temporary_diary_content[temporary_diary_content.length-1-position]);
        other_diary_heart_count.setText(temporary_diary_heart_count[temporary_diary_heart_count.length-1-position]);
        other_diary_comment_count.setText(temporary_diary_comment_count[temporary_diary_comment_count.length-1-position]);
        other_diary_nickname.setText(temporary_diary_profile_nickname[temporary_diary_profile_nickname.length-1-position]);


        Bitmap bitmapimage_diary;
        if (temporary_diary_image[temporary_diary_image.length-1-position].equals("null")) {
            bitmapimage_diary = BitmapFactory.decodeResource(getResources(), R.drawable.exercise_thumbnail); //이미지가 없으면 임시 이미지를 넣자!
        } else {
            byte[] encodeByte = Base64.decode(temporary_diary_image[temporary_diary_image.length-1-position], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            bitmapimage_diary = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
        }
        otherpeople_dairy_image.setImageBitmap(bitmapimage_diary);


        Bitmap bitmapimage_profile;
        if (temporary_diary_profile_image[temporary_diary_profile_image.length-1-position].equals("null")) {
            bitmapimage_profile = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); //이미지가 없으면 임시 이미지를 넣자!
        } else {
            byte[] encodeByte = Base64.decode(temporary_diary_profile_image[temporary_diary_profile_image.length-1-position], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            bitmapimage_profile = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
        }
        otherpeople_dairy_profile_image.setImageBitmap(bitmapimage_profile);



        //좋아요 눌렀을 때!!!, 댓글 달렸을 때!!!!!! 적용하기!!!!!!!
        //단순히 포지션 값으로 계산 하면 안돼!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!










        otherpeople_letter.setOnClickListener(new View.OnClickListener() { // dm보내기 편지 버튼 누르기

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherpeopleDiaryActivity.this,DmActivity.class);
                startActivity(intent);
            }
        });


        otherpeople_dairy_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String my_textmessage = otherpeople_dairy_wrightingcomment.getText().toString(); //dm_text에 edit text에서 썻던 내용을 받는다

                OtherpeopleDiary_data OtherpeopleDiary_data = new OtherpeopleDiary_data(my_textmessage,R.drawable.profileimage); //dm_text내용을 dm_data에 담는다
                otherArrayList.add(OtherpeopleDiary_data); //리스트에 dm_data내용을 추가 한다
                OtherpeopleDiary_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
                otherpeople_dairy_wrightingcomment.setText("");
            }
        });


//        otherpeople_dairy_wrightingcomment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                other_diary_title.setVisibility(View.GONE);
//                otherpeople_dairy_image.setVisibility(View.GONE);
//                return false;
//            }
//        });


//        otherpeople_dairy_wrightingcomment.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OtherpeopleDiaryActivity.this,MainpageActivity.class);
        startActivity(intent);
        finish();
    }
}