package com.bgylde.ticket.http;

import com.bgylde.ticket.database.CookieDbManager;
import com.bgylde.ticket.database.CookieModel;
import com.bgylde.ticket.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyan on 2019/1/7
 */
public class CookiesManager {

    private static final String TAG = "CookiesManager";

    private static CookiesManager instance = null;

    private HashSet<String> cookieHeaders = null;

    private HashMap<String, String> cookiesMap = new HashMap<>();

    private CookiesManager() {
        cookieHeaders = new HashSet<>();
    }

    public void initDbCookie() {
        if (cookiesMap.size() > 0) {
            return;
        }

        List<CookieModel> cookieModels = CookieDbManager.getInstance().queryCookieModel();
        if (cookieModels != null && cookieModels.size() > 0) {
            for (CookieModel model : cookieModels) {
                LogUtils.d(TAG, "title: " + model.getTitle() + " cookie: " + model.getCookie());
                cookiesMap.put(model.getTitle(), model.getCookie());
            }
        }
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
            CookieModel model = new CookieModel();
            for (String header : cookiesHeaders) {
                LogUtils.d(TAG, header);
                String[] result = header.split("=");
                if (result.length > 0) {
                    cookiesMap.put(result[0], header);
                    model.setCookieId(header.hashCode());
                    model.setTitle(result[0]);
                    model.setCookie(header);
                    CookieDbManager.getInstance().insertOrReplace(model);
                }
            }
        }
    }

    public HashSet<String> getCookieHeaders() {
        this.cookieHeaders.clear();
        for (Map.Entry<String, String> entry : cookiesMap.entrySet()) {
            cookieHeaders.add(entry.getValue());
        }

        return cookieHeaders;
    }
}
