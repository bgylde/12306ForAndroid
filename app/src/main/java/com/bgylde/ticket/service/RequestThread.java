package com.bgylde.ticket.service;

import android.content.Context;
import android.content.ContextWrapper;

import com.bgylde.ticket.request.RequestManaager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wangyan on 2019/1/7
 */
public class RequestThread extends Thread {

    private Context context;

    private AtomicBoolean isInited = new AtomicBoolean(false);

    RequestThread(Context context) {
        if (context instanceof ContextWrapper) {
            this.context = context.getApplicationContext();
        } else {
            this.context = context;
        }
    }

    @Override
    public void run() {
        if (isInited.compareAndSet(false, true)) {
            initQuery();
        }


    }

    private void initQuery() {
        RequestManaager.getInstance().queryInitPage(context);
    }
}
