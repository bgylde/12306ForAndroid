package com.bgylde.ticket.service;

import com.bgylde.ticket.request.model.QueryTicketItemModel;

import java.util.List;

/**
 * Created by wangyan on 2019/1/7
 */
public interface IPollService {

    // 登录用户
    void loginAccount(String userName, String passwd);

    // 开始抢票
    void startBuyTickets(List<QueryTicketItemModel> orderList);
}
