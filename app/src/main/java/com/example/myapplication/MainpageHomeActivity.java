package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainpageHomeActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private View drawerView;
    private ImageButton menubar_open;
    private Button logout; //로그아웃 버튼

    private Button diary,otherpeople_dairy_button,home_training;
    LinearLayout menubar_my_profile,menubar_exercise_report,menubar_information_of_calorie;

    private ImageView menubar_profile_image; //메뉴바 프로필 이미지
    private TextView menubar_profile_nickname; //메뉴바 프로필 닉네임3
    private Bitmap memu_profile_bitmap; //메뉴바 프로필 이미지 임시 저장
    private long backBtnTime = 0;

    private int total_exercise_count = 0;
    private int total_exercise_time = 0;
    private int total_reduce_kcal = 0;
    private int total_exercise_date = 0;
    private boolean date_comparison = false; //총 운동 일수를 구하기 위해 하루에 여러번 운동해도 값이 증가 되지 않도록 만든 변수

    private TextView exercise_total_timehour_num2;
    private TextView exercise_total_timeminute_num2;
    private TextView exercise_total_reducecalorienum2;
    private TextView exercise_total_datenum2;


    private TextView recommend_article_title;
    private TextView recommend_article_explanation;
    private SimpleDraweeView recommend_article; //fresco의 레이아웃 SimpleDraweeView 를 사용하겠다
    RequestQueue queue ; //네트워크 통신을 하기 위해서 volley는 queue에 담아서 데이터를 처리, RequestQueue는 queue에게 요청 한다는 뜻

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(MainpageHomeActivity.this); //Fresco를 이 화면에서 사용하겠다 /setContentView()가 실행되기 전에 Fresco클래스를 초기화 해야 한다

        setContentView(R.layout.mainpage_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);
        menubar_open = findViewById(R.id.menubar_open);
        menubar_exercise_report = findViewById(R.id.menubar_exercise_report); //메뉴바의 운동기록 버튼
        menubar_information_of_calorie = findViewById(R.id.menubar_information_of_calorie);
        menubar_my_profile = findViewById(R.id.menubar_my_profile); //나의 프로필 연결


        menubar_profile_nickname = findViewById(R.id.menubar_profile_nickname);//메뉴바에서 나의 닉네임 연결
        menubar_profile_image = findViewById(R.id.menubar_profile_image);//메뉴바에서 나의 닉네임 이미지 연결
        logout = findViewById(R.id.logout); //로그아웃 버튼

        diary = findViewById(R.id.diary);
        otherpeople_dairy_button = findViewById(R.id.otherpeople_dairy_button);
        home_training = findViewById(R.id.home_training);

        exercise_total_timehour_num2 = findViewById(R.id.exercise_total_timehour_num2);
        exercise_total_timeminute_num2 = findViewById(R.id.exercise_total_timeminute_num2);
        exercise_total_reducecalorienum2 = findViewById(R.id.exercise_total_reducecalorienum2);
        exercise_total_datenum2 = findViewById(R.id.exercise_total_datenum2);

        recommend_article_title = findViewById(R.id.recommend_article_title);
        recommend_article_explanation = findViewById(R.id.recommend_article_explanation);
        recommend_article = findViewById(R.id.recommend_article);


        queue = Volley.newRequestQueue(this); //네트워크 통신을 하기 위해서 volley는 queue에 담아서 데이터를 처리
        getNews();


        getNaverNews("불고기 칼로리");
    }


    @Override
    protected void onStart() {
        super.onStart();


        SharedPreferences sharedPreferences = getSharedPreferences("Total_Exercise_report_file", MODE_PRIVATE); //운동이 끝나면 운동 내용을 기록한다

        String receive_Total_report_id = sharedPreferences.getString("Total_report_id", "");
        String receive_Total_report_date = sharedPreferences.getString("Total_report_date", "");
        String receive_Total_report_time = sharedPreferences.getString("Total_report_time", "");
        String receive_Total_report_kcal = sharedPreferences.getString("Total_report_kcal", "");

//        Log.e("시간",receive_Total_report_time);

        String[] temporary_Total_report_id = receive_Total_report_id.split("@"); // 문자열 데어터들 각각의 배열에 넣기
        String[] temporary_Total_report_date = receive_Total_report_date.split("@");
        String[] temporary_Total_report_time = receive_Total_report_time.split("@");
        String[] temporary_Total_report_kcal = receive_Total_report_kcal.split("@");



        for (int i = 0; i < temporary_Total_report_id.length; i++) { //배열 크기만큼 운동 기록 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_Total_report_id[i].equals(logInActivity.my_id)) { //현재 로그인 한 아이디와 비교 했을 때 현재 아이디로 작성한 일기만 보여주기


                total_exercise_count++;
                total_exercise_time = total_exercise_time + Integer.parseInt(temporary_Total_report_time[i]); // 운동한 시간 모두 더하기 (단위 초)
                total_reduce_kcal = total_reduce_kcal + Integer.parseInt(temporary_Total_report_kcal[i]); // 소모한 칼로리 모두 더하기


                for (int j = i; j < temporary_Total_report_id.length; j++) {
                    if (temporary_Total_report_id[j].equals(logInActivity.my_id)) {
                        if (temporary_Total_report_date[i].equals(temporary_Total_report_date[j]) && i != j) { //현재 비교하는 일자와 다음 데이터 날짜와 비교 해서 같은 날이면 false;
                            date_comparison = false; // 날짜가 한 번이라도 겹치면 다음 대상 비교로 넘어간다
                            break;
                        } else if (!temporary_Total_report_date[i].equals(temporary_Total_report_date[j]) && i != j) { //현재 비교하는 일자와 다음 데이터 날짜와 비교 해서 다른 날이면 false;
                            date_comparison = true;
                        }
                    }
                }

                if (date_comparison) {
                    total_exercise_date++;
                    date_comparison = false;
                }

            }
        }


        exercise_total_timehour_num2.setText(String.valueOf(total_exercise_time / 3600));
        exercise_total_timeminute_num2.setText(String.valueOf(total_exercise_time / 60));
        exercise_total_reducecalorienum2.setText(String.valueOf(total_reduce_kcal)); //총 소모 칼로리

