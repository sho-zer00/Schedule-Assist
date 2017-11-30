package com.example.android.sample.sotuken.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sample.sotuken.receiver.AlarmBroadcastReceiver;
import com.example.android.sample.sotuken.AlarmDatabaseHelper;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.simpleline.SimpleLineDatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sho on 2017/10/11. 評価を数値だけで表現するクラス
 */

public class Evaluation extends AppCompatActivity {

    private SimpleLineDatabaseHelper helper;
    private PlanDatabaseHelper helper2;
    private AlarmDatabaseHelper helper3;
    private static final int bid2 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_layout);

        //レイアウトの初期化
        final TextView dateView = (TextView)findViewById(R.id.Date);
        TextView textView1 = (TextView)findViewById(R.id.Data);
        final TextView data_kazu = (TextView)findViewById(R.id.Data_kazu);
        TextView textView2 = (TextView)findViewById(R.id.Flag);
        final TextView flag_kazu = (TextView)findViewById(R.id.Flag_kazu);
        TextView textView3 = (TextView)findViewById(R.id.Tasseiritu);
        final TextView achieve_check = (TextView)findViewById(R.id.do_check);
        Button button = (Button)findViewById(R.id.graph_input) ;

        Intent intent = getIntent();
        String date = intent.getStringExtra("Date");
        String data_count = intent.getStringExtra("Data_num");
        String flag = intent.getStringExtra("Flag_num");
        String achievement_num = intent.getStringExtra("Achieve_num");
        int a = Integer.parseInt(data_count);
        int b = Integer.parseInt(flag);
        int c = Integer.parseInt(achievement_num);

        dateView.setText(date);
        data_kazu.setText(data_count);
        flag_kazu.setText(flag);
        achieve_check.setText(achievement_num);

        Log.d("Log","a = "+a+", b = "+b+", c = "+c);
        helper2 = new PlanDatabaseHelper(this);
        helper3 = new AlarmDatabaseHelper(this);

        //if文でrecodeCountが0でない時にデータベースに書き込む
        helper = new SimpleLineDatabaseHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //過去のデータの削除
                //現在の時刻を取得
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date call_time = new Date(System.currentTimeMillis());
                String date000 = df.format(call_time);
                final String[] params = {date000};

                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();
                    String date01 = dateView.getText().toString();
                    String[] array_date = date01.split("/",0);
                    for(int i = 0;i<array_date.length;i++){
                        Log.d("split","array_date["+i+"] = "+array_date[i]);
                    }
                    String date02 = array_date[0]+array_date[1]+array_date[2] ;
                    Log.d("split","date02 = "+date02);
                    //int型に変える
                    int date = Integer.parseInt(date02);
                    String record_count = data_kazu.getText().toString();
                    String flag_count = flag_kazu.getText().toString();
                    String achieve_count = achieve_check.getText().toString();
                    int record = Integer.parseInt(record_count);
                    int flag = Integer.parseInt(flag_count);
                    int achieve = Integer.parseInt(achieve_count);

                    if (record > 4) {
                        values.put("date", date);
                        values.put("record_count", record);
                        values.put("flag_count", flag);
                        values.put("achieve", achieve);
                        values.put("day",date000);
                        db.insertOrThrow(SimpleLineDatabaseHelper.TABLE_NAME, null, values);
                        Log.d("create", "登録できました");
                        Toast.makeText(getApplicationContext(), "登録しました。", Toast.LENGTH_SHORT).show();
                        // /todoリストのデータベースに接続し削除する
                        SQLiteDatabase db2 = helper2.getWritableDatabase();
                        db2.delete(helper2.TABLE_NAME,"day <= ?",params);
                        db2.close();
                        Intent intent2 = new Intent(getApplication(),EvaluationActivity.class);
                        startActivity(intent2);
                    }
                    else if(record < 5) {
                        Toast.makeText(getApplicationContext(), "データ数が少なすぎです。", Toast.LENGTH_SHORT).show();
                        Log.d("no", "登録できませんでした");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "登録できませんでした。", Toast.LENGTH_SHORT).show();
                        Log.d("no", "登録できませんでした");
                    }
                }finally {
                    db.close();
                }



                //アラームのデータも削除
                SQLiteDatabase db3= helper3.getWritableDatabase();
                db3.delete(helper3.TABLE_NAME,"date <= ?",params);
                long recodeCount = DatabaseUtils.queryNumEntries(db3,AlarmDatabaseHelper.TABLE_NAME,null,null);
                if(recodeCount > 0) {
                    Cursor cs = db3.query(AlarmDatabaseHelper.TABLE_NAME, new String[]{"min(date)"}, null, null, null, null, null);
                    cs.moveToFirst();
                    String date01 = cs.getString(0);
                    //アラーム機能を使うために取得した文字列を分割＆数字の型に変換
                    String[] array_date01 = date01.split(" ", 0);
                    for (int i = 0; i < array_date01.length; i++) {
                        Log.d("split", "array_date[" + i + "] = " + array_date01[i]);

                    }
                    String[] array_date = array_date01[0].split("/", 0);
                    for (int i = 0; i < array_date.length; i++) {
                        Log.d("split", "array_date[" + i + "] = " + array_date[i]);
                    }
                    String[] array_time = array_date01[1].split(":", 0);
                    for (int i = 0; i < array_time.length; i++) {
                        Log.d("split", "array_time[" + i + "] = " + array_time[i]);
                    }
                    //数値変換
                    int year = Integer.parseInt(array_date[0]);
                    int month = Integer.parseInt(array_date[1]) - 1;
                    int day = Integer.parseInt(array_date[2]);
                    int hour = Integer.parseInt(array_time[0]);
                    int minute = Integer.parseInt(array_time[1]);
                    //できているか確認のためのコード
                    Log.d("number_check", "" + year + "," + month + "," + day + "," + hour + "," + minute);
                    //アラームに登録
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DATE, day);
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    //calendar.set(Calendar.AM_PM,timeZone);

                    // アラームをセットする
                    AlarmManager am = (AlarmManager) Evaluation.this.getSystemService(ALARM_SERVICE);
                    for (int requestCode = 0; requestCode < bid2; requestCode++) {
                        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);

                        pendingIntent.cancel();
                        am.cancel(pendingIntent);
                    }
                    Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                    intent.putExtra("intentId", 100);
                    PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
                    //Toast.makeText(getApplicationContext(), "ALARM ", Toast.LENGTH_SHORT).show();
                    Log.d("alarm_check", date01 + "が登録されました");
                }
                else{
                    Log.d("alarm_check", "アラームは登録できませんでした");
                }

            }
        });

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
