package com.example.android.sample.sotuken.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sample.sotuken.receiver.AlarmBroadcastReceiver;
import com.example.android.sample.sotuken.AlarmDatabaseHelper;
import com.example.android.sample.sotuken.CheckPlanAdapter;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.PlanListItem;
import com.example.android.sample.sotuken.R;

import java.util.ArrayList;

/**
 * Created by sho on 2017/10/04.
 */

public class CheckPlanDateActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private PlanDatabaseHelper helper;
    private AlarmDatabaseHelper helper2;
    private static final int bid2 = 100;
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
        helper2 = new AlarmDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] cols = {"_id","date","time","day","doing","flag"};
        Cursor cs = db.query(PlanDatabaseHelper.TABLE_NAME,cols,"date = ?",date_check,null,null,"day asc");

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
                        SQLiteDatabase db2 = helper2.getWritableDatabase();
                        try{
                            db.delete(helper.TABLE_NAME, "_id = "+ id ,null);
                            String check = data.get(position).getTime();
                            String[]params = {check};
                            db2.delete(helper2.TABLE_NAME,"date = ? ",params);
                            data.remove(id);
                            adapter.notifyDataSetChanged();

                        }finally {
                            db.close();
                        }
                        long recodeCount = DatabaseUtils.queryNumEntries(db2,AlarmDatabaseHelper.TABLE_NAME,null,null);
                        if(recodeCount > 0) {
                            Cursor cs = db2.query(AlarmDatabaseHelper.TABLE_NAME, new String[]{"min(date)"}, null, null, null, null, null);
                            cs.moveToFirst();
                            String date = cs.getString(0);
                            //アラーム機能を使うために取得した文字列を分割＆数字の型に変換
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
                            AlarmManager am = (AlarmManager) CheckPlanDateActivity.this.getSystemService(ALARM_SERVICE);
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
                            Log.d("alarm_check", date + "が登録されました");
                        }
                        else{
                            Log.d("alarm_no","アラームデータはありません");

                        }
                        Toast.makeText(CheckPlanDateActivity.this,"データを削除しました。",Toast.LENGTH_SHORT).show();
                        Intent intent2 =  new Intent(getApplication(), CheckPlanActivity.class);
                        startActivity(intent2);
                        return false;

                    }
                }
        );

        cs.close();
    }
}
