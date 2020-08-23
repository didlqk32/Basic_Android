package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class kcal_information extends AppCompatActivity {

    private ArrayList<kcal_information_data> kcalArrayList;
    private kcal_information_adapter kcal_information_adapter;
    private RecyclerView kcal_information_recyclerview;
    private LinearLayoutManager linearLayoutManager;

    private EditText kcal_information_text; //검색 창
    private Button kcal_information_btn; // 검색 버튼

    kcalhanlder handler = new kcalhanlder(); //핸들러 객체 만들기
//    timerthread thread = new timerthread(); //쓰레드 객체 만들기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.kcal_information);

        kcal_information_text = findViewById(R.id.kcal_information_text);
        kcal_information_btn = findViewById(R.id.kcal_information_btn);


        kcal_information_recyclerview = findViewById(R.id.kcal_information_recyclerview); //칼로리 정보 리사이클러뷰
        linearLayoutManager = new LinearLayoutManager(kcal_information.this);
        kcal_information_recyclerview.setLayoutManager(linearLayoutManager);

        kcalArrayList = new ArrayList<>();
        kcal_information_adapter = new kcal_information_adapter(kcalArrayList, kcal_information.this);
        kcal_information_recyclerview.setAdapter(kcal_information_adapter);




        kcal_information_data kcalinformation_data = new kcal_information_data("1","2", "3", "4");
        kcalArrayList.add(0,kcalinformation_data);
        kcal_information_adapter.notifyDataSetChanged();


    }


    @Override
    protected void onStart() {
        super.onStart();

        kcal_information_btn.setOnClickListener(new View.OnClickListener() { //검색 버튼을 눌렀을 때
            @Override
            public void onClick(View view) {
                getNaverNews("바나나");
                Log.e("눌려지는거야?","맞네");
            }
        });
    }


    class kcalhanlder extends Handler { //핸들러에서 UI에 값 넣어주기
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("ASD",String.valueOf(msg.arg1));
        }
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


                    Log.e("SDVKLNSDVJLKN",obj.getString("title"));


                    obj.getString("title");
                    obj.getString("description");
                    obj.getString("urlToImage");
//1




                    kcal_information_data kcalinformation_data = new kcal_information_data("1", "2", "3", "4");
//                    kcal_information_data kcalinformation_data = new kcal_information_data(obj.getString("title"), obj.getString("link"), obj.getString("description"), obj.getString("thumbnail"));
                    kcalArrayList.add(0,kcalinformation_data);
                    kcal_information_adapter.notifyDataSetChanged();







                    Message msg = handler.obtainMessage();
                    msg.arg1 = 555;
                    handler.sendMessage(msg);







//                    String data = searchResult.toString();
//                    String[] array;
//                    array = data.split("\"");
//                    String[] title = new String[display];
//                    String[] link = new String[display];
//                    String[] description = new String[display];
//                    String[] bloggername = new String[display];
//                    String[] postdate = new String[display];
//
//                    int k = 0;
//                    for (int i = 0; i < array.length; i++) {
//                        if (array[i].equals("title"))
//                            title[k] = array[i + 2];
//                        if (array[i].equals("link"))
//                            link[k] = array[i + 2];
//                        if (array[i].equals("description"))
//                            description[k] = array[i + 2];
//                        if (array[i].equals("bloggername"))
//                            bloggername[k] = array[i + 2];
//                        if (array[i].equals("postdate")) {
//                            postdate[k] = array[i + 2];
//                            k++;
//                        }
//                    }
//
//                    Log.d("확인1", "title잘나오니: " + title[0] + title[1] + title[2]);
//                    Log.e("확인1", "title잘나오니: " + title[0] + link[0] + description[0]);;
//                     title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.

                } catch (Exception e) {
                    Log.d("확인2", "error : " + e);
                }
            }

        }.start();

    }




}