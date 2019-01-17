package com.bgylde.ticket.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.bgylde.ticket.http.CookiesManager;
import com.bgylde.ticket.utils.ConfigureManager;
import com.bgylde.ticket.utils.LogUtils;

/**
 * Created by wangyan on 2019/1/7
 */
public class PollService extends Service {

    private Binder binder = new Binder();

    private RequestThread requestThread = null;

    private Handler requestHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        CookiesManager.getInstance().initDbCookie();
        LogUtils.d("wy", "1 !!!!!!!!!!!!!!!!!!1");
        startQueryThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (requestHandler != null) {
            requestHandler = null;
        }

        if (requestThread != null) {
            requestThread.interrupt();
            requestThread = null;
        }
    }

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

    private class Binder extends android.os.Binder implements IPollService {
        @Override
        public void loginAccount(String userName, String passwd) {
            ConfigureManager.getInstance().setUsername(userName);
            ConfigureManager.getInstance().setAccountPwd(passwd);
            if (requestHandler != null) {
                requestHandler.sendEmptyMessage(RequestThread.USER_ACCOUNT_LOGIN);
            } else {
                requestHandler = requestThread.getHandler();
                requestHandler.sendEmptyMessage(RequestThread.USER_ACCOUNT_LOGIN);
            }
        }

        @Override
        public void startBuyTickets() {
            if (requestHandler != null) {
                requestHandler.sendEmptyMessage(RequestThread.START_BUY_TICKETS);
            } else {
                requestHandler = requestThread.getHandler();
                requestHandler.sendEmptyMessage(RequestThread.START_BUY_TICKETS);
            }
        }
    }
}
