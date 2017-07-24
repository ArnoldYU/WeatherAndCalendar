package com.example.arnold.weather.fragement.calendarviewall;

/**
 * Created by Arnold on 2017/7/22.
 */
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.arnold.weather.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static int NOTIFY_ID = 1000;


    @Override

    public void onReceive(Context context, Intent intent) {
        System.out.println("fuck");
        Toast.makeText(context,"Boot complete", Toast.LENGTH_LONG).show();
        throw new UnsupportedOperationException("Not yet implemented");
      /*  NotificationManager notifyMgr= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("http://www.google.com"));
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.a100)
                .setTicker("Hello")
                .setContentTitle("您即将有事情要做")
                .setContentText("做好准备")
                .setContentIntent(pi)
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notifyMgr.notify(NOTIFY_ID, notification);*/

    }
}