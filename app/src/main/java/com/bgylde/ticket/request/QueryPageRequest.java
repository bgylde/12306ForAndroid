package com.bgylde.ticket.request;

import android.content.Context;

import com.bgylde.ticket.http.RetrofitHttpUtils;

/**
 * Created by wangyan on 2019/1/7
 */
public class QueryPageRequest extends AbsRquest {

    private static final String URI = "otn/leftTicket/init";

    private static final String REFERER = "https://kyfw.12306.cn/otn/login/init";

    @Override
    public String getUri() {
        return URI;
    }

    @Override
    protected String getReferUrl() {
        return REFERER;
    }

    @Override
    public void sendRequest(Context context) {
        RetrofitHttpUtils.sendByGet(context, getUri(), getHeaders());
    }
}
