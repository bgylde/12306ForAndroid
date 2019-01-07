package com.bgylde.ticket.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

/**
 * Created by wangyan on 2019/1/7
 */
public class ServiceManager {

    private ServiceConnection serviceConnection;

    private IPollService pollService;

    @Deprecated
    public void bindService(Context context) {
        if (context == null) {
            return;
        }

        serviceConnection = new ServiceConnection();
        context.getApplicationContext().bindService(new Intent(context, PollService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Deprecated
    public void unBindService(Context context) {
        if (context == null) {
            return;
        }

        if (serviceConnection != null) {
            context.getApplicationContext().unbindService(serviceConnection);
            serviceConnection = null;
            pollService = null;
        }
    }

    public void startService(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PollService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.getApplicationContext().startForegroundService(intent);
        } else {
            context.getApplicationContext().startService(intent);
        }
    }

    @Deprecated
    public void stopService(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PollService.class);
        context.getApplicationContext().stopService(intent);
    }

    @Deprecated
    public void startQuery() {
        if (pollService != null) {
            pollService.startRequest();
        }
    }

    @Deprecated
    public void pauseQuery() {
        if (pollService != null) {
            pollService.pauseRequest();
        }
    }

    @Deprecated
    public void stopQuery() {
        if (pollService != null) {
            pollService.stopRequest();
        }
    }

    @Deprecated
    private class ServiceConnection implements android.content.ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pollService = (IPollService)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            pollService = null;
        }
    }
}
