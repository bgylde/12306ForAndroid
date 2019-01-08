package com.bgylde.ticket.request.model;

/**
 * Created by wangyan on 2019/1/8
 */
public class AuthResponse {
    private String result_message;
    private int result_code;
    private String apptk;
    private String newapptk;

    public int getResult_code() {
        return result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public String getApptk() {
        return apptk;
    }

    public String getNewapptk() {
        return newapptk;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public void setApptk(String apptk) {
        this.apptk = apptk;
    }

    public void setNewapptk(String newapptk) {
        this.newapptk = newapptk;
    }
}
