package com.example.android.sample.sotuken;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sho on 2017/07/26.
 */

public class Plan extends AppCompatActivity {

    private PlanDatabaseHelper helper = null;
    private EditText txtDate = null;
    private EditText txtTime = null;
    private EditText txtPlan = null;

    private static final int bid2 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_layout);

        helper = new PlanDatabaseHelper(this);//ヘルパーの準備
        txtDate = (EditText)findViewById(R.id.txtDate);
        txtTime = (EditText)findViewById(R.id.txtTime);
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

    public void input_Button(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            String date = txtDate.getText().toString();
            String time = txtTime.getText().toString();
            cv.put("date",date);
            cv.put("time",time);
            cv.put("doing",txtPlan.getText().toString());
            cv.put("flag",0);
            db.insertOrThrow("plans",null,cv);
            Toast.makeText(this,"データの登録に成功しました",
                    Toast.LENGTH_SHORT).show();

            //アラーム機能を使うために取得した文字列を分割＆数字の型に変換
            String[] array_date = date.split("/",0);
            for(int i = 0;i<array_date.length;i++){
                Log.d("split","array_date["+i+"] = "+array_date[i]);
            }
            String[] array_time = time.split(":",0);
            for(int i = 0;i<array_time.length;i++){
                Log.d("split","array_time["+i+"] = "+array_time[i]);
            }


            //数値変換
            int year = Integer.parseInt(array_date[0]);
            int month = Integer.parseInt(array_date[1])-1;
            int day = Integer.parseInt(array_date[2]);
            int hour = Integer.parseInt(array_time[0]);
            int minute = Integer.parseInt(array_time[1]);
            //できているか確認のためのコード
            Log.d("number_check",""+year+","+month+","+day+","+hour+","+minute);
            //アラームに登録
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DATE,day);
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            //calendar.set(Calendar.AM_PM,timeZone);

            Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
            intent.putExtra("intentId", 2);
            PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);

            // アラームをセットする
            AlarmManager am = (AlarmManager)Plan.this.getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
            //Toast.makeText(getApplicationContext(), "ALARM ", Toast.LENGTH_SHORT).show();
            /*
            Intent intent =  new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            */
        }
        finally {
            db.close();
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