package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Delayed;


public class HomeTraningStartActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton training_pause, training_speaker, training_no_speaker, training_stop;
    private int playbackPosition; //음악이 일시정지 됐을 때 현재 위치 값 저장하기 위한 변수
    private TextView home_training_start_title; //HomeTrainingDetailActivity 에서 데이터 전달 받아서 사용
    private ImageView home_training_start_image; //HomeTrainingDetailActivity 에서 데이터 전달 받아서 사용
    private String homet_title_item;
    public TextView training_time; //운동 시간을 나타내는 변수
    private String homet_timenum_item,homet_timetext_item,homet_timenum_item2,homet_timetext_item2,homet_level;

    private int exercise_time; //운동을 몇회에 나눠서 하는지에 대한 변수

    private boolean isrunning = true; //쓰레드를 진행 시킬지의 대한 여부
    timehanlder handler = new timehanlder(); //핸들러 객체 만들기
    timerthread thread = new timerthread(); //쓰레드 객체 만들기
    int count ; // 다른 메소드에서도 쓰기 위해서 전역변수로 만들었다
    byte[] arr; // 다른 메소드에서도 쓰기 위해서 전역변수로 만들었다


//    public  Handler handler = new Handler();
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
        training_time = findViewById(R.id.training_time);

        training_pause.setOnClickListener(this);
        training_speaker.setOnClickListener(this);
        training_no_speaker.setOnClickListener(this);
        training_stop.setOnClickListener(this);

        Intent intent = getIntent();
        homet_title_item = intent.getStringExtra("homet_title_item");
        home_training_start_title.setText(homet_title_item);

        arr = getIntent().getByteArrayExtra("image"); //이미지 데이터를 바이트로 받는다
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length); //바이트로 받은 이미지를 비트맵으로 바꾼다
        home_training_start_image.setImageBitmap(image); //비트맵으로 바뀐 이미지를 imageview에 넣는다

        homet_timenum_item = intent.getStringExtra("homet_timenum_item"); //사용은 안하고 뒤로가기 눌렀을 때 값을 다시 보낼 용
        homet_timetext_item = intent.getStringExtra("homet_timetext_item");
        homet_timenum_item2 = intent.getStringExtra("homet_timenum_item2");
        homet_timetext_item2 = intent.getStringExtra("homet_timetext_item2");
        homet_level = intent.getStringExtra("homet_level");





        exercise_time = (((Integer.parseInt(homet_timenum_item)*60)+Integer.parseInt(homet_timenum_item)) / 4) + HomeTrainingDetailActivity.exercise_level_plus  ;
//        Log.e("운동",String.valueOf(HomeTrainingDetailActivity.exercise_count_number));

//        exercise_count_number = 4; //계속 4로 됨 이 부분 !!!!!!!!!!!수정 필요!!!!!!!!!!!!!!!!!!!!!!!


//        HometrainingThread hometrainingThread = new HometrainingThread(40);

//        handler.post(runnable);


//        handler.post(runnable);
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//            }
//        };



