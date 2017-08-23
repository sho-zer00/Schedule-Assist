package com.example.android.sample.sotuken;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sho on 2017/08/02.
 * CheckPlanのリストを表示するためのアクテビティ
 */

public class CheckPlanActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private CheckDataAdapter check;
    private ArrayList<PlanListItem> data = new ArrayList<>();
    private PlanDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_layout);

        helper = new PlanDatabaseHelper(this);
        //データベースに接続
        check = new CheckDataAdapter(this);
        Cursor c = check.getAllList();

        adapter = new CheckPlanAdapter(this,data,R.layout.planlist_item);
        final ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        //データベースの中から項目を取り出す
        if(c.moveToFirst()){
            do{
                PlanListItem item = new PlanListItem();
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setTitle(c.getString(c.getColumnIndex("doing")));
                item.setTime(c.getString(c.getColumnIndex("date")));
                data.add(item);
                //adapter.notifyDataSetChanged();
            }while (c.moveToNext());
        }

        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                        /*
                        SQLiteDatabase db = helper.getWritableDatabase();
                        try{
                            db.delete(helper.TABLE_NAME, "_id = "+ id ,null);
                            adapter.notifyDataSetChanged();
                        }finally {
                            db.close();
                        }
                        */
                        Intent intent = new Intent(getApplication(), CheckPlanSubActivity.class);
                        //Toast.makeText(CheckPlanActivity.this,"sucess",Toast.LENGTH_SHORT).show();
                        

                    }
                }
        );
        c.close();
    }

}
