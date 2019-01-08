package com.bgylde.ticket.request.utils;

import com.bgylde.ticket.utils.LogUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by wangyan on 2019/1/8
 */
public class GsonParser {

    private static final String TAG = "GsonParser";

    private Gson gson;

    private static GsonParser instance = null;

    private GsonParser() {
        gson = new Gson();
    }

    public static GsonParser getInstance() {
        if (instance == null) {
            synchronized (GsonParser.class) {
                if (instance == null) {
                    instance = new GsonParser();
                }
            }
        }

        return instance;
    }

    public <T> T parseModel(String json, Class<T> classOfT) {
        LogUtils.d(TAG, "Parse json: " + json);
        return gson.fromJson(json, classOfT);
    }
}
