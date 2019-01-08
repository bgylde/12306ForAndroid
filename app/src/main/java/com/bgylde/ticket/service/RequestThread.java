package com.bgylde.ticket.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.DateUtils;

import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.utils.DialogUtils;
import com.bgylde.ticket.utils.LogUtils;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by wangyan on 2019/1/7
 */
public class RequestThread extends HandlerThread {

    private static final String TAG = "RequestThread";

    private static final long CHECK_USER_STATE_TIME = 2 * DateUtils.MINUTE_IN_MILLIS;

    private Context context;

    private Handler handler;

    private long lastCheckTime = 0;

    private volatile boolean isDestory = false;

    RequestThread(Context context) {
        super(TAG);
        this.context = context;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        if (handler == null) {
            handler = new Handler(getLooper());
        }

        initQuery();
//        while (!isDestory) {
//            try {
//                //RequestManaager.getInstance().sendUserCheckRequest(context);
//            } catch (Exception e) {
//                LogUtils.e(TAG, e);
//            }
//        }
    }

    private void initQuery() {
        ResponseBody responseBody = RequestManaager.getInstance().sendInitRequest(context);
        if (responseBody == null) {
            return;
        }

        Bitmap bitmap = RequestManaager.getInstance().sendIdentifyCodeRequest(context);
        if (bitmap == null) {
            return;
        }

        DialogUtils.showDialog(context, bitmap, handleMessage);
        AuthResponse authResponse = RequestManaager.getInstance().sendAuthRequest(context);
        if (authResponse == null || authResponse.getResult_code() == 0) {
            LogUtils.d(TAG, "authResponse: " + authResponse.getResult_message());
        }
    }

    public interface HandleMessage {
        void checkIdentifyCode(String answer);
    }

    private HandleMessage handleMessage = new HandleMessage() {
        @Override
        public void checkIdentifyCode(final String answer) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    IdentifyCheckResponse res =
                            RequestManaager.getInstance().sendIdentifyCheckRequest(context, answer);
                    if (res != null && "4".equals(res.getResult_code())) {
                        LoginResponse loginResponse = RequestManaager.getInstance().
                                sendUserLogin(context, "bgylde", "wy749900");
                        if (loginResponse != null && loginResponse.getResult_code() == 0) {
                            // 登录成功
                            AuthResponse authResponse = RequestManaager.getInstance().sendAuthRequest(context);
                            if (authResponse == null) {
                                return;
                            }

                            UserInfoResponse userInfoResponse = RequestManaager.getInstance().sendUserInfoRequest(context, authResponse.getNewapptk());
                            if (userInfoResponse == null || userInfoResponse.getResult_code() != 0) {
                                LogUtils.w(TAG, "User login error!");
                                return;
                            }

                            LogUtils.i(TAG, "Welcome user[" + userInfoResponse.getUsername() + "] login.");
                            queryTicket();
                        }
                    } else {
                        Bitmap bitmap = RequestManaager.getInstance().sendIdentifyCodeRequest(context);
                        if (bitmap == null) {
                            return;
                        }

                        DialogUtils.showDialog(context, bitmap, handleMessage);
                    }
                }
            });
        }
    };

    private void queryTicket() {
        while(!isDestory) {
            if (System.currentTimeMillis() - lastCheckTime > CHECK_USER_STATE_TIME) {
                UserCheckResponse response = RequestManaager.getInstance().sendUserCheckRequest(context);
                if (response != null) {
                    if (response.getData() != null && response.getData().getFlag()) {
                        lastCheckTime = System.currentTimeMillis();
                    } else {
                        LogUtils.w(TAG, "ReLogin!");
                        initQuery();
                    }
                }
            }

            QueryTicketsResponse response = RequestManaager.getInstance().sendQueryTickets(context, "2019-02-01", "BXP", "TNV");
            if (response != null) {
                List<String> list = response.getData().getResult();
                for (String itemStr : list) {
                    QueryTicketItemModel model = new QueryTicketItemModel(itemStr);
                    if (model.getResult().equals("Y") && model.getStatus().equals("预定")) {

                    }
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
