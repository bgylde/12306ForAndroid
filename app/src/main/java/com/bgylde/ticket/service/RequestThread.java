package com.bgylde.ticket.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.format.DateUtils;

import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketItemModel;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.request.utils.RequestManaager;
import com.bgylde.ticket.ui.model.EventBusCarrier;
import com.bgylde.ticket.utils.ConfigPreference;
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

    private static final long MAX_CHECK_INTERVAL = 1000;

    private static final String TAG = "RequestThread";

    private static final long CHECK_USER_STATE_TIME = 2 * DateUtils.MINUTE_IN_MILLIS;

    private Context context;

    private Handler handler;

    private long lastCheckTime = 0;

    private OrderInfoModel orderModel = null;

    private volatile boolean isBuyingTickets = false;

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
                case START_BUY_TICKETS:
                    LogUtils.d(TAG, "start but tickets.");
                    if (msg.obj instanceof List) {
                        isBuyingTickets = true;
                        List<QueryTicketItemModel> orderList = (List<QueryTicketItemModel>)msg.obj;
                        handleOrderList(orderList);
                    }
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

                ConfigPreference.updateUserInfo(context, userName, passwd);
                LogUtils.i(TAG, "Welcome user[" + userInfoResponse.getUsername() + "] login.");
                if (isBuyingTickets) {
                    queryTickets();
                } else {
                    EventBus.getDefault().post(new EventBusCarrier(LOGING_SUCCESSFUL_CODE, userInfoResponse));
                }
            }
        } else {
            Bitmap bitmap = RequestManaager.getInstance().sendIdentifyCodeRequest(context);
            if (bitmap == null) {
                return;
            }

            DialogUtils.showDialog(context, bitmap, handler);
        }
    }

    private void handleOrderList(List<QueryTicketItemModel> orderList) {
        if (orderList == null || orderList.size() <= 0) {
            return;
        }

        orderModel = new OrderInfoModel();
        for (QueryTicketItemModel model : orderList) {
            orderModel.setFromStationFlag(model.getFromStation())
                        .setToStationFlag(model.getToStation())
                        .addOrderDate(model.getDate())
                        .addTrainCode(model.getTrainCode());
        }

        queryTickets();
    }

    private void queryTickets() {
        if (orderModel == null) {
            return;
        }

        int count = 1;
        while(true) {
            if (System.currentTimeMillis() - lastCheckTime > CHECK_USER_STATE_TIME) {
                UserCheckResponse response = RequestManaager.getInstance().sendUserCheckRequest(context);
                if (response != null) {
                    if (response.getData() != null && response.getData().getFlag()) {
                        lastCheckTime = System.currentTimeMillis();
                    } else {
                        LogUtils.w(TAG, "ReLogin!");
                        initQuery();
                        break;
                    }
                }
            }

            List<String> dateList = orderModel.getDateList();
            if (dateList == null || dateList.size() <= 0) {
                return;
            }

            DialogUtils.showNotification(context, "第" + count++ + "次查询", count);
            for (String date : dateList) {
                QueryTicketsResponse response = RequestManaager.getInstance().sendQueryTicketsAnsyc(
                        context, date, orderModel.getFromStationFlag(), orderModel.getToStationFlag());
                if (response != null) {
                    List<String> list = response.getData().getResult();
                    for (String itemStr : list) {
                        QueryTicketItemModel model = new QueryTicketItemModel(itemStr, date);
                        if (orderModel.containsStationCode(model.getTrainCode())
                                && model.getResult().equals("Y")
                                && model.getStatus().equals("预订")) {
                            LogUtils.d(TAG, "OK, successful.");

                            //todo 下订单
                            break;
                        }
                    }
                }

                try {
                    Thread.sleep(MAX_CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler getHandler() {
        return handler;
    }
}
