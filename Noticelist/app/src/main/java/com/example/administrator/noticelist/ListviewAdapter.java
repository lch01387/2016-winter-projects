package com.example.administrator.noticelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// custom adapter
public class ListviewAdapter extends BaseAdapter {
    ArrayList<Listviewitem> datas;
    LayoutInflater inflater;

    public ListviewAdapter(LayoutInflater inflater, ArrayList<Listviewitem> datas) {
        this.datas= datas;
        this.inflater= inflater;
    }
    @Override
    public int getCount(){return datas.size();}
    @Override
    public Object getItem(int position){return datas.get(position);}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item,parent,false);
        }
        Listviewitem listviewitem=datas.get(position);

        TextView text_title= (TextView)convertView.findViewById(R.id.text_title);
        TextView text_inform= (TextView)convertView.findViewById(R.id.text_inform);
        ImageView img_kookmin= (ImageView)convertView.findViewById(R.id.img_kookmin);

        text_title.setText( datas.get(position).getTitle() );
        text_inform.setText( datas.get(position).getInform() );
        img_kookmin.setImageResource( datas.get(position).getIcon() );

        return convertView;
    }
}