package com.bgylde.ticket.request.model;

/**
 * Created by wangyan on 2019/1/8
 */
public class UserInfoResponse {

    // {"apptk":"WcojAJ68NqNS-KfznjTNCVIXi3Pkj9wKmkb2b0","result_code":0,"result_message":"验证通过","username":"王岩"}

    private String apptk;

    private int result_code;

    private String result_message;

    private String username;

    public String getApptk() {
        return apptk;
    }

    public String getResult_message() {
        return result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public String getUsername() {
        return username;
    }

    public void setApptk(String apptk) {
        this.apptk = apptk;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
