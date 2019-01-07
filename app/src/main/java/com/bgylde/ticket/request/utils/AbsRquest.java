package com.bgylde.ticket.request.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyan on 2019/1/7
 */
public abstract class AbsRquest implements IRequest {

    private static final String DEFAULT_HOST = "kyfw.12306.cn";

    private Map<String, Object> headers = new HashMap<>();

    public Map<String, Object> getHeaders() {
        headers.put("Host", DEFAULT_HOST);
        headers.put("Referer", getReferUrl());

        return headers;
    }

    protected abstract String getReferUrl();
}
