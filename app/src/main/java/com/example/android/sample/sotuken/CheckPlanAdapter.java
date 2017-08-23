package com.example.android.sample.sotuken;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;//レイアウトファイルからViewオブジェクトを生成するために使用
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sho on 2017/08/02.
 * CheckPlanのホルダーを使ったアダプタークラスの作成
 */

public class CheckPlanAdapter extends BaseAdapter{

    private ArrayList<PlanListItem> data;
    private Context context = null;
    private int resource = 0;
    private LayoutInflater inflater = null ;

    //コンストラクター(コンテキスト、データソース、レイアウトファイルを指定)
    public CheckPlanAdapter(Context context, ArrayList<PlanListItem> data ,int resource){
        //super();
        this.context = context;
        this.data = data;
        this.resource = resource;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //データ項目の個数を取得
    public int getCount(){
        return data.size();
    }

    //指定された項目を取得
    public Object getItem(int position){
        return data.get(position);
    }

    //指定された項目を識別するためのid値を取得
    public long getItemId(int position){
        return data.get(position).getId();
    }

    //リスト項目を表示するためのViewを取得
    public View getView(int position, View convertView, ViewGroup parent){
        Activity activity = (Activity)context;
        PlanListItem item = (PlanListItem)getItem(position);
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource,null);
        }
        ((TextView) convertView.findViewById(R.id.TitleText)).setText(item.getTitle());
        ((TextView) convertView.findViewById(R.id.UpdateTime)).setText(item.getTime());
        return convertView;
    }

}
