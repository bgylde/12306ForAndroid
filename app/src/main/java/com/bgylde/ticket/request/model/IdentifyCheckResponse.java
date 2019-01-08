package com.bgylde.ticket.request.model;

/**
 * Created by wangyan on 2019/1/8
 */
public class IdentifyCheckResponse {

    String result_message;

    String result_code;

    public String getResult_code() {
        return result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }
}
