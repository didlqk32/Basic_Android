package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HomeTrainingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton training_web_search, imageButton;
    private Button start_buttom;
    private Spinner training_repeat_spinner, training_level_spinner, training_song_spinner;
    private TextView training_repeat_spinner_text, training_level_spinner_text, training_song_spinner_text;
    private TextView textView2, homet_timenum, homet_timetext, homet_timenum2, homet_timetext2;
    private ImageView exercise_imageicon;
    private final int get_audio_file = 300;
    private String homet_title_item,homet_timenum_item,homet_timetext_item,homet_timenum_item2,homet_timetext_item2;
    static MediaPlayer mediaPlayer;
    static String choice_music;

    static int exercise_count_number;  //운동을 몇회에 나눠서 하는지에 대한 변수
    static int exercise_temporary_count;  //운동을 몇회에 나눠서 하는지에 대한 변수(HomeTraningStart 에서 계산값으로 쓰임)
    static int exercise_level_plus;  //운동 선택 난이도에 따라 한 사이클에 운동하는 시간이 늘어남

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_training_detail);

        training_web_search = findViewById(R.id.training_web_search);
        imageButton = findViewById(R.id.imageButton);
        start_buttom = findViewById(R.id.start_buttom);
        exercise_imageicon = findViewById(R.id.exercise_imageicon);
        textView2 = findViewById(R.id.textView2);
        homet_timenum = findViewById(R.id.homet_timenum);
        homet_timetext = findViewById(R.id.homet_timetext);
        homet_timenum2 = findViewById(R.id.homet_timenum2);
        homet_timetext2 = findViewById(R.id.homet_timetext2);

        training_web_search.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        start_buttom.setOnClickListener(this);



        Intent intent = getIntent();
        homet_title_item = intent.getStringExtra("homet_title_item");
        homet_timenum_item = intent.getStringExtra("homet_timenum_item");
        homet_timetext_item = intent.getStringExtra("homet_timetext_item");
        homet_timenum_item2 = intent.getStringExtra("homet_timenum_item2");
        homet_timetext_item2 = intent.getStringExtra("homet_timetext_item2");

        byte[] arr = getIntent().getByteArrayExtra("image"); //이미지 데이터를 바이트로 받는다
        if (arr != null) { //arr 값이 null 아니면
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length); //바이트로 받은 이미지를 비트맵으로 바꾼다
            exercise_imageicon.setImageBitmap(image); //비트맵으로 바뀐 이미지를 imageview에 넣는다
        }
        textView2.setText(homet_title_item); //타이틀 데이터 넣기
        homet_timenum.setText(homet_timenum_item);
        homet_timetext.setText(homet_timetext_item);
        homet_timenum2.setText(homet_timenum_item2);
        homet_timetext2.setText(homet_timetext_item2);

        //exercise_imageicon.setImageResource();



    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.training_web_search: // 운동 정보 인터넷으로 연결해서 보여주기
                switch (homet_title_item){ //제목으로 분류해서 정보 다르게 보여주기
                    case "스쿼트" :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://terms.naver.com/entry.nhn?docId=450655&cid=42876&categoryId=42876"));
                        startActivity(intent);
                        break;
                    case "푸쉬업" :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://terms.naver.com/entry.nhn?docId=938871&cid=51030&categoryId=51030"));
                        startActivity(intent);
                        break;
                    case "런지" :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://terms.naver.com/entry.nhn?docId=2099784&cid=51030&categoryId=51030"));
                        startActivity(intent);
                        break;
                    case "사이드 프랭크" :
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ko.dict.naver.com/#/entry/koko/7bd16e9986344293823ea4f940798ef4"));
                        startActivity(intent);
                        break;
                }

                break;

            case R.id.imageButton: // 이전 화면으로 전환
                intent = new Intent(getApplicationContext(), HomeTrainingActivity.class);
                startActivity(intent);




                HomeTraining_adapter.checkcount = 0; // 임시 오류 발생 제거용 나중에 꼭 제거 해줘야한다!!!!!!!!!!!!!!!!!!!!!!1




                break;

            case R.id.start_buttom: //시작 버튼을 누르면 운동, 음악 시작
                intent = new Intent(getApplicationContext(), HomeTraningStartActivity.class); //다음 액티비티로 값 전달하기
                if (choice_music.equals("catch_up")) {
                    mediaPlayer = MediaPlayer.create(HomeTrainingDetailActivity.this, R.raw.catch_up); // catch_up 음악 만들고 시작하기,raw 파일에서 가져온 음악
                } else if (choice_music.equals("after_you")) {
                    mediaPlayer = MediaPlayer.create(HomeTrainingDetailActivity.this, R.raw.after_you); // after_you 음악 만들고 시작하기
                } else if (choice_music.equals("everything_you_wanted")) {
                    mediaPlayer = MediaPlayer.create(HomeTrainingDetailActivity.this, R.raw.everything_you_wanted); // everything_you_wanted 음악 만들고 시작하기
                } else {

                }
                mediaPlayer.start();

                if (training_level_spinner_text.getText().toString().equals("초급")){
                    exercise_level_plus = 0;
                } else if (training_level_spinner_text.getText().toString().equals("중급")) {
                    exercise_level_plus = 5;
                } else if (training_level_spinner_text.getText().toString().equals("고급")) {
                    exercise_level_plus = 10;
                }

                exercise_count_number = 4 * Integer.parseInt(training_repeat_spinner_text.getText().toString()); //운동을 몇회에 나눠서 하는지에 대한 변수
                exercise_temporary_count = exercise_count_number;


                intent.putExtra("homet_title_item",textView2.getText()); //타이틀 전달하기
                intent.putExtra("homet_timenum_item",homet_timenum_item);
                intent.putExtra("homet_timetext_item",homet_timetext_item);
                intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                intent.putExtra("homet_level",training_level_spinner_text.getText().toString());

