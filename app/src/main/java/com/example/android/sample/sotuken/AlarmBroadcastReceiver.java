package com.example.android.sample.sotuken;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//いわゆるAndroidの警告とかの通知
//NotificationManagerで通知機能をつける
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    Context context;

    @Override   // データを受信した
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        Log.d("AlarmBroadcastReceiver","onReceive() pid=" + android.os.Process.myPid());

        int bid = intent.getIntExtra("intentId",0);
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date call_time = new Date(System.currentTimeMillis());
        String date = df.format(call_time);
        Intent intent2 = new Intent(context, CheckPlanSubActivity.class);
        intent2.putExtra("TIME_DATA",date);
        //PendingIntentはintentを予約したタイミングで表すもの
        //AlarmManagerやNotification等で何かイベントを起こしたいときに指定する.
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, bid, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Check Schdule")
                .setContentText("あなたが指定したスケジュールの時間になりました")
                // 音、バイブレート、LEDで通知
                .setDefaults(Notification.DEFAULT_ALL)
                // 通知をタップした時にMainActivityを立ち上げる
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        // 古い通知を削除
        //notificationManager.cancelAll();
        // 通知
        notificationManager.notify(R.string.app_name, notification);
    }
}