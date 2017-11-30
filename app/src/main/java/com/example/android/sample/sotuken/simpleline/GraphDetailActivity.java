package com.example.android.sample.sotuken.simpleline;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.activity.CheckPlanActivity;
import com.example.android.sample.sotuken.activity.EvaluationActivity;

import java.util.ArrayList;

/**
 * Created by sho on 2017/11/29.
 */

public class GraphDetailActivity extends AppCompatActivity {

    private GraphDetailAdapter adapter;
    private SimpleLineDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list);

        //データベースに接続
        helper = new SimpleLineDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"_id","achieve","day"};
        Cursor cs = db.query(SimpleLineDatabaseHelper.TABLE_NAME,cols,null,null,null,null,"_id desc");

        //データベースの中から項目を取り出し、アダプターにセット
        final ArrayList<GraphItem>data = new ArrayList<>();
        if(cs.moveToFirst()){
            do {
                GraphItem item = new GraphItem();
                item.setId(cs.getInt(cs.getColumnIndex("_id")));
                item.setAchievement(cs.getString(cs.getColumnIndex("achieve"))+"%");
                item.setResist(cs.getString(cs.getColumnIndex("day")));
                data.add(item);
            }while (cs.moveToNext());
        }
        adapter = new GraphDetailAdapter(this,data,R.layout.simple_item);
        final ListView list = (ListView)findViewById(R.id.simple_list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final long recodeCount = DatabaseUtils.queryNumEntries(db,SimpleLineDatabaseHelper.TABLE_NAME,null,null);

        //アイテムが長押しされた時の処理
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db =  helper.getWritableDatabase();
                if(recodeCount>6) {
                    try {
                        db.delete(helper.TABLE_NAME, "_id = " + id, null);
                        data.remove(id);
                        adapter.notifyDataSetChanged();
                    } finally {
                        db.close();
                    }
                    Toast.makeText(GraphDetailActivity.this,"データを削除しました。",Toast.LENGTH_SHORT).show();
                    Intent intent2 =  new Intent(getApplication(), GraphDetailActivity.class);
                    startActivity(intent2);
                }
                db.close();
                return false;
            }
        });

        cs.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.line_graph:
                Intent intent = new Intent(getApplication(),SimpleLine.class);
                startActivity(intent);
                break;
            case R.id.line_detail:
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplication(),EvaluationActivity.class);
            startActivity(intent);
            return super.onKeyDown(keyCode,event);
        }
        else{
            return false;
        }
    }
}
