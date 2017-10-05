package com.example.android.sample.sotuken;

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

/**
 * Created by sho on 2017/08/23.
 */

public class CheckPlanSubActivity extends AppCompatActivity {

    private String date;
    private PlanDatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_sublayout);
        TextView plan_text = (TextView)findViewById(R.id.TitleText);
        TextView days_text = (TextView)findViewById(R.id.UpdateTime);
        Button yes_button = (Button)findViewById(R.id.yes_button);
        Button no_button = (Button)findViewById(R.id.no_button);

        //データを受け取る
        Intent intent = getIntent();
        date = intent.getStringExtra("TIME_DATA");

        //取得した時間は2017/09/13 16:45
        Log.d("TIME","取得した時間は"+date);
        String[] array_date01 = date.split(" ",0);
        for(int i = 0;i<array_date01.length;i++){
            Log.d("split","array_date["+i+"] = "+array_date01[i]);
        }

        //取得した時間をそれぞれ違う変数に格納
        String day = array_date01[0];
        String times = array_date01[1];
        final String[] params = {day,times};

        //データベース読み込み
        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = null;
        try{
            String[] cols = {"_id","date","time","doing","flag"};
            cs = db.query(PlanDatabaseHelper.TABLE_NAME,cols,"date = ? AND time = ?",params,null,null,null);
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
                        db1.update(PlanDatabaseHelper.TABLE_NAME,values,"date = ? AND time = ?",params);
                        db1.close();
                        Toast.makeText(CheckPlanSubActivity.this,"データの書き換えに成功しました", Toast.LENGTH_SHORT).show();
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
