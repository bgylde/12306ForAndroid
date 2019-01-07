package com.bgylde.ticket.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by wangyan on 2019/1/7
 */
public class BindServiceUtils {

    private ServiceConnection serviceConnection;

    private IPollService pollService;

    public void bindService(Context context) {
        if (context == null) {
            return;
        }

        serviceConnection = new ServiceConnection();
        context.getApplicationContext().bindService(new Intent(context, PollService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

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

    public void startQuery() {
        if (pollService != null) {
            pollService.startRequest();
        }
    }

    public void pauseQuery() {
        if (pollService != null) {
            pollService.pauseRequest();
        }
    }

    public void stopQuery() {
        if (pollService != null) {
            pollService.stopRequest();
        }
    }

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
