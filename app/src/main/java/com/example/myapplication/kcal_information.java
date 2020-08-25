package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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
    private String search_keyword;

//    kcalhanlder handler = new kcalhanlder(); //핸들러 객체 만들기
//    timerthread thread = new timerthread(); //쓰레드 객체 만들기

    private TextView text_test;


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


        text_test = findViewById(R.id.text_test);

//        kcal_information_data kcalinformation_data = new kcal_information_data("1","2", "3", "4");
//        kcalArrayList.add(0,kcalinformation_data);
//        kcal_information_adapter.notifyDataSetChanged();


    }


    @Override
    protected void onStart() {
        super.onStart();



        kcal_information_btn.setOnClickListener(new View.OnClickListener() { //검색 버튼을 눌렀을 때
            @Override
            public void onClick(View view) {
//                getNaverNews("바나나");

                search_keyword = kcal_information_text.getText().toString();


                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            final String str = getNews(search_keyword);
                            runOnUiThread(new Runnable() {  //runOnUiThread 쓰면 따로 handler 안써도 된다
                                @Override
                                public void run() {

//                                    TextView searchResult2 = (TextView) findViewById(R.id.searchResult2);
                                    text_test.setText(str);


//                                    str.replace("<b>","");
//                                    str.replace("</b>","");
                                    String[] str_array = str.split("\n");

                                    ArrayList<String> Arraylist_str = new ArrayList<>();  // 배열을 ArrayList로 담는 과정
                                    for (String temp : str_array) {
                                        Arraylist_str.add(temp);
                                    }
                                    for (int j=0;j<3;j++)
                                    Arraylist_str.remove(0);


//                                    for (int h=0; h<Arraylist_str.size();h++)
//                                    Log.e("배열 내용",Arraylist_str.get(h));


                                    String dictionary_title ="";
                                    String dictionary_link ="";
                                    String dictionary_content ="";
                                    String dictionary_image ="";
                                    for (int k=0;k<Arraylist_str.size();k++){
                                        if ((k%4)==0){
                                            dictionary_title += Arraylist_str.get(k);
                                        } else if ((k%4)==1) {
                                            dictionary_link += Arraylist_str.get(k);
                                        } else if ((k%4)==2) {
                                            dictionary_content += Arraylist_str.get(k);
                                        } else if ((k%4)==3) {
                                            dictionary_image += Arraylist_str.get(k);
                                        }
                                    }

                                    dictionary_content.replace("<b>","");
                                    dictionary_content.replace("</b>","");


                                    Log.e("제목",dictionary_title);
                                    Log.e("링크",dictionary_link);
                                    Log.e("내용",dictionary_content);
                                    Log.e("이미지",dictionary_image);


                                    kcal_information_data kcalinformation_data = new kcal_information_data(dictionary_title,dictionary_content,dictionary_image,dictionary_link);
                                    kcalArrayList.add(kcalinformation_data);
                                    kcal_information_adapter.notifyDataSetChanged();


//                                    for (int i=0; i < strarray.length; i++){
//                                        kcal_information_data kcalinformation_data = new kcal_information_data("1",strarray[i], "3", "4");
//                                        kcalArrayList.add(kcalinformation_data);
//                                    }
//                                    kcal_information_adapter.notifyDataSetChanged();


//                                    kcal_information_data kcalinformation_data = new kcal_information_data("1","2", "3", "4");
//                                    kcalArrayList.add(0,kcalinformation_data);
//
//                                    kcal_information_adapter.notifyDataSetChanged();

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });thread.start();




                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); //키보드를 임의로 올리고 내릴 수 있도록 하는 기능, InputMethodManager 를 인포트 해야 한다
                inputMethodManager.hideSoftInputFromWindow(kcal_information_text.getWindowToken(), 0);//키보드 내리기
                kcal_information_text.setText("");
            }
        });
    }



    public String getNews(final String keyword) {
        String clientID = "npGw78ax5bfe8OQE3LXs";
        String clientSecret = "bj4IrqOd7F";
        StringBuffer sb = new StringBuffer();
        StringBuffer sb_title = new StringBuffer();
        StringBuffer sb_content = new StringBuffer();
        StringBuffer sb_image = new StringBuffer();
        StringBuffer sb_link = new StringBuffer();
        final int display = 1; // 보여지는 검색결과의 수

        try {

            String text = URLEncoder.encode(keyword, "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/search/local.xml?query=" + text + "&display=" + display + "&start=1";


            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Naver-Client-Id", clientID);
            conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            String tag;
            //inputStream으로부터 xml값 받기
            xpp.setInput(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("item")) ; //첫번째 검색 결과
                        else if (tag.equals("title")) {

//                            sb.append("제목 : ");

                            xpp.next();


                            if (xpp.getText()==null){
                                sb.append("null");
                            } else {
                                sb.append(xpp.getText());
                            }


//                            sb.append(xpp.getText());
//                            sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));

                            sb.append("\n");

                        } else if (tag.equals("description")) {

//                            sb.append("내용 : ");
                            xpp.next();


                            if (xpp.getText()==null){
                                sb.append("null");
                            } else {
                                sb.append(xpp.getText());
                            }


//                            sb.append(xpp.getText());
//                            sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            sb.append("\n");

                        } else if (tag.equals("thumbnail")){
//                            sb.append("이미지 : ");
                            xpp.next();


                            if (xpp.getText()==null){
                                sb.append("null");
                            } else {
                                sb.append(xpp.getText());
                            }

//                            sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            sb.append("\n");
//                            sb.append("#");

                        } else if (tag.equals("link")){
//                            sb.append("링크 : ");
                            xpp.next();

                            if (xpp.getText()==null){
                                sb.append("null");
                            } else {
                                sb.append(xpp.getText());
                            }


//                            sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            sb.append("\n");
                        }
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }




















//    class kcalhanlder extends Handler { //핸들러에서 UI에 값 넣어주기
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            Log.e("ASD",String.valueOf(msg.arg1));
//        }
//    }
//
//
//    public void getNaverNews(final String searchObject) {
//
//        final String clientId = "npGw78ax5bfe8OQE3LXs";//애플리케이션 클라이언트 아이디값";
//        final String clientSecret = "bj4IrqOd7F";//애플리케이션 클라이언트 시크릿값";
//        final int display = 5; // 보여지는 검색결과의 수
//
//        // 네트워크 연결은 Thread 생성 필요
//        new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    String text = URLEncoder.encode(searchObject, "UTF-8");
//                    String apiURL = "https://openapi.naver.com/v1/search/encyc?query=" + text + "&display=" + display + "&"; // json 결과
//                    // Json 형태로 결과값을 받아옴.
//                    URL url = new URL(apiURL);
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setRequestProperty("X-Naver-Client-Id", clientId);
//                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
//                    con.connect();
//
//                    int responseCode = con.getResponseCode();
//
//
//                    BufferedReader br;
//                    if(responseCode==200) { // 정상 호출
//                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    } else {  // 에러 발생
//                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//                    }
//
//                    StringBuilder searchResult = new StringBuilder();
//                    String inputLine;
//                    while ((inputLine = br.readLine()) != null) {
//                        searchResult.append(inputLine + "\n");
//
//                    }
//                    br.close();
//                    con.disconnect();
//
//
//
////                    Log.e("sdfsdf",searchResult.toString());
//
//
//
//                    JSONObject jsonObject = new JSONObject(searchResult.toString());
//                    final JSONArray arrayArticles = jsonObject.getJSONArray("items");
//
//                    final JSONObject obj = arrayArticles.getJSONObject(0);
//
////                            Log.e("뉴스 기사1",obj.toString());
////                            Log.e("뉴스 기사1_제목",obj.getString("title"));
////                            Log.e("뉴스 기사1_내용",obj.getString("content"));
////                            Log.e("뉴스 기사1_이미지",obj.getString("urlToImage"));
//
//
//                    Log.e("SDVKLNSDVJLKN",obj.getString("title"));
//
//
//                    obj.getString("title");
//                    obj.getString("description");
//                    obj.getString("urlToImage");
////1
//
//
//
//
//                    kcal_information_data kcalinformation_data = new kcal_information_data("1", "2", "3", "4");
////                    kcal_information_data kcalinformation_data = new kcal_information_data(obj.getString("title"), obj.getString("link"), obj.getString("description"), obj.getString("thumbnail"));
//                    kcalArrayList.add(0,kcalinformation_data);
//                    kcal_information_adapter.notifyDataSetChanged();
//
//
//
//
//
//
//
//                    Message msg = handler.obtainMessage();
//                    msg.arg1 = 555;
//                    handler.sendMessage(msg);
//
//
//
//
//
//
//
////                    String data = searchResult.toString();
////                    String[] array;
////                    array = data.split("\"");
////                    String[] title = new String[display];
////                    String[] link = new String[display];
////                    String[] description = new String[display];
////                    String[] bloggername = new String[display];
////                    String[] postdate = new String[display];
////
////                    int k = 0;
////                    for (int i = 0; i < array.length; i++) {
////                        if (array[i].equals("title"))
////                            title[k] = array[i + 2];
////                        if (array[i].equals("link"))
////                            link[k] = array[i + 2];
////                        if (array[i].equals("description"))
////                            description[k] = array[i + 2];
////                        if (array[i].equals("bloggername"))
////                            bloggername[k] = array[i + 2];
////                        if (array[i].equals("postdate")) {
////                            postdate[k] = array[i + 2];
////                            k++;
////                        }
////                    }
////
////                    Log.d("확인1", "title잘나오니: " + title[0] + title[1] + title[2]);
////                    Log.e("확인1", "title잘나오니: " + title[0] + link[0] + description[0]);;
////                     title[0], link[0], bloggername[0] 등 인덱스 값에 맞게 검색결과를 변수화하였다.
//
//                } catch (Exception e) {
//                    Log.d("확인2", "error : " + e);
//                }
//            }
//
//        }.start();
//
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(kcal_information.this,MainpageHomeActivity.class);
        startActivity(intent);
    }
}