//        Log.e("길이",String.valueOf(temporary_Total_report_date.length));
//        Log.e("길이",temporary_Total_report_date[0]);

        if(temporary_Total_report_date.length < 2){
            if (temporary_Total_report_date[0].equals("")){
                exercise_total_datenum2.setText("0");
            } else {
                exercise_total_datenum2.setText("1");
            }
        } else if (!temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            exercise_total_datenum2.setText(String.valueOf(total_exercise_date));
        } else if (temporary_Total_report_date[temporary_Total_report_date.length-1].equals(temporary_Total_report_date[temporary_Total_report_date.length-2])){
            //운동 마지막 기록한 일자가 마지막 전 데이터와 같은 날이면 +1 해준다(마지막 배열은 위 코드로 비교할 수 없기 때문에)
            exercise_total_datenum2.setText(String.valueOf(total_exercise_date+1));
        }





        SharedPreferences shared = getSharedPreferences("profile_edit_file", MODE_PRIVATE); //"dm_file"파일의 데이터 받아오기

        String receive_profile_id = shared.getString("profile_id", "");
        String receive_profile_nickname = shared.getString("profile_nickname", "null"); //받아온 데이터 String 변수 안에 넣기
        String receive_profile_image = shared.getString("profile_image", "null"); //받아온 이미지 데이터 String 변수 안에 넣기

        String[] temporary_profile_id = receive_profile_id.split("@"); // 프로필 저장한 아이디 배열에 데이터 저장
        String[] temporary_profile_nickname = receive_profile_nickname.split("@");
        String[] temporary_profile_image = receive_profile_image.split("@");

