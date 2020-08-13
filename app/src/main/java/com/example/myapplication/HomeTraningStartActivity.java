package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeTraningStartActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton training_pause, training_speaker, training_no_speaker, training_stop;
    private int playbackPosition;
    private TextView home_training_start_title; //HomeTrainingDetailActivity 에서 데이터 전달 받아서 사용
    private ImageView home_training_start_image; //HomeTrainingDetailActivity 에서 데이터 전달 받아서 사용
    private String homet_title_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_traning_start);

        training_pause = findViewById(R.id.training_pause);
        training_speaker = findViewById(R.id.training_speaker);
        training_no_speaker = findViewById(R.id.training_no_speaker);
        training_stop = findViewById(R.id.training_stop);
        home_training_start_title = findViewById(R.id.home_training_start_title);
        home_training_start_image = findViewById(R.id.home_training_start_image);

        training_pause.setOnClickListener(this);
        training_speaker.setOnClickListener(this);
        training_no_speaker.setOnClickListener(this);
        training_stop.setOnClickListener(this);

        Intent intent = getIntent();
        homet_title_item = intent.getStringExtra("homet_title_item");
        home_training_start_title.setText(homet_title_item);

        byte[] arr = getIntent().getByteArrayExtra("image"); //이미지 데이터를 바이트로 받는다
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length); //바이트로 받은 이미지를 비트맵으로 바꾼다
        home_training_start_image.setImageBitmap(image); //비트맵으로 바뀐 이미지를 imageview에 넣는다
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.training_pause: //다이얼로그 팝업 불러서 운동 종료할 지 물어봄
                AlertDialog.Builder pause = new AlertDialog.Builder(HomeTraningStartActivity.this);
                pause.setIcon(R.drawable.pause);
                pause.setTitle("일시정지");
                pause.setMessage("운동을 계속 하시겠습니까");

                pause.setPositiveButton("운동 재개", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                pause.setNegativeButton("운동 종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeTraningStartActivity.this, HomeTrainingDetailActivity.class);
                        startActivity(intent);
                        dialogInterface.dismiss();
                        finish();
                    }
                });
                pause.show();
                break;

            case R.id.training_speaker: // 음소거로 전환
                training_no_speaker.setVisibility(View.VISIBLE);
                training_speaker.setVisibility(View.GONE);

                if(HomeTrainingDetailActivity.mediaPlayer !=null) { //음악 객체 존재 할 때
                    playbackPosition = HomeTrainingDetailActivity.mediaPlayer.getCurrentPosition();//현재 재생위치 저장
                    HomeTrainingDetailActivity.mediaPlayer.pause(); //일시 정지(중지가 아님)
                }

//                if (HomeTrainingDetailActivity.mediaPlayer.isPlaying()){ //음악 종료
//                    HomeTrainingDetailActivity.mediaPlayer.stop();
//                    HomeTrainingDetailActivity.mediaPlayer.reset();
//                }


                break;

            case R.id.training_no_speaker: // 소리 나도록 전환
                training_no_speaker.setVisibility(View.GONE);
                training_speaker.setVisibility(View.VISIBLE);

                    if(HomeTrainingDetailActivity.mediaPlayer !=null && !HomeTrainingDetailActivity.mediaPlayer.isPlaying()){ //음악 객채는 존재 하는데 플레이가 안될 때
                        HomeTrainingDetailActivity.mediaPlayer.start();
                        HomeTrainingDetailActivity.mediaPlayer.seekTo(playbackPosition);
                        Toast.makeText(HomeTraningStartActivity.this,"Restart",Toast.LENGTH_SHORT).show();
                    }
//                if(HomeTrainingDetailActivity.choice_music.equals("catch_up")) {
//                    HomeTrainingDetailActivity.mediaPlayer = MediaPlayer.create(HomeTraningStartActivity.this, R.raw.catch_up); // 음악 시작
//                } else if (HomeTrainingDetailActivity.choice_music.equals("after_you")) {
//                    HomeTrainingDetailActivity.mediaPlayer = MediaPlayer.create(HomeTraningStartActivity.this, R.raw.after_you);
//                }  else if (HomeTrainingDetailActivity.choice_music.equals("everything_you_wanted")) {
//                    HomeTrainingDetailActivity.mediaPlayer = MediaPlayer.create(HomeTraningStartActivity.this, R.raw.everything_you_wanted);
//                }
//                HomeTrainingDetailActivity.mediaPlayer.start();

                break;
            case R.id.training_stop: //운동 종료후 이전 화면으로 돌아가기
                Intent intent = new Intent(HomeTraningStartActivity.this, HomeTrainingDetailActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy 검사 확인");
        if (HomeTrainingDetailActivity.mediaPlayer != null){
            HomeTrainingDetailActivity.mediaPlayer.release();
            HomeTrainingDetailActivity.mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "onPause 검사 확인");
        Toast.makeText(HomeTraningStartActivity.this,"운동을 종료 합니다",Toast.LENGTH_LONG).show();


        //음악 끄기! 일시 정지?!
    }


}