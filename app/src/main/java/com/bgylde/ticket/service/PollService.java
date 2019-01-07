package com.bgylde.ticket.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bgylde.ticket.MainApplication;

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
    public void onDestroy() {
        super.onDestroy();
        if (requestThread != null) {
            requestThread = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class Binder extends android.os.Binder implements IPollService {

        @Override
        public void startRequest() {
            if (requestThread == null || !requestThread.isAlive()) {
                requestThread = new RequestThread(MainApplication.getApplication().getApplicationContext());
                requestThread.start();
            } else {

            }
        }

        @Override
        public void pauseRequest() {

        }

        @Override
        public void stopRequest() {

        }
    }
}
