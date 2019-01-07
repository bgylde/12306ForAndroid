package com.bgylde.ticket.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.bgylde.ticket.http.RetrofitHttpUtils;
import com.bgylde.ticket.request.utils.AbsRquest;
import com.bgylde.ticket.utils.DialogUtils;
import com.bgylde.ticket.utils.LogUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangyan on 2019/1/7
 */
public class IdentifyCodeRequest extends AbsRquest {

    private static final String TAG = "IdentifyCodeRequest";

    private static final String URI = "passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&%d";

    private static final String REFERER = "https://kyfw.12306.cn/otn/login/init";

    private Context context;

    public IdentifyCodeRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String getReferUrl() {
        return REFERER;
    }

    @Override
    public String getUri() {
        String uri = String.format(Locale.CHINA, URI, new Random().nextInt());
        return uri;
    }

    @Override
    public void sendRequest(Context context) {
        RetrofitHttpUtils.sendByGet(context, getUri(), getHeaders(), callback);
    }

    private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
            try {
                final Bitmap bitmap = DialogUtils.getPicFromBytes(response.body().bytes(), null);
                DialogUtils.showDialog(context, bitmap);
            } catch (IOException e) {
                LogUtils.e(TAG, e);
            } catch (Exception e) {
                LogUtils.e(TAG, e);
            } finally {
                ResponseBody body = response.body();
                if (body != null) {
                    body.close();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            LogUtils.e(TAG, t);
        }
    };
}
