package com.example.android.sample.sotuken;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/04.
 */

public class CheckPlanDateActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private PlanDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_layout);

        //Date情報を受け取る
        Intent intent = getIntent();
        String date = intent.getStringExtra("SORT_DATE");
        Log.d("date_check","読み込まれたデータは" +date);
        String[] date_check = {date};

        //データベースに接続
        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"_id","date","time","doing","flag"};
        Cursor cs = db.query(PlanDatabaseHelper.TABLE_NAME,cols,"date = ?",date_check,null,null,"date asc,time asc");

        //データベスの中から項目を取り出しアダプターにセット
        final ArrayList<PlanListItem> data= new ArrayList<>();

        if(cs.moveToFirst()){
            do{

                PlanListItem item = new PlanListItem();
                item.setId(cs.getInt(cs.getColumnIndex("_id")));
                item.setTitle(cs.getString(cs.getColumnIndex("doing")));
                item.setTime(cs.getString(cs.getColumnIndex("date"))+" "+cs.getString(cs.getColumnIndex("time")));
                data.add(item);
            }while (cs.moveToNext());
        }


        adapter = new CheckPlanAdapter(this,data,R.layout.planlist_item);
        final ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                        SQLiteDatabase db = helper.getWritableDatabase();
                        try{
                            db.delete(helper.TABLE_NAME, "_id = "+ id ,null);
                            data.remove(id);
                            adapter.notifyDataSetChanged();

                        }finally {
                            db.close();
                        }

                        //Intent intent = new Intent(getApplication(), CheckPlanSubActivity.class);
                        Toast.makeText(CheckPlanDateActivity.this,"sucess",Toast.LENGTH_SHORT).show();
                        Intent intent =  new Intent(getApplication(), CheckPlanActivity.class);
                        startActivity(intent);
                        return false;
                    }
                }
        );

        cs.close();
    }
}