//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        Handler handler = new Handler();
//        BackgroundThread thread = new BackgroundThread();
//        handler.post(thread);
//        thread.start();

        count = exercise_time + 1;
        training_time.setText(String.valueOf(count-1));
        thread.start();

    }


    class timehanlder extends Handler { //핸들러에서 UI에 값 넣어주기
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            training_time.setText(String.valueOf(msg.arg1));
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

//                    Log.e("운동 횟수1",String.valueOf(HomeTrainingDetailActivity.exercise_count_number));
                    HomeTrainingDetailActivity.exercise_count_number --; //운동과 휴식이 한번씩 끝나면 -1
//                    Log.e("운동 횟수2",String.valueOf(HomeTrainingDetailActivity.exercise_count_number));

                    if (HomeTrainingDetailActivity.exercise_count_number==0){
//                        Log.e("종료","확인");
//                        Toast.makeText(HomeTraningStartActivity.this,"운동이 끝났습니다",Toast.LENGTH_SHORT).show();


                        //여기에 칼로리 소모 기록?!!!!!!!!!!!!!!!!!! shared 써서!!!!!!!!!! //여기도 날짜 기록??? //운동 시간!!!















                        Date currentTime = Calendar.getInstance().getTime(); //현재 날짜 구하기
                        String date_text = new SimpleDateFormat("yyyy.M.dd", Locale.getDefault()).format(currentTime);  //날짜 형태 정해서 변수에 넣기

                        //운동 완료 기록!!!! //쉐어드는 항상 아이디랑 같이 기록 해야 한다!!! 날짜도 기록!!!! // 운동 횟수?!!!!!!!!!!!-> 그냥 length로 하면 될 거 같고
                        SharedPreferences shared = getSharedPreferences("Exercise_report_file", MODE_PRIVATE); //운동이 끝나면 운동 내용을 기록한다
                        SharedPreferences.Editor editor = shared.edit();

                        String receive_report_id = shared.getString("Exercise_report_id", "");
                        String receive_report_date = shared.getString("Exercise_report_date", "");
                        String receive_report_title = shared.getString("Exercise_report_title", "");
                        String receive_report_level = shared.getString("Exercise_report_level", "");

                        receive_report_id = receive_report_id + logInActivity.my_id + "@"; //기존 저장된 데이터에서 현재 데이터 더하기
                        receive_report_date = receive_report_date + date_text + "@";
                        receive_report_title = receive_report_title + homet_title_item + "@";
                        receive_report_level = receive_report_level + homet_level + "@";


                        editor.putString("Exercise_report_id", receive_report_id);
                        editor.putString("Exercise_report_date", receive_report_date);
                        editor.putString("Exercise_report_title", receive_report_title);
                        editor.putString("Exercise_report_level", receive_report_level);
//                        editor.clear();
                        editor.apply(); //동기,세이브를 완료 해라

                        Log.e("운동 종료 되면 값 저장 되나?!",receive_report_date);


                        Intent intent = new Intent(HomeTraningStartActivity.this,HomeTrainingDetailActivity.class); //다음 휴식 화면으로 현재 운동,시간 정보를 보내준다
                        intent.putExtra("homet_title_item", homet_title_item);
                        intent.putExtra("homet_timenum_item",homet_timenum_item);
                        intent.putExtra("homet_timetext_item",homet_timetext_item);
                        intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                        intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                        intent.putExtra("homet_level",homet_level);
                        intent.putExtra("image",arr);
                        startActivity(intent);

                    } else {
                        Log.e("아직 종료 아님","확인");
                        Intent intent = new Intent(HomeTraningStartActivity.this,HomeTrainingStartRestActivity.class); //다음 휴식 화면으로 현재 운동,시간 정보를 보내준다
                        intent.putExtra("homet_title_item", homet_title_item);
                        intent.putExtra("homet_timenum_item",homet_timenum_item);
                        intent.putExtra("homet_timetext_item",homet_timetext_item);
                        intent.putExtra("homet_timenum_item2",homet_timenum_item2);
                        intent.putExtra("homet_timetext_item2",homet_timetext_item2);
                        intent.putExtra("homet_level",homet_level);
                        intent.putExtra("image",arr);
                        startActivity(intent);
                    }
                    break;
                }
            }
        }
    }

//    class BackgroundThread extends Thread {
//        int value = 0;
//        boolean running = false;
//
//        public void run() {
//            running = true;
//            while(running) {
//                value += 1;
//                Message message = handler.obtainMessage();
//                Bundle bundle = new Bundle();
//                bundle.putInt("value",value);
//                message.setData(bundle);
//                handler.sendMessage(message);
//
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {}
//            }
//        }
//    }

//    class ValueHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//            Bundle bundle = msg.getData();
//            int value = bundle.getInt("value");
//            training_time.setText("현재 값 : " + value);
//        }
//    }

//    class HometrainingThread extends Thread {
//        public HometrainingThread (int value){
//            this.value = value;
//        }
//        int value ;
//        boolean running = false;
//        public void run() {
//            for (int i=value; i>0;i--){
//
//                training_time.setText(String.valueOf(i));
//
////                try {
////                    sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }
//        }
//    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.training_pause: //다이얼로그 팝업 불러서 운동 종료할 지 물어봄
                AlertDialog.Builder pause = new AlertDialog.Builder(HomeTraningStartActivity.this);

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
                            Toast.makeText(HomeTraningStartActivity.this,"운동을 재개 합니다",Toast.LENGTH_SHORT).show();
                        }


                        isrunning = true; //시간 감소 쓰레드 다시 실행
                        thread.start(); //시간 감소 쓰레드 다시 실행

                        dialogInterface.dismiss(); //다이얼로그 사라지기
                    }
                });
                pause.setNegativeButton("운동 종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeTraningStartActivity.this,"운동을 종료 합니다",Toast.LENGTH_SHORT).show();
                        isrunning = false;

                        Intent intent = new Intent(HomeTraningStartActivity.this, HomeTrainingDetailActivity.class); // 운동 준비 화면에서 받았던 값들 그대로 전달

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

                Toast.makeText(HomeTraningStartActivity.this,"음악 정지",Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(HomeTraningStartActivity.this,"음악 재생",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeTraningStartActivity.this,"운동을 종료 합니다",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeTraningStartActivity.this, HomeTrainingDetailActivity.class);

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
//        Log.e("onDestroy", "onDestroy 검사 확인");
        if (HomeTrainingDetailActivity.mediaPlayer != null){ //음악 객체가 있으면
            HomeTrainingDetailActivity.mediaPlayer.release(); //음악 취소
            HomeTrainingDetailActivity.mediaPlayer = null; //값 초기화
        }
        isrunning = false; //true 이면 시간이 줄어드는 쓰레드 실행
//        thread.interrupt();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e("onPause", "onPause 검사 확인");
//        Toast.makeText(HomeTraningStartActivity.this,"운동을 종료 합니다",Toast.LENGTH_LONG).show();

    }
}