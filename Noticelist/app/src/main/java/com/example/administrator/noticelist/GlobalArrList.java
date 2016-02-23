package com.example.administrator.noticelist;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-02-19.
 */
public class GlobalArrList {
    ArrayList<String> arrList = new ArrayList<String>();

    public void add(String str) {
        arrList.add(str);
    }

    public String get(int position) {
        return arrList.get(position);
    }
}
