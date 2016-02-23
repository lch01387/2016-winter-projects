package com.example.administrator.noticelist;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Intent in_Data;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> titleList = new ArrayList<String>(); // 제목을 담아 adapter에 넘겨줄것
        ArrayAdapter Adapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList);

        listView = (ListView)findViewById(R.id.listView);
        in_Data = new Intent(getApplicationContext(), DataView.class);
        mContext = this.getApplication().getApplicationContext();

        // 데이터 원본 준비
        ArrayList<Listviewitem> selectOne = new ArrayList<>();
        Listviewitem haksa = new Listviewitem(R.drawable.kookmin_mark, "학사공지", "국민대학교 공지사항");
        Listviewitem hakbu = new Listviewitem(R.drawable.kookmin_mark, "학부공지", "국민대학교 컴퓨터공학과 공지사항");
        Listviewitem janghak = new Listviewitem(R.drawable.kookmin_mark, "장학공지", "국민대학교 공지사항");
        Listviewitem iljung = new Listviewitem(R.drawable.kookmin_mark, "학사일정", "국민대학교 학사일정");
        Listviewitem menu = new Listviewitem(R.drawable.restaurant_icon, "학생식당", "오늘자 학생식당 점심메뉴");
        Listviewitem stack = new Listviewitem(R.drawable.stackoverflow_logo, "StackOverflow", "프로그래밍 질의응답 사이트");
        Listviewitem bloter = new Listviewitem(R.drawable.bloter_logo_kor_750, "BLOTER", "IT전문 뉴스 블로그");
        Listviewitem zdnet = new Listviewitem(R.drawable.zdnet_logo, "ZDNet Korea", "컴퓨터 및 정보통신 뉴스");

        selectOne.add(haksa);
        selectOne.add(hakbu);
        selectOne.add(janghak);
        selectOne.add(iljung);
        selectOne.add(menu);
        selectOne.add(stack);
        selectOne.add(bloter);
        selectOne.add(zdnet);

        ListviewAdapter adapter = new ListviewAdapter( getLayoutInflater() , selectOne);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isNetworkAvailable(getApplicationContext())){
                    Toast.makeText(mContext, "통신상태가 좋지 않습니다", Toast.LENGTH_LONG).show();
                }else {
                    if (position == 3) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kookmin.ac.kr/site/resource/guide/cal.htm")));
                    } else if (position == 4) {
                        startActivity(new Intent(getApplicationContext(), MenuView.class));
                    } else if (position == 5) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stackoverflow.com/")));
                    } else if (position == 6) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bloter.net/")));
                    } else if (position == 7) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.zdnet.co.kr/")));
                    } else {
                        in_Data.putExtra("position", position);
                        startActivity(in_Data);
                        //positon 0:학사공지 1:학부공지 2:장학공지 3:학사일정 4:stackoverflow 5:bloter 6:zdnet
                        //Toast.makeText(mContext, Integer.toString(position), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    // 인터넷에 연결되었는지 알려주는 함수
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
                || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING)
        {
            return true;
        }
        else
        if (connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING)
        {

            return true;
        }
        else
            return false;
    }
}
