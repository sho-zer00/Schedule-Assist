package com.example.android.sample.sotuken.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.android.sample.sotuken.receiver.DarakeBroadcastReceiver;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.chat.ChatMain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private AlarmDatabaseHelper helper;
    private PlanDatabaseHelper helper2;
    private static final int bid2 = 30;
    private static final int bid3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        Button button1 = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Plan.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),Check.class);
                startActivity(intent);
            }
        });

        //データベースに問い合わせて、現在時刻より前の時間のアラームのデータを削除し、現在時刻以上の時間を登録
        helper = new AlarmDatabaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_time = new Date(System.currentTimeMillis());
        String date = df.format(call_time);
        final String[] params = {date};
        db.delete(helper.TABLE_NAME,"date <= ?",params);
        try{
            long recodeCount = DatabaseUtils.queryNumEntries(db,AlarmDatabaseHelper.TABLE_NAME,null,null);
            Log.d("Count","recodeCount : "+recodeCount);
            if(recodeCount > 0){
                Cursor cs = db.query(AlarmDatabaseHelper.TABLE_NAME, new String[]{"min(date)"}, null, null, null, null, null);
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
                AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
                //過去に必要なくなったアラームを削除
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
                Log.d("alarm_main", date01 + "が登録されました");
            }
            else {
                Log.d("data","アラームに登録されるデータはありません");
            }
        }finally {
            db.close();
        }

        //ここに数日経ったらToastを出す
        helper2 = new PlanDatabaseHelper(this);
        SQLiteDatabase db2 = helper2.getReadableDatabase();
        DateFormat df01 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_date = new Date(System.currentTimeMillis());
        final String date02 = df01.format(call_date);//比較のためのデータ
        String[] date_check = {date02};

        long recodeCount = DatabaseUtils.queryNumEntries(db2, PlanDatabaseHelper.TABLE_NAME,"day <= ?",date_check);
        if (recodeCount > 4){
            Toast.makeText(MainActivity.this, "そろそろ達成度データを\n登録しましょう", Toast.LENGTH_SHORT).show();
        }
        db2.close();
        darake();

    }

    //三日坊主対策
    void darake(){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_time = new Date(System.currentTimeMillis());
        String date = df.format(call_time);
        String[] array_date01 = date.split(" ", 0);
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
        int hour = Integer.parseInt(array_time[0])+5;
        //int hour = 26;
        int minute = Integer.parseInt(array_time[1]);

        //できているか確認のためのコード
        Log.d("number_check", "" + year + "," + month + "," + day + "," + hour + "," + minute);
        //アラームに登録
        //一応三日後を想定だが今はしない
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
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);

        //過去に必要なくなったアラームを削除
        for (int requestCode = 0; requestCode < bid3; requestCode++) {
            Intent intent02 = new Intent(getApplicationContext(), DarakeBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent02, 0);
            pendingIntent.cancel();
            am.cancel(pendingIntent);
        }

        //定期感覚でセットしていく
        Intent intent02 = new Intent(getApplicationContext(), DarakeBroadcastReceiver.class);
        intent02.putExtra("intentId01", 100);
        PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid3, intent02, 0);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        //Toast.makeText(getApplicationContext(), "ALARM ", Toast.LENGTH_SHORT).show();
        Log.d("darake_main", "" + year + "/" + month + "/" + day + " " + hour + ":" + minute);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.check:
                Intent intent1 = new Intent(getApplication(),Check.class);
                startActivity(intent1);
                break;
            case R.id.evaluation:
                Intent intent2 = new Intent(getApplication(),EvaluationActivity.class);
                startActivity(intent2);
                break;
            case R.id.chat:
                Intent intent3 = new Intent(getApplication(),ChatMain.class);
                startActivity(intent3);
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
