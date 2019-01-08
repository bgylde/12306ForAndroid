package com.bgylde.ticket.request.model;

import java.util.List;

/**
 * Created by wangyan on 2019/1/8
 */
public class UserCheckResponse {
    //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":{"flag":false},"messages":[],"validateMessages":{}}

    private String validateMessagesShowId;

    private boolean status;

    private int httpstatus;

    private Data data;

    private List<String> messages;

    public int getHttpstatus() {
        return httpstatus;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getValidateMessagesShowId() {
        return validateMessagesShowId;
    }

    public boolean getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public void setHttpstatus(int httpstatus) {
        this.httpstatus = httpstatus;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setValidateMessagesShowId(String validateMessagesShowId) {
        this.validateMessagesShowId = validateMessagesShowId;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private boolean flag;

        public boolean getFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
}
