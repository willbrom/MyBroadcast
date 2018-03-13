package com.wilbrom.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

//        MyIntentService.startActionGetData(context, "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en", null);
        MyFirebaseJobDispatcher.scheduleJob(context);
    }
}
