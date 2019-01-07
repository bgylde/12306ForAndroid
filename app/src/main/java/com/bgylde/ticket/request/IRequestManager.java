package com.bgylde.ticket.request;

import android.content.Context;

/**
 * Created by wangyan on 2019/1/7
 */
public interface IRequestManager {

    void queryInitPage(Context context);

    void identifyCodeRequest(Context context);

    void auth(Context context);

    void identifyCodeTest(Context context);

    void checkUserState(Context context);

    void queryLeftTickets(Context context);

    void queryPeople(Context context);

    void submitOrder(Context context);
}
