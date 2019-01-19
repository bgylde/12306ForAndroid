package com.bgylde.ticket.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.bgylde.ticket.R;
import com.bgylde.ticket.http.CookiesManager;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.ui.MainQueryActivity;
import com.bgylde.ticket.utils.ConfigPreference;
import com.bgylde.ticket.utils.ConfigureManager;

import java.util.List;

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
        startQueryThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification("开始抢票", 0);
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

    private void showNotification(String state, int progress) {
        Intent  intent = new Intent(this, MainQueryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, PendingIntent.FLAG_UPDATE_CURRENT, intent,0);
        NotificationManager manger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(state)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setProgress(100, progress, true)
                .build();

        startForeground(1, notification);
    }

    private class Binder extends android.os.Binder implements IPollService {
        @Override
        public void loginAccount(String userName, String passwd) {
            ConfigureManager.getInstance().setUsername(userName);
            ConfigureManager.getInstance().setAccountPwd(passwd);
            ConfigPreference.updateUserInfo(PollService.this, userName, passwd);
            if (requestHandler != null) {
                requestHandler.sendEmptyMessage(RequestThread.USER_ACCOUNT_LOGIN);
            } else {
                requestHandler = requestThread.getHandler();
                requestHandler.sendEmptyMessage(RequestThread.USER_ACCOUNT_LOGIN);
            }
        }

        @Override
        public void startBuyTickets(List<QueryTicketItemModel> orderList) {
            if (requestHandler == null) {
                requestHandler = requestThread.getHandler();
            }

            Message msg = requestHandler.obtainMessage();
            msg.what = RequestThread.START_BUY_TICKETS;
            msg.obj = orderList;
            requestHandler.sendMessage(msg);
        }
    }
}
