package com.bgylde.ticket.request.utils;

import android.content.Context;

import java.util.Map;

/**
 * Created by wangyan on 2019/1/7
 */
public interface IRequest {

    String getUri();

    Map<String, Object> getHeaders();

    void sendRequest(Context context);
}