//        Log.e("닉네임 확인",receive_profile_nickname);


        for (int i = 0; i < temporary_profile_id.length; i++) { //배열 크기만큼 일기 보여주기(배열 인덱스값 1당 일기 1개)
            if (temporary_profile_id[i].equals(logInActivity.my_id)){ //프로필 데이터중에 내 아이디로 쓴 데이터 찾기

//                Log.e("닉네임 확인",temporary_profile_nickname[i]);

                if (!temporary_profile_image[i].equals("null")) {
                    byte[] encodeByte = Base64.decode(temporary_profile_image[i], Base64.DEFAULT); //string으로 받은 이미지 바이트로 바꾸기
                    memu_profile_bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); //바이트로 바꾼 이미지 비트맵으로 바꾸기
                    menubar_profile_image.setImageBitmap(memu_profile_bitmap);//닉네임 이미지에 저장해놓은 이미지 넣기
                }

                if (temporary_profile_nickname[i].equals("null")){
                    menubar_profile_nickname.setText("닉네임");
                } else {
                    menubar_profile_nickname.setText(temporary_profile_nickname[i]);//닉네임 텍스트에 저장해놓은 텍스트 넣기
                }

            }
        }





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
            public void onClick(View view) { // 메뉴바에서 나의 프로필 선택
                Intent intent = new Intent(MainpageHomeActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menubar_exercise_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 메뉴바에서 운동 기록 선택
                Intent intent = new Intent(MainpageHomeActivity.this,ExerciseReportActivity.class);
                startActivity(intent);
                finish();
            }
        });

        menubar_information_of_calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainpageHomeActivity.this,kcal_information.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //네비게이션 메뉴에서 로그아웃 선택
                Intent intent = new Intent(MainpageHomeActivity.this, logInActivity.class);
                logInActivity.my_id = ""; //내 아이디 값 초기화
                startActivity(intent);
                finish();
            }
        });

        home_training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //홈트레이닝 화면으로 이동
                Intent intent = new Intent(MainpageHomeActivity.this,HomeTrainingActivity.class);
                startActivity(intent);
            }
        });

        diary.setOnClickListener(new View.OnClickListener() { //일기 쓰기 화면으로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainpageHomeActivity.this,BundleDiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        otherpeople_dairy_button.setOnClickListener(new View.OnClickListener() { //공유 일기 화면으로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainpageHomeActivity.this,MainpageActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
















    public void getNews() {

        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this); //네트워크 통신을 하기 위해서 volley는 queue에 담아서 데이터를 처리
        String url ="http://newsapi.org/v2/top-headlines?country=kr&category=health&apiKey=5001bcd6fa2e4ff2a5df48a0412c6e40";  // API주소를 입력

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: "+ response.substring(0,500));

                        try {
//                            Log.e("뉴스 기사",response);
                            JSONObject jsonObject = new JSONObject(response);
                            final JSONArray arrayArticles = jsonObject.getJSONArray("articles");

                            final JSONObject obj = arrayArticles.getJSONObject(1);

//                            Log.e("뉴스 기사1",obj.toString());
//                            Log.e("뉴스 기사1_제목",obj.getString("title"));
//                            Log.e("뉴스 기사1_내용",obj.getString("content"));
//                            Log.e("뉴스 기사1_이미지",obj.getString("urlToImage"));

                            obj.getString("title");
                            obj.getString("description");
                            obj.getString("urlToImage");

                            final String ArticleUrl = obj.getString("url");

                            if (obj.getString("title")!=null) {
                                recommend_article_title.setText(obj.getString("title"));
                            } else if (obj.getString("title")==null){
                                recommend_article_title.setText("-");
                            }
                            if (obj.getString("description")!=null) {
                                recommend_article_explanation.setText(obj.getString("description"));
                            } else if (obj.getString("description")==null){
                                recommend_article_explanation.setText("-");
                            }

                            Uri uri = Uri.parse(obj.getString("urlToImage")); //fresco를 이용하여 이미지 보여주기
                            recommend_article.setImageURI(uri);



                            recommend_article.setOnClickListener(new View.OnClickListener() { //기사를 클릭 했을 때 웹상의 기사로 넘어가기
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ArticleUrl));
                                    startActivity(intent);
                                }
                            });




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }













    public void getNaverNews(final String searchObject) {

        final String clientId = "npGw78ax5bfe8OQE3LXs";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "bj4IrqOd7F";//애플리케이션 클라이언트 시크릿값";
        final int display = 5; // 보여지는 검색결과의 수

        // 네트워크 연결은 Thread 생성 필요
        new Thread() {

            @Override
            public void run() {
                try {
                    String text = URLEncoder.encode(searchObject, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/encyc?query=" + text + "&display=" + display + "&"; // json 결과
                    // Json 형태로 결과값을 받아옴.
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                    con.connect();

                    int responseCode = con.getResponseCode();


                    BufferedReader br;
                    if(responseCode==200) { // 정상 호출
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    } else {  // 에러 발생
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }

                    StringBuilder searchResult = new StringBuilder();
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        searchResult.append(inputLine + "\n");

                    }
                    br.close();
                    con.disconnect();



//                    Log.e("sdfsdf",searchResult.toString());



                    JSONObject jsonObject = new JSONObject(searchResult.toString());
                    final JSONArray arrayArticles = jsonObject.getJSONArray("items");

                    final JSONObject obj = arrayArticles.getJSONObject(0);

//                            Log.e("뉴스 기사1",obj.toString());
//                            Log.e("뉴스 기사1_제목",obj.getString("title"));
//                            Log.e("뉴스 기사1_내용",obj.getString("content"));
//                            Log.e("뉴스 기사1_이미지",obj.getString("urlToImage"));

//                    Log.e("SDVKLNSDVJLKN",obj.getString("title"));

                    obj.getString("title");
                    obj.getString("description");
                    obj.getString("urlToImage");
//1









                    String data = searchResult.toString();
                    String[] array;
                    array = data.split("\"");
                    String[] title = new String[display];
                    String[] link = new String[display];
                    String[] description = new String[display];
                    String[] bloggername = new String[display];
                    String[] postdate = new String[display];

                    int k = 0;
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("title"))
                            title[k] = array[i + 2];
                        if (array[i].equals("link"))
                            link[k] = array[i + 2];
                        if (array[i].equals("description"))
                            description[k] = array[i + 2];
                        if (array[i].equals("bloggername"))
                            bloggername[k] = array[i + 2];
                        if (array[i].equals("postdate")) {
                            postdate[k] = array[i + 2];
                            k++;
                        }
                    }

//                    Log.d("확인1", "title잘나오니: " + title[0] + title[1] + title[2]);
                    Log.e("확인1", "title잘나오니: " + title[0] + link[0] + description[0]);;
                    // title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.

                } catch (Exception e) {
                    Log.d("확인2", "error : " + e);
                }

            }
        }.start();

    }













    DrawerLayout.DrawerListener listner = new DrawerLayout.DrawerListener() {
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


    @Override
    public void onBackPressed() {
        //뒤로가기 두 번 누르면 종료하는 기능
        long curtime = System.currentTimeMillis();
        long gaptime = curtime - backBtnTime;

        if (0<=gaptime && 2500 >= gaptime) {

            ActivityCompat.finishAffinity(this);
            System.exit(0);
//            finish();
        } else {
            backBtnTime = curtime;
            Toast.makeText(this,"한번 더 누르면 앱이 종료 됩니다",Toast.LENGTH_SHORT).show();
        }
    }
}
