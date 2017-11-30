package com.example.android.sample.sotuken.activity;

import android.app.AlarmManager;
import android.app.DialogFragment;
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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sample.sotuken.receiver.AlarmBroadcastReceiver;
import com.example.android.sample.sotuken.AlarmDatabaseHelper;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.PlanDateFragment;
import com.example.android.sample.sotuken.PlanDoFragment;
import com.example.android.sample.sotuken.PlanTimeFragment;
import com.example.android.sample.sotuken.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sho on 2017/07/26.
 */

public class Plan extends AppCompatActivity {

    private PlanDatabaseHelper helper = null;
    private AlarmDatabaseHelper helper2 = null;
    private TextView txtDate = null;
    private TextView txtTime = null;
    private EditText txtPlan = null;

    private static final int bid2 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_layout);

        helper = new PlanDatabaseHelper(this);//ヘルパーの準備
        helper2 = new AlarmDatabaseHelper(this);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtTime = (TextView)findViewById(R.id.txtTime);
        txtPlan = (EditText)findViewById(R.id.txtPlan);
    }

    public void date_onClick(View view) {
        DialogFragment dialog = new PlanDateFragment();
        dialog.show(getFragmentManager(), "dialog_date");
    }

    public void time_onClick(View view){
        DialogFragment dialog = new PlanTimeFragment();
        dialog.show(getFragmentManager(), "dialog_time");
    }

    public void do_Button(View view){
        DialogFragment dialog = new PlanDoFragment();
        dialog.show(getFragmentManager(), "dialog_radio");
    }

    public void input_Button(View view) {
        //今日の日付を取得するプログラムを書く
        SQLiteDatabase db = helper.getWritableDatabase();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        String alarm_data = date + " " + time;
        Log.d("alarm_data", alarm_data);
        try {
            ContentValues cv = new ContentValues();
            cv.put("date", date);
            cv.put("time", time);
            cv.put("day",alarm_data);
            cv.put("doing", txtPlan.getText().toString());
            cv.put("flag", 0);
            //ここに今の時間を取得してその時間より表示されている時間が早ければ登録しない
            //アラームデータベースに登録する時間そして時間比較のための値を得る
            if(date.length()==0 || time.length()==0 || txtPlan.getText().toString().length() == 0) {
                Toast.makeText(this, "値は全部入れてください",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                db.insertOrThrow("plans", null, cv);
                Toast.makeText(this, "データの登録に成功しました", Toast.LENGTH_SHORT).show();
                SQLiteDatabase db2 = helper2.getWritableDatabase();
                ContentValues cv2 = new ContentValues();
                cv2.put("date", alarm_data);
                db2.insertOrThrow("alarms", null, cv2);
                Log.d("log", "通知データ登録に成功しました");
                db2.close();
            }
        } finally {
            db.close();
        }
        SQLiteDatabase db3 = helper2.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_time = new Date(System.currentTimeMillis());
        String date_0 = df.format(call_time);
        final String[] params = {date_0};
        db3.delete(helper2.TABLE_NAME,"date <= ?",params);
        try{
            long recodeCount = DatabaseUtils.queryNumEntries(db3,AlarmDatabaseHelper.TABLE_NAME,null,null);
            Log.d("Count","recodeCount : " + recodeCount);
            if(recodeCount > 0 ) {
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

                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                intent.putExtra("intentId", 2);
                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);

                // アラームをセットする
                AlarmManager am = (AlarmManager) Plan.this.getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);

                //Toast.makeText(getApplicationContext(), "ALARM ", Toast.LENGTH_SHORT).show();
                Log.d("tuuti","通知機能は使えます");
                //Intent intent =  new Intent(getApplication(), MainActivity.class);
            }
            else {
                Log.d("tuuti","通知機能は使えません");
            }
            //Intent intent =  new Intent(getApplication(), MainActivity.class);
            //startActivity(intent);

        }
        finally {
            db3.close();
        }



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            return super.onKeyDown(keyCode, event);
        } else {
            return false;
        }
    }


}