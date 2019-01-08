package com.bgylde.ticket.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.utils.DialogUtils;
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

    public static boolean sendByGet(Context context, String url, Map<String, Object> headers, retrofit2.Callback<ResponseBody> callback) {
        IRetrofitService service = getRetrofitService(context);
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
                callback.onResponse(repoCall, response);
                return true;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
            callback.onFailure(repoCall, e);
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

    public static boolean sendByPost(Context context, String url, Map<String, String> fields, Map<String, Object> headers, retrofit2.Callback<ResponseBody> callback) {
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
                callback.onResponse(repoCall, response);
                return true;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
            callback.onFailure(repoCall, e);
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


//    public static <T> T sendSyncRequest(Context context, String url, Map<String, Object> headers, Map<String, String> fields, Class<T> classOfT) {
//
//    }

    // 请求抢票页面
    public static ResponseBody sendInitRequest(Context context, Map<String, Object> headers) {
        IRetrofitService service = getRetrofitService(context);
        Call<ResponseBody> repoCall = service.sendInitRequest(headers);

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 下载验证码
    public static Bitmap sendIdentifyCodeRequest(Context context, Map<String, Object> headers) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendIdentifyCodeRequest(headers);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return DialogUtils.getPicFromBytes(body.bytes(), null);
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 认证
    public static AuthResponse sendAuthRequest(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<AuthResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendAuthRequest(headers, fields);
        }

        retrofit2.Response<AuthResponse> response = null;
        AuthResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 验证码校验
    public static IdentifyCheckResponse sendIdentifyCheckRequest(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<IdentifyCheckResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendIdentifyCheckRequest(headers, fields);
        }

        retrofit2.Response<IdentifyCheckResponse> response = null;
        IdentifyCheckResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 用户登录
    public static LoginResponse sendUserLogin(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<LoginResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendUserLogin(headers, fields);
        }

        retrofit2.Response<LoginResponse> response = null;
        LoginResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 获取用户信息
    public static UserInfoResponse sendUserInfoRequest(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<UserInfoResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendUserInfo(headers, fields);
        }

        retrofit2.Response<UserInfoResponse> response = null;
        UserInfoResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 检查用户登录
    public static UserCheckResponse sendUserCheckRequest(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<UserCheckResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendUserCheckRequest(headers, fields);
        }

        retrofit2.Response<UserCheckResponse> response = null;
        UserCheckResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 查询余票
    public static QueryTicketsResponse sendQueryTickets(Context context, String url, Map<String, Object> headers) {
        IRetrofitService service = getRetrofitService(context);

        Call<QueryTicketsResponse> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendQueryTickets(url, headers);
        }

        retrofit2.Response<QueryTicketsResponse> response = null;
        QueryTicketsResponse result = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                result = response.body();
                return result;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
//            if (response != null) {
//                Response body = response.raw();
//                if (body != null) {
//                    body.close();
//                }
//            }
        }

        return null;
    }

    // 获取联系人
    public static ResponseBody sendQueryContactPerson(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendQueryContactPerson(headers, fields);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 快速下单
    public static ResponseBody sendQuickOrder(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendQuickOrder(headers, fields);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 请求排队接口
    public static ResponseBody sendQuickQueue(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendQuickQueue(headers, fields);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 普通下单
    public static ResponseBody sendNormalOrder(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendNormalOrder(headers, fields);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
    }

    // 普通排队
    public static ResponseBody sendNormalQueue(Context context, Map<String, Object> headers, Map<String, String> fields) {
        IRetrofitService service = getRetrofitService(context);

        Call<ResponseBody> repoCall = null;
        if (headers == null || headers.size() <= 0) {
            return null;
        } else {
            repoCall = service.sendNormalQueue(headers, fields);
        }

        retrofit2.Response<ResponseBody> response = null;
        ResponseBody body = null;
        try {
            response = repoCall.execute();
            int code = response.code();
            if (code >= 200 && code < 400) {
                body = response.body();
                return body;
            }
        } catch (Exception | Error e) {
            LogUtils.e(TAG, e);
        } finally {
            if (body != null) {
                body.close();
            }
        }

        return null;
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
