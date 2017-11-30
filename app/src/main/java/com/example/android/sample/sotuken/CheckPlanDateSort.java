package com.example.android.sample.sotuken;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.sample.sotuken.activity.Check;
import com.example.android.sample.sotuken.activity.CheckPlanActivity;
import com.example.android.sample.sotuken.activity.CheckPlanDateActivity;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/04.
 */

public class CheckPlanDateSort extends AppCompatActivity{
    private DateListAdapter adapter;
    private PlanDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_sort);

        //データベースに接続&日付の取り出し
        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"date"};
        String orderStr = "date asc";
        Cursor cs = null;
        cs = db.query(true,PlanDatabaseHelper.TABLE_NAME,cols,null,null,null,null,orderStr,null);

        //データベースの中から項目を取り出しアダプターにセットする
        final ArrayList<DateListItem> data = new ArrayList<>();
        if(cs.moveToFirst()){
            do{
                DateListItem item = new DateListItem();
                item.setDate(cs.getString(cs.getColumnIndex("date")));
                data.add(item);
            }while (cs.moveToNext());
        }

        adapter = new DateListAdapter(this,data,R.layout.date_item);
        final ListView list = (ListView)findViewById(R.id.list_date);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //ListViewのアイテム選択イベント
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //次の画面に渡すデータをセット
                Intent intent = new Intent(CheckPlanDateSort.this,CheckPlanDateActivity.class);
                intent.putExtra("SORT_DATE",data.get(position).getDate());
                startActivity(intent);
            }
        });

        cs.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plan_list_get, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                Intent intent = new Intent(getApplication(),CheckPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.date_sort:
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplication(),Check.class);
            startActivity(intent);
            return super.onKeyDown(keyCode,event);
        }
        else{
            return false;
        }
    }
}
