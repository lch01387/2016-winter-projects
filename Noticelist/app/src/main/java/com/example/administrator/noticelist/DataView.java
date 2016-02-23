package com.example.administrator.noticelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DataView extends AppCompatActivity {
    Intent in_get;
    Intent in_Web;
    String URL;
    Document doc;
    ListView listView;
    ArrayAdapter adapter;
    TextView textView;
    ArrayList<String> urlList;
    Context mContext;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view2);

        in_get = getIntent();
        in_Web = new Intent(this, WebPage.class);
        position = in_get.getIntExtra("position", -1);
        listView = (ListView)findViewById(R.id.listView2);
        textView = (TextView)findViewById(R.id.textView);
        mContext = this.getApplication().getApplicationContext();
        urlList = new ArrayList<String>(); // URL을 담을 arrayList


        if(position == -1){
            finish(); // position정보가 제대로 전달되지 않았다면 activity를 종료
        }else if(position == 0){ // 학사공지
            textView.setText("학사 공지");
            URL = "http://eecs.kookmin.ac.kr/site/computer/work.htm";

            // 메인 UI스레드와 충돌 -> 새로운 스레드 생성
            Thread downloadThread = new Thread() {
                public void run() {
                    ArrayList<String> titleList = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
                    try {
                        doc = Jsoup.connect(URL).get();           // timeout(1000)
                        int skip = 9;
                        // 한글 인코딩
                        //String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                        //Document doc = Jsoup.parse(res);
                        Elements contents = doc.select("td > a");

                        for(Element content : contents){
                            if(skip > 0){
                                skip--;
                                continue;
                            }
                            String title = content.text();
                            String link = "http://eecs.kookmin.ac.kr" + content.attr("href");
                            Log.i("title", title);
                            Log.i("link", link);
                            titleList.add(title);
                            urlList.add(link);
                        }
                        adapter= new ArrayAdapter(DataView.this, android.R.layout.simple_list_item_1, titleList);
                        // UI스레드만이 view에 접근할 수 있다.
                        listView.post(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                            }
                        });
                        //ArrayAdapter<String> adapter;
                       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titleList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            downloadThread.start();
        }else if(position == 1){ // 학부공지
            textView.setText("학부 공지");
            URL = "http://eecs.kookmin.ac.kr/site/computer/notice.htm";

            // 메인 UI스레드와 충돌 -> 새로운 스레드 생성
            Thread downloadThread = new Thread() {
                public void run() {
                    ArrayList<String> titleList = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
                    try {
                        doc = Jsoup.connect(URL).get();           // timeout(1000)
                        // 한글 인코딩
                        //String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                        //Document doc = Jsoup.parse(res);
                        Elements contents = doc.getElementsByClass("bd_subject");

                        for(Element content : contents){
                            String title = content.text();
                            String link = "http://eecs.kookmin.ac.kr" + content.attr("href");
                            Log.i("title", title);
                            Log.i("link", link);
                            titleList.add(title);
                            urlList.add(link);
                        }
                        adapter= new ArrayAdapter(DataView.this, android.R.layout.simple_list_item_1, titleList);
                        // UI스레드만이 view에 접근할 수 있다.
                        listView.post(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                            }
                        });
                        //ArrayAdapter<String> adapter;
                        // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titleList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            downloadThread.start();
        }else if(position == 2){ // 장학공지
            textView.setText("장학 공지");
            URL = "http://www.kookmin.ac.kr/site/resource/board/scholarship/";

            // 메인 UI스레드와 충돌 -> 새로운 스레드 생성
            Thread downloadThread = new Thread() {
                public void run() {
                    ArrayList<String> titleList = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
                    try {
                        doc = Jsoup.connect(URL).get();           // timeout(1000)
                        int skip = 9;
                        // 한글 인코딩
                        //String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                        //Document doc = Jsoup.parse(res);
                        Elements contents = doc.select("td > a");

                        for(Element content : contents){
                            String title = content.text();
                            String link = "http://www.kookmin.ac.kr/site/resource/board/scholarship" +  content.attr("href").substring(1);
                            Log.i("title", title);
                            Log.i("link", link);
                            titleList.add(title);
                            urlList.add(link);
                        }
                        adapter= new ArrayAdapter(DataView.this, android.R.layout.simple_list_item_1, titleList);
                        // UI스레드만이 view에 접근할 수 있다.
                        listView.post(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                            }
                        });
                        //ArrayAdapter<String> adapter;
                        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titleList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            downloadThread.start();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, urlList.get(position), Toast.LENGTH_LONG).show();
                in_Web.putExtra("URL", urlList.get(position));
                startActivity(in_Web);
            }
        });
    }
}
