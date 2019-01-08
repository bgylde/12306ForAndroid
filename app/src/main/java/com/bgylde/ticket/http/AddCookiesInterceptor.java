package com.bgylde.ticket.http;

import com.bgylde.ticket.utils.LogUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangyan on 2019/1/7
 */
public class AddCookiesInterceptor implements Interceptor {

    private static final String TAG = "AddCookiesInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = CookiesManager.getInstance().getCookieHeaders();
        LogUtils.d(TAG, "cookies size: " + cookies.size());
        for (String cookie : cookies) {
            LogUtils.d(TAG, cookie);
            builder.addHeader("Cookie", cookie);
        }

        return chain.proceed(builder.build());
    }
}
