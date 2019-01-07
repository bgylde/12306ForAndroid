package com.bgylde.ticket.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bgylde.ticket.MainApplication;
import com.bgylde.ticket.R;
import com.bgylde.ticket.utils.DialogUtils;

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
        if (requestThread == null || !requestThread.isAlive()) {
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
