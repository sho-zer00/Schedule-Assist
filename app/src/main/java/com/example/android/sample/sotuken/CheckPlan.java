package com.example.android.sample.sotuken;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sho on 2017/08/02.
 * ビューホルダーの作成
 * ビューホルダーとはビューを保持するためのクラス
 * アダプターで利用するためのクラス
 */

public class CheckPlan extends RecyclerView.ViewHolder {

    //ビューに配置されたウィジェットを保持しておくためのプライベート変数
    View view;
    TextView title;
    TextView time;

    //コンストラクタ(ウィジェットへの参照を格納)
    public CheckPlan(View itemView){
        super(itemView);
        this.view = itemView;
        this.title = (TextView)view.findViewById(R.id.TitleText);
        this.time = (TextView)view.findViewById(R.id.UpdateTime);
    }
}
