package com.bgylde.ticket.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.format.DateUtils;

import com.bgylde.ticket.database.UserDbManager;
import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.ui.model.EventBusCarrier;
import com.bgylde.ticket.utils.ConfigureManager;
import com.bgylde.ticket.utils.DialogUtils;
import com.bgylde.ticket.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;

import static com.bgylde.ticket.ui.model.EventBusCarrier.LOGING_SUCCESSFUL_CODE;

/**
 * Created by wangyan on 2019/1/7
 */
public class RequestThread extends HandlerThread {

    // 用户账号登录
    public static final byte USER_ACCOUNT_LOGIN = 1;

    // 开始抢票
    public static final byte START_BUY_TICKETS = 2;

    // 验证码验证
    public static final byte IDENTIFY_CHECK = 3;

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
            handler = new Handler(getLooper(), handlerCallback);
        }
    }

    private void checkIsLogin() {
        UserCheckResponse response = RequestManaager.getInstance().sendUserCheckRequest(context);
        if (response != null) {
            if (response.getData() != null && response.getData().getFlag()) {
                AuthResponse authResponse = RequestManaager.getInstance().sendAuthRequest(context);
                if (authResponse == null) {
                    LogUtils.d(TAG, "authResponse null.");
                    initQuery();
                }

                UserInfoResponse userInfoResponse = RequestManaager.getInstance().sendUserInfoRequest(context, authResponse.getNewapptk());
                if (userInfoResponse == null || userInfoResponse.getResult_code() != 0) {
                    LogUtils.w(TAG, "User login error!");
                    initQuery();
                    return;
                }

                LogUtils.i(TAG, "Welcome user[" + userInfoResponse.getUsername() + "] login.");
                EventBus.getDefault().post(new EventBusCarrier(LOGING_SUCCESSFUL_CODE, userInfoResponse));
            } else {
                LogUtils.w(TAG, "Login!");
                initQuery();
            }
        } else {
            initQuery();
        }
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

        DialogUtils.showDialog(context, bitmap, handler);
        // EventBus.getDefault().post(new EventBusCarrier(EventBusCarrier.IDENTIFY_CODE, bitmap));
        AuthResponse authResponse = RequestManaager.getInstance().sendAuthRequest(context);
        if (authResponse == null || authResponse.getResult_code() == 0) {
            LogUtils.d(TAG, "authResponse: " + authResponse.getResult_message());
        }
    }

    private Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case USER_ACCOUNT_LOGIN:
                    checkIsLogin();
                    break;
                case IDENTIFY_CHECK:
                    identifyCodeCheck((String)msg.obj, ConfigureManager.getInstance().getAccountUser(),
                            ConfigureManager.getInstance().getAccountPwd());
                    break;
                default:
                    return false;
            }

            return true;
        }
    };

    private void identifyCodeCheck(String answer, String userName, String passwd) {
        IdentifyCheckResponse res =
                RequestManaager.getInstance().sendIdentifyCheckRequest(context, answer);
        if (res != null && "4".equals(res.getResult_code())) {
            LoginResponse loginResponse = RequestManaager.getInstance().sendUserLogin(context, userName, passwd);
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

                UserDbManager.getInstance().insertOrReplaceUser(userName, passwd);
                LogUtils.i(TAG, "Welcome user[" + userInfoResponse.getUsername() + "] login.");
                EventBus.getDefault().post(new EventBusCarrier(LOGING_SUCCESSFUL_CODE, userInfoResponse));

                //queryTicket();
            }
        } else {
            Bitmap bitmap = RequestManaager.getInstance().sendIdentifyCodeRequest(context);
            if (bitmap == null) {
                return;
            }

            // EventBus.getDefault().post(new EventBusCarrier(EventBusCarrier.IDENTIFY_CODE, bitmap));
            DialogUtils.showDialog(context, bitmap, handler);
        }
    }

    public Handler getHandler() {
        return handler;
    }

//    private void queryTicket() {
//        while(!isDestory) {
//            if (System.currentTimeMillis() - lastCheckTime > CHECK_USER_STATE_TIME) {
//                UserCheckResponse response = RequestManaager.getInstance().sendUserCheckRequest(context);
//                if (response != null) {
//                    if (response.getData() != null && response.getData().getFlag()) {
//                        lastCheckTime = System.currentTimeMillis();
//                    } else {
//                        LogUtils.w(TAG, "ReLogin!");
//                        initQuery();
//                    }
//                }
//            }
//
//            QueryTicketsResponse response = RequestManaager.getInstance().sendQueryTicketsAnsyc(context, "2019-02-01", "BXP", "TNV");
//            if (response != null) {
//                List<String> list = response.getData().getResult();
//                for (String itemStr : list) {
//                    QueryTicketItemModel model = new QueryTicketItemModel(itemStr, "2019-02-01");
//                    if (model.getResult().equals("Y") && model.getStatus().equals("预定")) {
//
//                    }
//                }
//            }
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
}
