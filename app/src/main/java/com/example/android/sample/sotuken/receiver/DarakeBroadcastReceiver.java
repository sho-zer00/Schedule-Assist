package com.example.android.sample.sotuken.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.android.sample.sotuken.R;
import com.example.android.sample.sotuken.activity.CheckPlanSubActivity;
import com.example.android.sample.sotuken.activity.MainActivity;

/**
 * Created by sho on 2017/11/30.
 * 三日坊主防止のための工夫
 */

public class DarakeBroadcastReceiver extends BroadcastReceiver {

    Context context;
    @Override   // データを受信した
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.d("DarakeBroadcastReceiver","onReceive() pid=" + android.os.Process.myPid());
        int bid = intent.getIntExtra("intentId01",100);

        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, bid, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("大丈夫ですか？")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("だらけチェック")
                .setContentText("そろそろアプリを開きませんか？")
                // 音、バイブレート、LEDで通知
                .setDefaults(Notification.DEFAULT_ALL)
                // 通知をタップした時にActivityを立ち上げる
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        // 古い通知を削除
        notificationManager.cancelAll();
        // 通知
        notificationManager.notify(R.string.app_name, notification);
    }
}