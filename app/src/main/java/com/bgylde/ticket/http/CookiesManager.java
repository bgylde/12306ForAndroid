package com.bgylde.ticket.http;

import com.bgylde.ticket.utils.LogUtils;

import java.util.HashSet;
import java.util.List;

/**
 * Created by wangyan on 2019/1/7
 */
public class CookiesManager {

    private static final String TAG = "CookiesManager";

    private static CookiesManager instance = null;

    private HashSet<String> cookieHeaders = null;

    private CookiesManager() {
        cookieHeaders = new HashSet<>();
    }

    public static CookiesManager getInstance() {
        if (instance == null) {
            synchronized (CookiesManager.class) {
                if (instance == null) {
                    instance = new CookiesManager();
                }
            }
        }

        return instance;
    }

    public void insertOrUpdateCookies(List<String> cookiesHeaders) {
        if (cookiesHeaders != null && cookiesHeaders.size() > 0) {
            for (String header : cookiesHeaders) {
                LogUtils.d(TAG, header);
            }

            this.cookieHeaders.addAll(cookieHeaders);
        }
    }

    public HashSet<String> getCookieHeaders() {
        return cookieHeaders;
    }
}
