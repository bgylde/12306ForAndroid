package com.bgylde.ticket;

import android.app.Application;

/**
 * Created by wangyan on 2019/1/7
 */
public class MainApplication extends Application {

    public static MainApplication application = null;

    public MainApplication() {
        super();
        application = this;
    }

    public static MainApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
