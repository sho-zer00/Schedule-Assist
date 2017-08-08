package com.example.android.sample.sotuken;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;//レイアウトファイルからViewオブジェクトを生成するために使用
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sho on 2017/08/02.
 * CheckPlanのホルダーを使ったアダプタークラスの作成
 */

public class CheckPlanAdapter extends RecyclerView.Adapter<CheckPlan> {

    private ArrayList<PlanListItem> data;

    //コンストラクタ(データソースを準備)
    public CheckPlanAdapter(ArrayList<PlanListItem> data){
        this.data = data;
    }

    private View.OnLongClickListener longlistener;

    private View.OnClickListener listener;

    //ビューホルダーを生成
    //アダプターはこれを利用して、個々のリスト項目を生成して行く
    @Override
    public CheckPlan onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planlist_item,parent,false);
        return new CheckPlan(v);
    }

    //ビューにデータを割り当て、リスト項目を生成
    @Override
    public void onBindViewHolder(CheckPlan holder, int position){
        holder.title.setText(this.data.get(position).getTitle());
        holder.time.setText(this.data.get(position).getTime());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                longlistener.onLongClick(view);
                return true;
            }
        });
    }

    public boolean setOnLongClickListener(View.OnLongClickListener longlistener) {
        this.longlistener = longlistener;
        return true;
    }
    //データ項目数を取得
    @Override
    public int getItemCount(){
        return this.data.size();
    }




}
