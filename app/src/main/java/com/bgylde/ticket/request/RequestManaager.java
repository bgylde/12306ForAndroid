package com.bgylde.ticket.request;

import android.content.Context;

/**
 * Created by wangyan on 2019/1/7
 */
public class RequestManaager implements IRequestManager {

    private static final String TAG = "RequestManager";

    private static RequestManaager instance = null;

    private QueryPageRequest queryPageRequest = null;

    private RequestManaager() {

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


    @Override
    public void queryInitPage(Context context) {
        if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }

        queryPageRequest.sendRequest(context);
    }

    @Override
    public void identifyCodeRequest(Context context) {

    }

    @Override
    public void auth(Context context) {

    }

    @Override
    public void identifyCodeTest(Context context) {

    }

    @Override
    public void checkUserState(Context context) {

    }

    @Override
    public void queryLeftTickets(Context context) {

    }

    @Override
    public void queryPeople(Context context) {

    }

    @Override
    public void submitOrder(Context context) {

    }
}