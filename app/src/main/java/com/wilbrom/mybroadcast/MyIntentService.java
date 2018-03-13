package com.wilbrom.mybroadcast;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MyIntentService extends IntentService {

    public interface OnChangeDataListener {
        void dataChanged(String data);
    }

    private static OnChangeDataListener mListener;

    private static final String ACTION_GET_DATA = "com.wilbrom.mybroadcast.action.GET_DATA";
    private static final String ACTION_BAZ = "com.wilbrom.mybroadcast.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.wilbrom.mybroadcast.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.wilbrom.mybroadcast.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionGetData(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_GET_DATA);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void attachListener(Object o) {
        mListener = (OnChangeDataListener) o;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_DATA.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionGetData(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionGetData(String path, String param2) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String quotation = prefs.getString("key", "No Value");
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            if (sc.hasNext()) {
                quotation = sc.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("key", quotation);
        editor.apply();

        MyNotifications.notify(this, quotation, "Author");

        if (mListener != null)
            mListener.dataChanged(quotation);
    }

    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
