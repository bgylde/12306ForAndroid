package com.bgylde.ticket.request.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bgylde.ticket.http.RetrofitHttpUtils;
import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;
import com.bgylde.ticket.utils.LogUtils;
import com.bgylde.ticket.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * Created by wangyan on 2019/1/7
 */
public class RequestManaager {

    private static final String TAG = "RequestManager";

    private static RequestManaager instance = null;

    public static final String HOST_NAME = "Host";

    public static final String REFERER_NAME = "Referer";

    public static final String HOST = "kyfw.12306.cn";

    public static final String REFERER_INIT = "https://kyfw.12306.cn/otn/login/init";

    public static final String REFERER_IDENTIFY_CODE = "https://kyfw.12306.cn/otn/login/init";

    public static final String REFERER_USER_LOGIN = "https://kyfw.12306.cn/otn/login/init";

    public static final String REFERER_AUTH = "https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin";

    public static final String REFERER_IDENTIFY_CHECK = "https://kyfw.12306.cn/otn/login/init";

    public static final String REFERER_USER_CHECK = "https://kyfw.12306.cn/otn/leftTicket/init";

    public static final String REFERER_USER_INFO = "https://kyfw.12306.cn/otn/passport?redirect=/otn/login/userLogin";

    public static final String REFERER_QUERY_TICKETS = "https://kyfw.12306.cn/otn/leftTicket/init";

    private final Map<String, Object> headers;

    private RequestManaager() {
        headers = new HashMap<>();
    }

    public static RequestManaager getInstance() {
        if (instance == null) {
            synchronized (RequestManaager.class) {
                if (instance == null) {
                    instance = new RequestManaager();
                }
            }
        }

        return instance;
    }

    private Map<String, Object> buildHeader(String referer) {
        headers.put(HOST_NAME, HOST);
        headers.put(REFERER_NAME, referer);

        return  headers;
    }

    public ResponseBody sendInitRequest(Context context) {
        return RetrofitHttpUtils.sendInitRequest(context, buildHeader(REFERER_INIT));
    }

    public Bitmap sendIdentifyCodeRequest(Context context) {
        return RetrofitHttpUtils.sendIdentifyCodeRequest(context, buildHeader(REFERER_IDENTIFY_CODE));
    }

    public LoginResponse sendUserLogin(Context context, String username, String password) {
        Map<String, String> fields = new HashMap<>();
        fields.put("username", username);
        fields.put("password", password);
        fields.put("appid", "otn");

        return RetrofitHttpUtils.sendUserLogin(context, buildHeader(REFERER_USER_LOGIN), fields);
    }

    public AuthResponse sendAuthRequest(Context context) {
        Map<String, String> fields = new HashMap<>();
        fields.put("appid", "otn");

        return RetrofitHttpUtils.sendAuthRequest(context, buildHeader(REFERER_AUTH), fields);
    }

    public IdentifyCheckResponse sendIdentifyCheckRequest(Context context, String answer) {
        Map<String, String> fields = new HashMap<>();
        fields.put("answer", answer);
        fields.put("rand", "sjrand");
        fields.put("login_site", "E");
        fields.put("_", String.valueOf(System.currentTimeMillis()));

        return RetrofitHttpUtils.sendIdentifyCheckRequest(context, buildHeader(REFERER_IDENTIFY_CHECK), fields);
    }

    // {"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":{"flag":false},"messages":[],"validateMessages":{}}
    public UserCheckResponse sendUserCheckRequest(Context context) {
        Map<String, String> fields = new HashMap<>();
        fields.put("_json_att", "");

        return RetrofitHttpUtils.sendUserCheckRequest(context, buildHeader(REFERER_USER_CHECK), fields);
    }

    // {"apptk":"WcojAJ68NqNS-KfznjTNCVIXi3Pkj9wKmkb2b0","result_code":0,"result_message":"验证通过","username":"王岩"}
    public UserInfoResponse sendUserInfoRequest(Context context, String umatk) {
        Map<String, String> fields = new HashMap<>();
        fields.put("tk", umatk);

        return RetrofitHttpUtils.sendUserInfoRequest(context, buildHeader(REFERER_USER_INFO), fields);
    }

    // https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2019-02-01&leftTicketDTO.from_station=BXP&leftTicketDTO.to_station=TNV&purpose_codes=ADULT
    public QueryTicketsResponse sendQueryTicketsAnsyc(Context context, String trainDate, String fromStation, String toStation) {
        String uri = "otn/leftTicket/queryZ?" +
                "leftTicketDTO.train_date=" + trainDate +
                "&leftTicketDTO.from_station=" + fromStation +
                "&leftTicketDTO.to_station=" + toStation +
                "&purpose_codes=ADULT";

        return RetrofitHttpUtils.sendQueryTicketsAnsyc(context, uri, buildHeader(REFERER_QUERY_TICKETS));
    }

    public void sendQueryTickets(Context context, String trainDate, String fromStation, String toStation, Callback<QueryTicketsResponse> callback) {
        if (!StringUtils.isNotBlank(trainDate) || !StringUtils.isNotBlank(toStation) || !StringUtils.isNotBlank(fromStation)) {
            LogUtils.w(TAG, "sendQueryTickets error. trainDate: " + trainDate + " fromStation: " + fromStation + " toStation:" + toStation);
            return;
        }
        String uri = "otn/leftTicket/queryZ?" +
                "leftTicketDTO.train_date=" + trainDate +
                "&leftTicketDTO.from_station=" + fromStation +
                "&leftTicketDTO.to_station=" + toStation +
                "&purpose_codes=ADULT";

        RetrofitHttpUtils.sendQueryTickets(context, uri, buildHeader(REFERER_QUERY_TICKETS), callback);
    }
}
