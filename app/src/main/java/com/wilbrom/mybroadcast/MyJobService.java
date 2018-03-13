package com.wilbrom.mybroadcast;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    public static final String TAG = MyJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "onStartJob called!");
        MyIntentService.startActionGetData(this, "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en", null);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "onStopJob called!");
        return false;
    }
}
