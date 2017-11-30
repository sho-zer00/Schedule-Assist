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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sample.sotuken.receiver.AlarmBroadcastReceiver;
import com.example.android.sample.sotuken.AlarmDatabaseHelper;
import com.example.android.sample.sotuken.CheckDataAdapter;
import com.example.android.sample.sotuken.CheckPlanAdapter;
import com.example.android.sample.sotuken.CheckPlanDateSort;
import com.example.android.sample.sotuken.PlanDatabaseHelper;
import com.example.android.sample.sotuken.PlanListItem;
import com.example.android.sample.sotuken.R;

import java.util.ArrayList;

/**
 * Created by sho on 2017/08/02.
 * CheckPlanのリストを表示するためのアクテビティ
 */

public class CheckPlanActivity extends AppCompatActivity {

    private CheckPlanAdapter adapter;
    private CheckDataAdapter check;
    private PlanDatabaseHelper helper;
    private AlarmDatabaseHelper helper2;
    private static final int bid2 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planlist_layout);


        helper = new PlanDatabaseHelper(this);
        helper2 = new AlarmDatabaseHelper(this);
        //データベースに接続
        check = new CheckDataAdapter(this);
        Cursor c = check.getAllList();


        //データベースの中から項目を取り出しアダプターにセットする
        final ArrayList<PlanListItem> data= new ArrayList<>();

        if(c.moveToFirst()){
            do{

                PlanListItem item = new PlanListItem();
                item.setId(c.getInt(c.getColumnIndex("_id")));
                item.setTitle(c.getString(c.getColumnIndex("doing")));
                item.setTime(c.getString(c.getColumnIndex("date"))+" "+c.getString(c.getColumnIndex("time")));
                data.add(item);
            }while (c.moveToNext());
        }


        adapter = new CheckPlanAdapter(this,data,R.layout.planlist_item);
        final ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        adapter.notifyDataSetChanged();



        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id) {


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
                            AlarmManager am = (AlarmManager) CheckPlanActivity.this.getSystemService(ALARM_SERVICE);
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
                        Toast.makeText(CheckPlanActivity.this,"データを削除しました。",Toast.LENGTH_SHORT).show();
                        Intent intent2 =  new Intent(getApplication(), CheckPlanActivity.class);
                        startActivity(intent2);
                        return false;
                    }
                }
        );

        c.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plan_list_get, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all:
                break;
            case R.id.date_sort:
                Intent intent = new Intent(getApplication(),CheckPlanDateSort.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplication(),Check.class);
            startActivity(intent);
            return super.onKeyDown(keyCode,event);
        }
        else{
            return false;
        }
    }

}
