package com.bgylde.ticket.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bgylde.ticket.utils.LogUtils;
import com.bgylde.ticket.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangyan on 2019/1/7
 */
public class RetrofitHttpUtils {

    private static final String TAG = "RetrofitHttpUtils";

    private static final int DEFAULT_CONN_TIMEOUT = 3;

    private static final int DEFAULT_READ_TIMEOUT = 3;

    private static final int DEFAULT_WRITE_TIMEOUT = 3;

    private static final String BASE_URL = "https://kyfw.12306.cn";

    private static IRetrofitService service = null;

    public static boolean sendByGet(Context context, String url, Map<String, Object> headers) {
        IRetrofitService service = getRetrofitService(context);

        //Call<ResponseBody> repoCall = service.sendByGet(url);
        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            repoCall = service.sendByGet(url);
        } else {
            repoCall = service.sendByGet(url, headers);
        }

        retrofit2.Response<ResponseBody> response = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                return true;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (response != null) {
                ResponseBody body = response.body();
                if (body != null) {
                    body.close();
                }
            }
        }

        return false;
    }

    public static boolean sendByPost(Context context, String url, Map<String, String> fields, Map<String, Object> headers) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            repoCall = service.sendByPost(url, fields);
        } else {
            repoCall = service.sendByPost(url, fields, headers);
        }

        retrofit2.Response<ResponseBody> response = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                return true;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (response != null) {
                ResponseBody body = response.body();
                if (body != null) {
                    body.close();
                }
            }
        }

        return false;
    }

    private static IRetrofitService getRetrofitService(Context context) {
        if (service == null) {
            synchronized (RetrofitHttpUtils.class) {
                if (service == null) {
                    Retrofit retrofit = buildRetrofitRequest(context, BASE_URL);
                    service = retrofit.create(IRetrofitService.class);
                }
            }
        }

        return service;
    }

    private static Retrofit buildRetrofitRequest(Context context, String baseUrl) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                try {
                    if (message.contains("Content-Type: multipart/form-data")) {
                        Reader reader = new StringReader(message);
                        BufferedReader br = new BufferedReader(reader);
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            LogUtils.d(TAG, line);
                            if (line.contains("Content-Length")) {
                                LogUtils.d(TAG, "Ignore file content.");
                                break;
                            }
                        }
                    } else {
                        LogUtils.d(TAG, message);
                    }

                } catch (Exception | Error e) {
                    LogUtils.e(TAG, e.getMessage(), e);
                }
            }
        });

        Interceptor retryInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = null;
                boolean responseOK = false;
                int tryCount = 0;

                while (!responseOK && tryCount < 3) {
                    try {
                        response = chain.proceed(request);
                        responseOK = response.isSuccessful();
                    } catch (IOException e) {
                        LogUtils.e(TAG, e.getMessage(), e);
                        LogUtils.d(TAG, "Request is not successful - " + tryCount);
                    } finally {
                        tryCount++;
                    }
                }

                // otherwise just pass the original response on
                return response;
            }
        };

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient
                .connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(retryInterceptor)
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());

        if (StringUtils.isNotBlank(baseUrl)) {
            builder.baseUrl(baseUrl);
        }

        return builder.client(client).build();
    }
}
