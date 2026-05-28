package com.lggyx.lgdemo08;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

public class MyBcReceiver1 extends BroadcastReceiver {

    private static final String TAG = "MyBcReceiver1";
    private static final String CHANNEL_ID = "brd";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: MyBcReceiver1 received broadcast");

        String msg = intent.getStringExtra("msg");
        Toast toast = Toast.makeText(context, "第1个接收器,接收到广播消息。", Toast.LENGTH_SHORT);
        toast.setGravity(android.view.Gravity.TOP, 0, 650);
        toast.show();

        sendNotification(context, msg);
    }

    private void sendNotification(Context context, String msg) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, context.getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("广播通知");
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bc)
            .setContentTitle(context.getString(R.string.notification_title1))
            .setContentText(msg)
            .setColor(context.getResources().getColor(R.color.colorPrimary))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build();

        manager.notify(1, notification);
    }
}
