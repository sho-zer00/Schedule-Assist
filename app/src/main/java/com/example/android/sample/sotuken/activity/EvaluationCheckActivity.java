package com.example.android.sample.sotuken.activity;

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

import com.example.android.sample.sotuken.CheckPlanAdapter;
import com.example.android.sample.sotuken.EvaluationFlag;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.PlanListItem;
import com.example.android.sample.sotuken.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sho on 2017/10/16.
 */

public class EvaluationCheckActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private PlanDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_layout);

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_time = new Date(System.currentTimeMillis());
        String date = df.format(call_time);
        final String[] params = {date};

        helper = new PlanDatabaseHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] cols = {"_id","date","time","day","doing","flag"};
        Cursor c = db.query(PlanDatabaseHelper.TABLE_NAME,cols,"day <= ? AND flag = 0",params,null,null,"day asc");


        //データベースの中から項目を取り出しアダプターにセットする
        final ArrayList<PlanListItem> data= new ArrayList<>();

        if(c.moveToFirst()){
            do{

                PlanListItem item = new PlanListItem();
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setTitle(c.getString(c.getColumnIndex("doing")));
                item.setTime(c.getString(c.getColumnIndex("date"))+" "+c.getString(c.getColumnIndex("time")));
                data.add(item);
            }while (c.moveToNext());
        }
        adapter = new CheckPlanAdapter(this,data,R.layout.planlist_item);
        final ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //次の画面に渡すデータをセット
                        Intent intent = new Intent(EvaluationCheckActivity.this,EvaluationFlag.class);
                        intent.putExtra("EDATA",data.get(position).getTime());
                        startActivity(intent);
                    }
                });
        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.evaluation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.do_check:
                Intent intent = new Intent(getApplication(),EvaluationCheckActivity.class);
                startActivity(intent);
                break;
            case R.id.yes_list:
                Intent intent1 = new Intent(getApplication(),CheckPlanYesActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplication(), EvaluationActivity.class);
            startActivity(intent);
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }
    }

}
