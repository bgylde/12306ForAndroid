package com.bgylde.ticket.ui.model;

/**
 * Created by wangyan on 2019/1/14
 */
public class EventBusCarrier {

    public static final byte INVALID_CODE = 0;
    public static final byte IDENTIFY_CODE = 1;

    // 登录成功
    public static final byte LOGING_SUCCESSFUL_CODE = 2;

    private byte eventType;

    private Object object;

    public EventBusCarrier(){}

    public EventBusCarrier(byte type, Object o) {
        this.eventType = type;
        this.object = o;
    }

    public byte getEventType() {
        return eventType;
    }

    public Object getObject() {
        return object;
    }

    public void setEventType(byte eventType) {
        this.eventType = eventType;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
