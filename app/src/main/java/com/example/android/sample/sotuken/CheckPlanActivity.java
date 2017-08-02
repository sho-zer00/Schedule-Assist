package com.example.android.sample.sotuken;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by sho on 2017/08/02.
 * CheckPlanのリストを表示するためのアクテビティ
 */

public class CheckPlanActivity extends AppCompatActivity {

    private CheckDataAdapter check;
    private ArrayList<PlanListItem> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_layout);

        check = new CheckDataAdapter(this);
        Cursor c = check.getAllList();

        if(c.moveToFirst()){
            do{
                PlanListItem item = new PlanListItem();
                item.setTitle(c.getString(c.getColumnIndex("doing")));
                item.setTime(c.getString(c.getColumnIndex("date")));
                data.add(item);
            }while (c.moveToNext());
        }

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        rv.setHasFixedSize(true);//固定サイズの場合にパフォーマンスを向上

        //レイアウトマネージャーの準備
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        //アダプターをRecyclerManagerに設定
        RecyclerView.Adapter adapter = new CheckPlanAdapter(data);
        rv.setAdapter(adapter);

    }
}
