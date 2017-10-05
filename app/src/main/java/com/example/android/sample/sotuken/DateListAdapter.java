package com.example.android.sample.sotuken;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/04.
 */

public class DateListAdapter extends BaseAdapter {

    private ArrayList<DateListItem> data;
    private Context context = null;
    private int resource = 0;

    //コンストラクタ(コンテキスト、データソース、レイアウトファイルを指定)
    public DateListAdapter(Context context,ArrayList<DateListItem> data, int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    //データ項目の個数を取得
    public int getCount(){
        return data.size();
    }

    @Override
    //指定された項目を取得
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    //指定された項目を識別するためのid値を取得
    public long getItemId(int position){
        return data.get(position).getId();
    }


    @Override
    //リスト項目を表示するためのViewを取得
    public View getView(int position, View convertView, ViewGroup parent){

        Activity activity = (Activity)context;
        DateListItem item = (DateListItem)getItem(position);
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        ((TextView) convertView.findViewById(R.id.sort_date)).setText(item.getDate());
        ((TextView) convertView.findViewById(R.id.sort_date)).setGravity(Gravity.CENTER);
        return convertView;


    }
}
