package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeTrainingStartRestActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton training_pause, training_speaker, training_no_speaker, training_stop;
    private TextView rest_time;
    private String homet_title_item,homet_timenum_item,homet_timetext_item,homet_timenum_item2,homet_timetext_item2,homet_level; //인텐트로 받은 값 사용은 안하고 운동 중지 했을 때 운동 상세페이지에 넘겨주기 위한 용도
    private int playbackPosition; //음악이 일시정지 됐을 때 현재 위치 값 저장하기 위한 변수

    timehanlder handler = new timehanlder(); //핸들러 객체 만들기
    timerthread thread = new timerthread(); //쓰레드 객체 만들기
    private boolean isrunning = true; //쓰레드를 진행 시킬지의 대한 여부

    byte[] arr; // 다른 메소드에서도 쓰기 위해서 전역변수로 만들었다
    int count ; // 쓰레드와 다른 메소드에서도 쓰기 위해서 전역변수로 만들었다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_training_start_rest);

        training_pause = findViewById(R.id.training_pause);
        training_speaker = findViewById(R.id.training_speaker);
        training_no_speaker = findViewById(R.id.training_no_speaker);
        training_stop = findViewById(R.id.training_stop);
        rest_time = findViewById(R.id.rest_time);

        training_pause.setOnClickListener(this);
        training_speaker.setOnClickListener(this);
        training_no_speaker.setOnClickListener(this);
        training_stop.setOnClickListener(this);


        Intent intent = getIntent();
        homet_title_item = intent.getStringExtra("homet_title_item");
        homet_timenum_item = intent.getStringExtra("homet_timenum_item"); //사용은 안하고 뒤로가기 눌렀을 때 값을 다시 보낼 용
        homet_timetext_item = intent.getStringExtra("homet_timetext_item");
        homet_timenum_item2 = intent.getStringExtra("homet_timenum_item2");
        homet_timetext_item2 = intent.getStringExtra("homet_timetext_item2");
        homet_level = intent.getStringExtra("homet_level");
        arr = getIntent().getByteArrayExtra("image"); //이미지 데이터를 바이트로 받는다

        count = 11;
        rest_time.setText(String.valueOf(count));
        thread.start();


    }

    class timehanlder extends Handler { //핸들러에서 UI에 값 넣어주기
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            rest_time.setText(String.valueOf(msg.arg1));
        }
    }


    class timerthread extends Thread{ //점점 운동 시간이 감소 하는 쓰레드
        @Override
        public void run() {
            while (isrunning) {
                Message msg = handler.obtainMessage();
                count--;
                msg.arg1 = count;
                handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (count==0) {
                    Intent intent = new Intent(HomeTrainingStartRestActivity.this,HomeTraningStartActivity.class); //다음 휴식 화면으로 현재 운동,시간 정보를 보내준다
                    intent.putExtra("homet_title_item", homet_title_item);
                    intent.putExtra("homet_timenum_item",homet_timenum_item);
                    intent.putExtra("homet_timetext_item",homet_timetext_item);
                    intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                    intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                    intent.putExtra("homet_level",homet_level);
                    intent.putExtra("image",arr);
                    startActivity(intent);
                    break;
                }
            }
        }
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.training_pause: //다이얼로그 팝업 불러서 운동 종료할 지 물어봄
                AlertDialog.Builder pause = new AlertDialog.Builder(HomeTrainingStartRestActivity.this);

                isrunning = false; //시간 감소 쓰레드 잠시 실행 중지

                if(HomeTrainingDetailActivity.mediaPlayer !=null) { //음악 객체 존재 할 때
                    playbackPosition = HomeTrainingDetailActivity.mediaPlayer.getCurrentPosition();//현재 재생위치 값 저장
                    HomeTrainingDetailActivity.mediaPlayer.pause(); //일시 정지(중지가 아님)
                }


                pause.setIcon(R.drawable.pause);
                pause.setTitle("일시정지");
                pause.setMessage("운동을 계속 하시겠습니까");

                pause.setPositiveButton("운동 재개", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        if(HomeTrainingDetailActivity.mediaPlayer !=null && !HomeTrainingDetailActivity.mediaPlayer.isPlaying()){ //음악 객채는 존재 하는데 플레이가 안될 때
                            HomeTrainingDetailActivity.mediaPlayer.start(); //음악 실행
                            HomeTrainingDetailActivity.mediaPlayer.seekTo(playbackPosition); // 실행 위치는 일시정지 때 저장했던 값에서 부터 실행
                            Toast.makeText(HomeTrainingStartRestActivity.this,"운동을 재개 합니다",Toast.LENGTH_SHORT).show();
                        }


                        isrunning = true; //시간 감소 쓰레드 다시 실행
                        thread.start(); //시간 감소 쓰레드 다시 실행

                        dialogInterface.dismiss(); //다이얼로그 사라지기
                    }
                });
                pause.setNegativeButton("운동 종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeTrainingStartRestActivity.this,"운동을 종료 합니다",Toast.LENGTH_SHORT).show();
                        isrunning = false;

                        Intent intent = new Intent(HomeTrainingStartRestActivity.this, HomeTrainingDetailActivity.class); // 운동 준비 화면에서 받았던 값들 그대로 전달

                        intent.putExtra("homet_title_item", homet_title_item);
                        intent.putExtra("homet_timenum_item",homet_timenum_item);
                        intent.putExtra("homet_timetext_item",homet_timetext_item);
                        intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                        intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                        intent.putExtra("homet_level",homet_level);
                        intent.putExtra("image",arr);

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

                Toast.makeText(HomeTrainingStartRestActivity.this,"음악 정지",Toast.LENGTH_SHORT).show();

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
                    HomeTrainingDetailActivity.mediaPlayer.start(); //음악 실행
                    HomeTrainingDetailActivity.mediaPlayer.seekTo(playbackPosition); // 실행 위치는 일시정지 때 저장했던 값에서 부터 실행
                    Toast.makeText(HomeTrainingStartRestActivity.this,"음악 재생",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.training_stop: //운동 종료후 이전 화면으로 돌아가기
                Toast.makeText(HomeTrainingStartRestActivity.this,"운동을 종료 합니다",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeTrainingStartRestActivity.this, HomeTrainingDetailActivity.class);

                intent.putExtra("homet_title_item", homet_title_item); // 다시 값 돌려주기
                intent.putExtra("homet_timenum_item",homet_timenum_item);
                intent.putExtra("homet_timetext_item",homet_timetext_item);
                intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                intent.putExtra("homet_level",homet_level);
                intent.putExtra("image",arr);

                startActivity(intent);
                finish();
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy 검사 확인");
        if (HomeTrainingDetailActivity.mediaPlayer != null){ //음악 객체가 있으면
            HomeTrainingDetailActivity.mediaPlayer.release(); //음악 취소
            HomeTrainingDetailActivity.mediaPlayer = null; //값 초기화
        }
        isrunning = false; //true 이면 시간이 줄어드는 쓰레드 실행
        thread.interrupt();
    }



}


