package com.bgylde.ticket.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
}

