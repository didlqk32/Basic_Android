package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private TextView textView;
    private ImageView otherpeople_dairy_image;
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

        textView = findViewById(R.id.textView);
        otherpeople_dairy_image = findViewById(R.id.otherpeople_dairy_image);

    }


    @Override
    protected void onStart() {
        super.onStart();


        textView.setVisibility(View.VISIBLE);
        otherpeople_dairy_image.setVisibility(View.VISIBLE);
//        imageButton.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼 누르기, 삭제!!
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OtherpeopleDiaryActivity.this,MainpageActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


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



//        otherpeople_dairy_wrightingcomment.setFocusableInTouchMode(true);
//        otherpeople_dairy_wrightingcomment.requestFocus();




        otherpeople_dairy_wrightingcomment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textView.setVisibility(View.GONE);
                otherpeople_dairy_image.setVisibility(View.GONE);
                return false;
            }
        });


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
    protected void onPause() {
        super.onPause();







    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OtherpeopleDiaryActivity.this,MainpageActivity.class);
        startActivity(intent);
        finish();
    }
}