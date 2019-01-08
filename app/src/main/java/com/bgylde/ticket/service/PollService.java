package com.bgylde.ticket.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by wangyan on 2019/1/7
 */
public class PollService extends Service {

    private Binder binder = new Binder();

    private RequestThread requestThread = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startQueryThread();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (requestThread != null) {
            requestThread.interrupt();
            requestThread = null;
        }
    }

    @Nullable
    @Deprecated
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void startQueryThread() {
        if (requestThread == null || !requestThread.isAlive() || requestThread.isInterrupted()) {
            requestThread = new RequestThread(this);
            requestThread.start();
        }
    }

    @Deprecated
    private class Binder extends android.os.Binder implements IPollService {

        @Override
        public void startRequest() {
            startQueryThread();
        }

        @Override
        public void pauseRequest() {

        }

        @Override
        public void stopRequest() {

        }
    }
}
