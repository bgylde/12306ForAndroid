package com.bgylde.ticket.utils;

import android.util.Log;

/**
 * Created by wangyan on 2019/1/7
 */
public class LogUtils {

    private static final String DEFAULT_TAG = "LogMonitor";

    public static void i(String tag, String message) {
        if (isShowLog()) {
            Log.i(tag, " [" + message + "]");
        }
    }

    public static void i(String message) {
        if (isShowLog()) {
            Log.i(DEFAULT_TAG, message);
        }
    }

    public static void d(String tag, String message) {
        if (isShowLog()) {
            Log.d(tag, " [" + message + "]");
        }
    }

    public static void d(String message) {
        if (isShowLog()) {
            Log.d(DEFAULT_TAG, message);
        }
    }

    public static void w(String tag, String message) {
        if (isShowLog()) {
            Log.w(tag, " [" + message + "]");
        }
    }

    public static void w(String message) {
        if (isShowLog()) {
            Log.w(DEFAULT_TAG, message);
        }
    }

    public static void e(Throwable e) {
        if (isShowLog()) {
            Log.e(DEFAULT_TAG, " [" + Log.getStackTraceString(e) + "]");
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (isShowLog()) {
            Log.e(tag, " [" + message + "]", e);
        }
    }

    public static void e(String tag, Throwable e) {
        if (isShowLog()) {
            Log.e(tag, " [" + Log.getStackTraceString(e) + "]", e);
        }
    }

    private static boolean isShowLog() {
        return Constants.DEBUG;
    }
}
