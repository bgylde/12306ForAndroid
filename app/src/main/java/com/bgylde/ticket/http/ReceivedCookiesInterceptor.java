package com.bgylde.ticket.http;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by wangyan on 2019/1/7
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private static final String RECEIVE_COOKIE_HEADER_NAME = "Set-Cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        List<String> headerList = originalResponse.headers(RECEIVE_COOKIE_HEADER_NAME);
        if (headerList != null && headerList.size() > 0) {
            CookiesManager.getInstance().insertOrUpdateCookies(headerList);
        }

        return originalResponse;
    }
}
