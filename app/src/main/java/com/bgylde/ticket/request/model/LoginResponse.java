package com.bgylde.ticket.request.model;

/**
 * Created by wangyan on 2019/1/8
 */
public class LoginResponse {
    String result_message;

    int result_code;

    String uamtk;

    public String getResult_message() {
        return result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public String getUamtk() {
        return uamtk;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public void setUamtk(String uamtk) {
        this.uamtk = uamtk;
    }
}
