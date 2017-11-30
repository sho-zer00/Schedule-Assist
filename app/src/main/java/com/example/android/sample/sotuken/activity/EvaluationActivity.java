package com.example.android.sample.sotuken.activity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.simpleline.SimpleLine;
import com.example.android.sample.sotuken.simpleline.SimpleLineDatabaseHelper;
import com.example.android.sample.sotuken.anketo.QuizMain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sho on 2017/10/11.
 */

public class EvaluationActivity extends AppCompatActivity {
    private Button check_button1 = null;
    private Button check_button2 = null;
    private Button quiz_button;
    private PlanDatabaseHelper helper;
    private SimpleLineDatabaseHelper helper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        check_button1 = (Button) findViewById(R.id.evaluation_button1);
        check_button2 = (Button) findViewById(R.id.evaluation_button2);
        quiz_button = (Button)findViewById(R.id.quiz_button);

        //データを受け取る
        //現在の時刻を取得
        DateFormat df01 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        DateFormat df02 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df03 = new SimpleDateFormat("yyyyMMdd");
        Date call_date = new Date(System.currentTimeMillis());
        final String date3 = df03.format(call_date);
        final String date2 = df02.format(call_date);//受けわたすためのデータ
        final String date = df01.format(call_date);//比較のためのデータ
        Log.d("date","date :"+date);
        //条件値の設定
        String[] date_check = {date};

        //データベースに接続
        helper = new PlanDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //今日の日付より前のデータの件数を数える
        long recodeCount = DatabaseUtils.queryNumEntries(db,PlanDatabaseHelper.TABLE_NAME,"day <= ?",date_check);
        Log.d("Count","recodeCount : " + recodeCount);
        //そしてなおかつフラグが1のデータの件数を数える
        long flagCount = DatabaseUtils.queryNumEntries(db,PlanDatabaseHelper.TABLE_NAME,"day <= ? AND flag = 1",date_check);
        Log.d("Count","FlagCount :"+flagCount);
        int achievement_final = 0;
        //達成率の計算
        if(recodeCount > 0) {
            double achievement = ((double) flagCount / (double) recodeCount) * 100;
            BigDecimal achievement_change01 = new BigDecimal(achievement);
            achievement_change01 = achievement_change01.setScale(0, BigDecimal.ROUND_HALF_UP);
            achievement_final = achievement_change01.intValue();
            Log.d("Achievement", "Achievement : " + achievement_final);
        }
        else {
            Log.d("Achievement","Achievement :"+achievement_final);
        }

        //数値を文字列に変換
        final String data_count = String.valueOf(recodeCount);
        final String flag = String.valueOf(flagCount);
        final String achievement_num = String.valueOf(achievement_final);


        check_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Evaluation.class);
                intent.putExtra("Date",date2);
                intent.putExtra("Data_num",data_count);
                intent.putExtra("Flag_num",flag);
                intent.putExtra("Achieve_num",achievement_num);
                startActivity(intent);
            }
        });

        helper2 = new SimpleLineDatabaseHelper(this);
        check_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db2 = helper2.getReadableDatabase();
                long recodeCount = DatabaseUtils.queryNumEntries(db2,SimpleLineDatabaseHelper.TABLE_NAME,null,null);
                SQLiteDatabase db = helper.getReadableDatabase();
                Log.d("recordCount",":"+recodeCount);
                /*
                if(recodeCount > 6){
                    Log.d("date",": "+date3);
                    int graph_date = Integer.parseInt(date3);
                    int graph_date01 = graph_date - 7;
                    String date_graph = String.valueOf(graph_date01);
                    Log.d("date_graph",": "+date_graph);
                    String[] params = {date_graph};
                    db2.delete(helper2.TABLE_NAME,"date <= ?",params);
                    Log.d("delete","削除されました");
                }
                */

                if (recodeCount >=1 ){
                    //Toast.makeText(EvaluationActivity.this, "データ数が少ないです。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), SimpleLine.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EvaluationActivity.this, "データ数が少ないです。", Toast.LENGTH_SHORT).show();
                }

            }
        });
        db.close();
        quiz_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db2 = helper2.getReadableDatabase();
                long recodeCount = DatabaseUtils.queryNumEntries(db2,SimpleLineDatabaseHelper.TABLE_NAME,null,null);
                if (recodeCount >=1 ){
                    //Toast.makeText(EvaluationActivity.this, "データ数が少ないです。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), QuizMain.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(EvaluationActivity.this, "データ数が少ないです。", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplication(), Check.class);
            startActivity(intent);
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }

    }
}
