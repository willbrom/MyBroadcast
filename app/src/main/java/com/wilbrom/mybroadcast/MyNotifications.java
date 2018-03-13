package com.wilbrom.mybroadcast;


import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class MyNotifications {

    private static final String CHANNEL_ID = "11";
    public static final int REQUEST_CODE_MAIN = 100;
    public static final int NOTIFICATION_ID = 22;

    public static void notify(Context context, String quote, String author) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(author)
                .setContentText(quote)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(createPendingIntent(context));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private static PendingIntent createPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                REQUEST_CODE_MAIN, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