//                Bitmap sendBitmap = BitmapFactory.decodeResource(this, image); // 이미지 전달하기
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                sendBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //stream파일 압축 PNG로 전달하는데 퀄리티는 100퍼센트 그대로
//                byte[] byteArray = stream.toByteArray(); //stream파일 바이트로 변환


                //약간 우회적으로 해서 위에 BitmapFactory 코드 다시 이해해야 한다
                byte[] sendbyte = getIntent().getByteArrayExtra("image");
                intent.putExtra("image",sendbyte); //바이트로 변환 된 이미지 파일 전달


                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        training_repeat_spinner_text = findViewById(R.id.training_repeat_spinner_text);
        training_repeat_spinner = findViewById(R.id.training_repeat_spinner);

        training_repeat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //반복 횟 수 스피너
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                training_repeat_spinner_text.setText(adapterView.getItemAtPosition(i).toString()); // 반복 횟 수 텍스트에 집어넣기

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        training_level_spinner_text = findViewById(R.id.training_level_spinner_text);
        training_level_spinner = findViewById(R.id.training_level_spinner);

        training_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //난이도 조절 스피너
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                training_level_spinner_text.setText(adapterView.getItemAtPosition(i).toString()); // 난이도 텍스트에 집어넣기

                if (training_level_spinner_text.getText().toString().equals("초급")){
                    int temporary = Integer.parseInt(homet_timenum_item)*60 + Integer.parseInt(homet_timenum_item2);
                    homet_timenum.setText(String.valueOf(temporary/60));
                    homet_timenum2.setText(String.valueOf(temporary%60));
                } else if (training_level_spinner_text.getText().toString().equals("중급")) {
                    int temporary = Integer.parseInt(homet_timenum_item)*60 + Integer.parseInt(homet_timenum_item2) + 20;
                    homet_timenum.setText(String.valueOf(temporary/60));
                    homet_timenum2.setText(String.valueOf(temporary%60));
                } else if (training_level_spinner_text.getText().toString().equals("고급")) {
                    int temporary = Integer.parseInt(homet_timenum_item)*60 + Integer.parseInt(homet_timenum_item2) + 40;
                    homet_timenum.setText(String.valueOf(temporary/60));
                    homet_timenum2.setText(String.valueOf(temporary%60));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        training_song_spinner_text = findViewById(R.id.training_song_spinner_text);
        training_song_spinner = findViewById(R.id.training_song_spinner);

        training_song_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //음악 선택 스피너
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                training_song_spinner_text.setText(adapterView.getItemAtPosition(i).toString()); // 음악 선택 텍스트에 집어넣기
                choice_music = adapterView.getItemAtPosition(i).toString();
                if (choice_music.equals("노래 가져오기")) {
                    //Log.e("내 노래 선택", adapterView.getItemAtPosition(i).toString());

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                    startActivity(intent);


                }
                //Toast.makeText(HomeTrainingDetailActivity.this,choice_music,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeTrainingDetailActivity.this,HomeTrainingActivity.class);
        startActivity(intent);
    }
}