package com.bgylde.ticket.http;

import com.bgylde.ticket.request.model.AuthResponse;
import com.bgylde.ticket.request.model.IdentifyCheckResponse;
import com.bgylde.ticket.request.model.LoginResponse;
import com.bgylde.ticket.request.model.QueryTicketsResponse;
import com.bgylde.ticket.request.model.UserCheckResponse;
import com.bgylde.ticket.request.model.UserInfoResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by wangyan on 2019/1/7
 */
interface IRetrofitService {

    @FormUrlEncoded
    @POST
    Call<ResponseBody> sendByPost(@Url String url, @FieldMap Map<String, String> fields, @HeaderMap Map<String, Object> headers);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> sendByPost(@Url String url, @FieldMap Map<String, String> fields);

    @GET
    Call<ResponseBody> sendByGet(@Url String url, @HeaderMap Map<String, Object> headers);

    @GET
    Call<ResponseBody> sendByGet(@Url String url);


    // 请求抢票页面
    @GET("otn/leftTicket/init")
    Call<ResponseBody> sendInitRequest(@HeaderMap Map<String, Object> headers);

    // 下载验证码
    @GET("passport/captcha/captcha-image")
    Call<ResponseBody> sendIdentifyCodeRequest(@HeaderMap Map<String, Object> headers);

    // 认证
    @FormUrlEncoded
    @POST("passport/web/auth/uamtk")
    Call<AuthResponse> sendAuthRequest(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 验证码校验
    @FormUrlEncoded
    @POST("passport/captcha/captcha-check")
    Call<IdentifyCheckResponse> sendIdentifyCheckRequest(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 检查用户登录
    @FormUrlEncoded
    @POST("otn/login/checkUser")
    Call<UserCheckResponse> sendUserCheckRequest(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 用户登录
    @FormUrlEncoded
    @POST("passport/web/login")
    Call<LoginResponse> sendUserLogin(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("otn/uamauthclient")
    Call<UserInfoResponse> sendUserInfo(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 查询余票
    @GET
    Call<QueryTicketsResponse> sendQueryTickets(@Url String url, @HeaderMap Map<String, Object> headers);

    // 获取联系人
    @FormUrlEncoded
    @POST("otn/confirmPassenger/getPassengerDTOs")
    Call<ResponseBody> sendQueryContactPerson(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 快速下单
    @FormUrlEncoded
    @POST("otn/confirmPassenger/autoSubmitOrderRequest")
    Call<ResponseBody> sendQuickOrder(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 请求排队接口
    @FormUrlEncoded
    @POST("otn/confirmPassenger/getQueueCountAsync")
    Call<ResponseBody> sendQuickQueue(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 普通下单
    @FormUrlEncoded
    @POST("otn/confirmPassenger/checkOrderInfo")
    Call<ResponseBody> sendNormalOrder(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);

    // 普通排队
    @FormUrlEncoded
    @POST("otn/confirmPassenger/getQueueCount")
    Call<ResponseBody> sendNormalQueue(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, String> fields);
}

