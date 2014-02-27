package com.aeliseev.androwatch;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;

/**
 * Created by AEliseev on 21.02.2014
 */
public abstract class SingletonService extends Service {

    public final static String INTENT_DISCRIMINATOR = "intentDisc";

    public final static String EXTRA_CALLBACK_KEY = "callbackKey";
    public final static int CALLBACK_SUCCESS_RESPONSE = 0;
    public final static int CALLBACK_FAILURE_RESPONSE = 1;

    class MyBinder extends Binder {
        SingletonService getService() {
            return SingletonService.this;
        }
    }

    private final IBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        return START_NOT_STICKY;
    }

    public void processCallback(Bundle extras, Bundle callbackData) {
        ResultReceiver callback = (ResultReceiver) extras.get(EXTRA_CALLBACK_KEY);
        callback.send(CALLBACK_SUCCESS_RESPONSE, callbackData);
    }

    public abstract void onHandleIntent(Intent intent);
}
