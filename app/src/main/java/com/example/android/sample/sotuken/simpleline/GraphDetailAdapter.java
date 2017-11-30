package com.example.android.sample.sotuken.simpleline;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.sample.sotuken.R;

import java.util.ArrayList;

/**
 * Created by sho on 2017/11/29.
 */

public class GraphDetailAdapter extends BaseAdapter {

    private ArrayList<GraphItem> data;
    private Context context = null;
    private int resource = 0;

    public GraphDetailAdapter(Context context, ArrayList<GraphItem> data,int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    //データ項目の個数を取得
    public int getCount(){return data.size();}

    @Override
    //指定された項目を取得
    public Object getItem(int position){return data.get(position);}

    @Override
    //指定された項目を識別するためのid値を取得
    public long getItemId(int position){
        return data.get(position).getId();
    }

    @Override
    //リスト項目を表示するためのViewを取得
    public View getView(int position, View convertView, ViewGroup parent){

        Activity activity = (Activity)context;
        GraphItem item = (GraphItem)getItem(position);
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        ((TextView) convertView.findViewById(R.id.item_achi)).setText(item.getAchievement());
        ((TextView) convertView.findViewById(R.id.touroku)).setText(item.getResist());
        return convertView;

    }

}
