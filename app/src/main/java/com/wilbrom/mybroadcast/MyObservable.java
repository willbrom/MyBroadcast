package com.wilbrom.mybroadcast;

import java.util.Observable;

public class MyObservable extends Observable implements MyIntentService.OnChangeDataListener {

    public MyObservable() {
        MyIntentService.attachListener(this);
    }

    @Override
    public void dataChanged(String data) {
        setChanged();
        notifyObservers(data);
    }
}
