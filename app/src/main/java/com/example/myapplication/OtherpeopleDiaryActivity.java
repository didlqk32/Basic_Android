package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton otherdairy_share; //공유하기 취소를 위한 버튼
    int resetting_count = 0;
    int mydiary_check = 0;
    private String temporary_share; //임시적으로 공유여부의 쉐어드를 받기 위한 변수

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
        otherdairy_share = findViewById(R.id.otherdairy_share); //공유 취소를 위한 버튼

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //키보드가 띄워질 때 화면을 위로 올려주는 기능 ,WindowManager 를 인포트 해야 한다


    }


    @Override
    protected void onStart() {
        super.onStart();

        otherpeople_letter.setVisibility(View.VISIBLE);
        final Intent intent = getIntent(); //intent 값을 Mainpage_adapter 에서 받아온다, Mainpage에서 클릭한 공유일기 보기 위해
        final int position = intent.getIntExtra("position", -1); //Bundle_diary에서 선택한 일기의 포지션 값을 받고 처음 작성한 일기라면 초기값으로 -1을 받는다
        final String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        final String date = intent.getStringExtra("date");



        // 내 일기랑 다른 사람 일기랑 차이점 있어야 한다
        SharedPreferences shared = getSharedPreferences("Diary_data_file", MODE_PRIVATE); //일기 데이터 받아오기

        String receive_diary_id = shared.getString("diary_id", "");
        String receive_diary_title = shared.getString("diary_title", "");
        String receive_diary_content = shared.getString("diary_content", "");
        String receive_diary_heart_count = shared.getString("diary_heart_count", ""); //-1은 값이 없다는 것을 표현할 때 사용
        String receive_diary_comment_count = shared.getString("diary_comment_count", "");
        String receive_diary_profile_nickname = shared.getString("diary_profile_nickname", "");
        String receive_diary_date = shared.getString("diary_date", "");

        String receive_diary_image = shared.getString("diary_image", "");
        String receive_diary_profile_image = shared.getString("diary_profile_image", "");


        String[] temporary_diary_id = receive_diary_id.split("@");
        String[] temporary_diary_title = receive_diary_title.split("@");
        String[] temporary_diary_content = receive_diary_content.split("@");
        String[] temporary_diary_heart_count = receive_diary_heart_count.split("@");
        String[] temporary_diary_comment_count = receive_diary_comment_count.split("@");
        String[] temporary_diary_profile_nickname = receive_diary_profile_nickname.split("@");
        String[] temporary_diary_date = receive_diary_date.split("@");

        String[] temporary_diary_image = receive_diary_image.split("@");
        String[] temporary_diary_profile_image = receive_diary_profile_image.split("@");

        //adapter에서 받은 포지션값하고 쉐어드 배열 값하고 단순히 비교하면 안된다, 리싸이클러뷰에서 내 아이디가 아닌 일기는 안보이기 때문에!!!!!!!!!!!!!!!!!!!



        for (int i = 0; i < temporary_diary_id.length; i++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_diary_title[i].equals(title) && temporary_diary_content[i].equals(content) && temporary_diary_date[i].equals(date)) {
                resetting_count = i;
            }
        }


        for (int i = 0; i < temporary_diary_id.length; i++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_diary_id[i].equals(logInActivity.my_id) && temporary_diary_title[i].equals(title) && temporary_diary_content[i].equals(content) && temporary_diary_date[i].equals(date)) { //내가 쓴 일기인지 확인
                mydiary_check = i;
            }
        }

        if (temporary_diary_id[mydiary_check].equals(logInActivity.my_id)){ //선택한 일기가 내 아이디로 쓴 일기라면
            otherpeople_letter.setVisibility(View.GONE); //채팅아이콘 안보이게 하기
            otherdairy_share.setVisibility(View.VISIBLE); //공유버튼 보이게 하기
        }

        other_diary_title.setText(temporary_diary_title[resetting_count]);
        other_diary_content.setText(temporary_diary_content[resetting_count]);
        other_diary_heart_count.setText(temporary_diary_heart_count[resetting_count]);
        other_diary_comment_count.setText(temporary_diary_comment_count[resetting_count]);



        //조거문 해서 이름이 null 이면 nickname으로 바꿔주기!!!!!!!!!!!!!!     꼭!!!!!!!!!!!!!!!

        if (temporary_diary_profile_nickname[resetting_count].equals("null")) {
            other_diary_nickname.setText("nickname");
        } else {
            other_diary_nickname.setText(temporary_diary_profile_nickname[resetting_count]);
        }











        Bitmap bitmapimage_diary; //다이어리 이미지에 대한 부분
        if (temporary_diary_image[resetting_count].equals("null")) {
//            bitmapimage_diary = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); //이미지가 없으면 임시 이미지를 넣자!
            otherpeople_dairy_image.setVisibility(View.GONE); //이미지가 없으면 공간을 없애자
        } else {
            byte[] encodeByte = Base64.decode(temporary_diary_image[resetting_count], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            bitmapimage_diary = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
            otherpeople_dairy_image.setImageBitmap(bitmapimage_diary);
        }





        Bitmap bitmapimage_profile; //프로필 이미지에 대한 부분
        if (temporary_diary_profile_image[resetting_count].equals("null")) {
            bitmapimage_profile = BitmapFactory.decodeResource(getResources(), R.drawable.profileimage); //프로필 이미지가 없으면 임시 이미지를 넣자!
        } else {
            byte[] encodeByte = Base64.decode(temporary_diary_profile_image[resetting_count], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
            bitmapimage_profile = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
        }
        otherpeople_dairy_profile_image.setImageBitmap(bitmapimage_profile);




        //좋아요 눌렀을 때!!!, 댓글 달렸을 때!!!!!! 적용하기!!!!!!!
        //단순히 포지션 값으로 계산 하면 안돼!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!















        otherdairy_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder diary_share_cancle = new AlertDialog.Builder(OtherpeopleDiaryActivity.this); //삭제 여부 다이얼로그 생성
                diary_share_cancle.setIcon(R.drawable.share);
                diary_share_cancle.setTitle("공유 여부");
                diary_share_cancle.setMessage("일기 공유를 취소하시겠습니까?");




                diary_share_cancle.setNegativeButton("공유 취소", new DialogInterface.OnClickListener() {
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
                                Arraylist_share.set(h,"null");
                            }
                        }

                        if (Arraylist_share.size() != 0) {
                            for (int j = 0; j < Arraylist_share.size(); j++) {
                                temporary_share = temporary_share + Arraylist_share.get(j) + "@";
                            }
                        }

                        editor.putString("diary_share", temporary_share);
                        editor.apply(); //동기화,세이브를 완료 해라








                        Intent intent = new Intent(OtherpeopleDiaryActivity.this,MainpageActivity.class);
                        startActivity(intent);
                    }
                });

                diary_share_cancle.setNeutralButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                diary_share_cancle.show(); //다이얼로그 보여주기기


            }
        });

        otherpeople_letter.setOnClickListener(new View.OnClickListener() { // dm보내기 편지 버튼 누르기

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherpeopleDiaryActivity.this,DmActivity.class);

                intent.putExtra("position", position); //포지션값 넘기기
                intent.putExtra("title", title); //제목값 넘기기
                intent.putExtra("content", content); //내용값 넘기기
                intent.putExtra("date", date); //날짜 값 넘기기



                startActivity(intent);
            }
        });


        otherpeople_dairy_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //댓글 작성 버튼 누르기



                if (otherpeople_dairy_wrightingcomment.getText().toString().equals("")){ //댓글을 작성 했는지 여부에 대한 내용
                    Toast.makeText(getApplicationContext(),"댓글을 작성해 주세요",Toast.LENGTH_SHORT).show();
                } else {
                    String my_textmessage = otherpeople_dairy_wrightingcomment.getText().toString(); //dm_text에 edit text에서 썻던 내용을 받는다

                OtherpeopleDiary_data OtherpeopleDiary_data = new OtherpeopleDiary_data(my_textmessage,R.drawable.profileimage); //dm_text내용을 dm_data에 담는다
                otherArrayList.add(OtherpeopleDiary_data); //리스트에 dm_data내용을 추가 한다
                OtherpeopleDiary_adapter.notifyDataSetChanged(); //추가된 내용을 반영하여 다시 정리
                otherpeople_dairy_wrightingcomment.setText("");

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); //키보드를 임의로 올리고 내릴 수 있도록 하는 기능, InputMethodManager 를 인포트 해야 한다
                inputMethodManager.hideSoftInputFromWindow(otherpeople_dairy_wrightingcomment.getWindowToken(), 0);//키보드 내리기
                }

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
    protected void onPause() {
        super.onPause();
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