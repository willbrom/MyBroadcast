package com.wilbrom.mybroadcast;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String s = prefs.getString("key", "No value");
        displayQuote(s);

        MyObservable obs = new MyObservable();
        obs.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        final Object obj = o;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayQuote((String) obj);
            }
        });

        Log.d("TAG", (String) o);
    }

    private void displayQuote(String quote) {
        ((TextView) findViewById(R.id.textView)).setText(quote);
    }

    private Map<String , String> getQuoteData(String data) {
        Map<String, String> quoteData = new HashMap<>();
        String quote = null;
        String author = null;
        try {
            JSONObject rootObj = new JSONObject(data);
            quote = rootObj.getString("quoteText");
            author = rootObj.getString("quoteAuthor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        quoteData.put("quote", quote);
        quoteData.put("author", author);
        return quoteData;
    }
}
