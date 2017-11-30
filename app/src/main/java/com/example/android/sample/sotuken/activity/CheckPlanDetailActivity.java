package com.example.android.sample.sotuken.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sample.sotuken.CheckPlanAdapter;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;

/**
 * Created by sho on 2017/10/11.
 */

public class CheckPlanDetailActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private PlanDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanseState){
        super.onCreate(savedInstanseState);
        setContentView(R.layout.planlist_detail_layout);
        TextView plan_text = (TextView)findViewById(R.id.DetailTitleText);
        TextView days_text = (TextView)findViewById(R.id.DetailUpdateTime);
        Button yes_button = (Button)findViewById(R.id.yes_button);
        Button no_button = (Button)findViewById(R.id.no_button);


        //Date情報を受け取る
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        Log.d("date_check","読み込まれたデータは" +id);
        final String[] id_check = {id};

        //データベースに接続
        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cs = null;
        try{
            String[] cols = {"_id","date","time","doing","flag"};
            cs = db.query(PlanDatabaseHelper.TABLE_NAME,cols,"_id  = ?",id_check,null,null,null);
            if(cs.moveToFirst()){
                plan_text.setText(cs.getString(cs.getColumnIndex("doing")));
                days_text.setText(cs.getString(cs.getColumnIndex("date"))+" "+cs.getString(cs.getColumnIndex("time")));
            }
            else{
                Toast.makeText(this,"データがありません",Toast.LENGTH_SHORT).show();
            }
        }finally {
            cs.close();
            db.close();
        }
        //yes_buttonが呼ばれた時
        yes_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //データのフラグを1にする処理を書く
                        SQLiteDatabase db1 = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("flag",1);
                        db1.update(PlanDatabaseHelper.TABLE_NAME,values,"_id = ?",id_check);
                        db1.close();
                        Toast.makeText(CheckPlanDetailActivity.this,"データの書き換えに成功しました", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );

        //no_buttonが呼ばれた時
        no_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //flagはそのまま0にしてメイン画面に移行する処理を書く
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

}
