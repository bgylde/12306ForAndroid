package com.bgylde.ticket.service;

/**
 * Created by wangyan on 2019/1/7
 */
public interface IPollService {

    // 登录用户
    void loginAccount(String userName, String passwd);

    // 开始抢票
    void startBuyTickets();
}
