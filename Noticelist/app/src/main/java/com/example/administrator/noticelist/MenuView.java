package com.example.administrator.noticelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MenuView extends AppCompatActivity {
    String URL;
    Document doc;
    ArrayAdapter adapter;
    ListView list_haksik;
    ListView list_hanul;
    int day_of_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        list_haksik = (ListView)findViewById(R.id.list_haksik);
        list_hanul = (ListView)findViewById(R.id.list_hanul);

        Calendar cal = Calendar.getInstance();
        // 일요일:1 토요일:7
        day_of_week = cal.get(Calendar.DAY_OF_WEEK);

        Thread1.start(); // 복지관 식당
        Thread2.start(); // 법학관 식당
    }
    // 메인 UI스레드와 충돌 -> 새로운 스레드 생성
    Thread Thread1 = new Thread() {
        public void run() {
            URL = "http://kmucoop.kookmin.ac.kr/restaurant/restaurant.php?w=2";
            ArrayList<String> titleList = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
            try {
                doc = Jsoup.connect(URL).get();           // timeout(1000)
                // 한글 인코딩
                //String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                int skip = 2;
                int check = 0; // 0이면 이전줄 공백, 1이면 이전줄 공백아님
                int checkDay = 2; // 2:월요일  6: 금요일
                int whatTime = 1; // 조식, 중식, 석식
                Elements contents = doc.getElementsByClass("ft1");

                for(Element content : contents){
                    if(skip > 0){
                        skip--;
                        continue;
                    }
                    String title = content.text();
                    if(title.isEmpty()){
                        check = 0;
                        continue;
                    }
                    if(check == 0){
                        checkDay = 2;
                    }else{
                        checkDay++;
                    }
                    if(day_of_week == checkDay){
                        title = title.replaceAll("\\(.*?\\)","");
                        title = title.replaceAll("\\[.*?\\]", "");
                        title = title.replaceAll("[*].*?[*]", "");
                        title = title.replaceAll("\\p{Space}", "");
                        if (whatTime == 1){ title = "조식) " + title; }
                        else if (whatTime < 6){ title = "중식) " + title; }
                        else { title = "석식) " + title; }
                        whatTime++;
                        titleList.add(title);
                    }
                    check = 1; // 이번줄 공백아님
                }
                adapter= new ArrayAdapter(MenuView.this, android.R.layout.simple_list_item_1, titleList);
                // UI스레드만이 view에 접근할 수 있다.
                list_haksik.post(new Runnable() {
                    public void run() {
                        list_haksik.setAdapter(adapter);
                    }
                });
                //ArrayAdapter<String> adapter;
                // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titleList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Thread Thread2 = new Thread() {
        public void run() {
            // 한울식당
            URL = "http://kmucoop.kookmin.ac.kr/restaurant/restaurant.php?w=1";
            ArrayList<String> titleList2 = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
            try {
                doc = Jsoup.connect(URL).get();           // timeout(1000)
                // 한글 인코딩
                //String res = new String(super.handleResponse(response).getBytes("8859_1"), "euc-kr");
                int skip = 2;
                int check = 0; // 0이면 이전줄 공백, 1이면 이전줄 공백아님
                int checkDay = 2; // 2:월요일  6: 금요일
                int whatTime = 1; // 조식, 중식, 석식
                Elements contents = doc.getElementsByClass("ft1");

                for(Element content : contents){
                    if(skip > 0){
                        skip--;
                        continue;
                    }
                    String title = content.text();
                    if(title.isEmpty()){
                        check = 0;
                        continue;
                    }
                    if(check == 0){
                        checkDay = 2;
                    }else{
                        checkDay++;
                    }
                    if(day_of_week == checkDay){
                        title = title.replaceAll("\\(.*?\\)","");
                        title = title.replaceAll("\\[.*?\\]", "");
                        title = title.replaceAll("[*].*?[*]", "");
                        title = title.replaceAll("\\p{Space}", "");
                        title = title.replace("￦", "");
                        title = title.replaceAll("[0-9]", "");

                        if (whatTime == 4 || whatTime == 5){ title = "중,석식) " + title; }
                        else { title = "중식) " + title; }
                        whatTime++;
                        titleList2.add(title);
                    }
                    check = 1; // 이번줄 공백아님
                }
                adapter= new ArrayAdapter(MenuView.this, android.R.layout.simple_list_item_1, titleList2);
                // UI스레드만이 view에 접근할 수 있다.
                list_hanul.post(new Runnable() {
                    public void run() {
                        list_hanul.setAdapter(adapter);
                    }
                });
                //ArrayAdapter<String> adapter;
                // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, titleList2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
