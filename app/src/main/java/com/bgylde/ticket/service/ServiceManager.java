package com.bgylde.ticket.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.bgylde.ticket.request.model.QueryTicketItemModel;

import java.util.List;

/**
 * Created by wangyan on 2019/1/7
 */
public class ServiceManager {

    private ServiceConnection serviceConnection;

    private IPollService pollService;

    public static ServiceManager getInstance() {
        return ServiceManagerImpl.instnce;
    }

    private static class ServiceManagerImpl {
        private static final ServiceManager instnce = new ServiceManager();
    }

    private ServiceManager() {}

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

    public void stopService(Context context) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PollService.class);
        context.getApplicationContext().stopService(intent);
    }

    public void startBuyTickets(List<QueryTicketItemModel> orderList) {
        if (orderList != null && orderList.size() > 0) {
            if (pollService != null) {
                pollService.startBuyTickets(orderList);
            }
        }
    }

    public void login(String username, String passwd) {
        if (pollService != null) {
            pollService.loginAccount(username, passwd);
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
