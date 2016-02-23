package com.example.administrator.noticelist;

/**
 * Created by Administrator on 2016-02-17.
 */
public class Listviewitem {
    private int icon;
    private String title;
    private String inform;

    public int getIcon() {return icon;}
    public String getTitle() {return title;}
    public String getInform() {return inform;}

    public Listviewitem(int icon, String title, String inform){
        this.icon=icon;
        this.title=title;
        this.inform=inform;
    }
}
