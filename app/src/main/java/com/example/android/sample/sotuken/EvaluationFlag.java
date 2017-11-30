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

import com.example.android.sample.sotuken.activity.EvaluationCheckActivity;

/**
 * Created by sho on 2017/10/16.
 */

public class EvaluationFlag extends AppCompatActivity {

    private String date;
    private PlanDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_sublayout);
        TextView plan_text = (TextView) findViewById(R.id.TitleText);
        TextView days_text = (TextView) findViewById(R.id.UpdateTime);
        Button yes_button = (Button) findViewById(R.id.yes_button);
        Button no_button = (Button) findViewById(R.id.no_button);

        //データを受け取る
        Intent intent = getIntent();
        date = intent.getStringExtra("EDATA");
        Log.d("evaluation","読み込まれたデータは"+date);

        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"_id","date","time","day","doing","flag"};
        final String[] params = {date};
        Cursor cs = null;
        cs = db.query(helper.TABLE_NAME, cols, "day = ?", params, null, null, null);
        try {
            if (cs.moveToFirst()) {
                plan_text.setText(cs.getString(cs.getColumnIndex("doing")));
                days_text.setText(cs.getString(cs.getColumnIndex("day")));
            } else {
                Toast.makeText(this, "データがありません", Toast.LENGTH_SHORT).show();
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
                        db1.update(PlanDatabaseHelper.TABLE_NAME,values,"day = ?",params);
                        db1.close();
                        Toast.makeText(EvaluationFlag.this,"データの書き換えに成功しました", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), EvaluationCheckActivity.class);
                        startActivity(intent);
                    }
                }
        );

        //no_buttonが呼ばれた時
        no_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //flagを0にする処理を書く
                        SQLiteDatabase db1 = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("flag",0);
                        db1.update(PlanDatabaseHelper.TABLE_NAME,values,"day = ?",params);
                        db1.close();
                        Toast.makeText(EvaluationFlag.this,"データの書き換えに成功しました", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), EvaluationCheckActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
