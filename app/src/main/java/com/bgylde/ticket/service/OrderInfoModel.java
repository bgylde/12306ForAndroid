package com.bgylde.ticket.service;

import com.bgylde.ticket.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyan on 2019/1/19
 */
public class OrderInfoModel {

    private List<String> dateList;

    private String fromStationFlag;

    private String toStationFlag;

    private List<String> orderTrainCodeList;

    public OrderInfoModel() {
        this.dateList = new ArrayList<>();
        this.orderTrainCodeList = new ArrayList<>();
    }

    public OrderInfoModel setFromStationFlag(String fromStationFlag) {
        if (StringUtils.isNotBlank(this.fromStationFlag)) {
            return this;
        }

        this.fromStationFlag = fromStationFlag;
        return this;
    }

    public OrderInfoModel setToStationFlag(String toStationFlag) {
        if (StringUtils.isNotBlank(this.toStationFlag)) {
            return this;
        }

        this.toStationFlag = toStationFlag;
        return this;
    }

    public String getFromStationFlag() {
        return fromStationFlag;
    }

    public String getToStationFlag() {
        return toStationFlag;
    }

    public OrderInfoModel addOrderDate(String date) {
        if (StringUtils.isNotBlank(date) && !dateList.contains(date)) {
            dateList.add(date);
        }

        return this;
    }

    public OrderInfoModel addTrainCode(String code) {
        if (StringUtils.isNotBlank(code) && !orderTrainCodeList.contains(code)) {
            orderTrainCodeList.add(code);
        }

        return this;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public boolean containsStationCode(String stationCode) {
        if (!StringUtils.isNotBlank(stationCode) || orderTrainCodeList.size() <= 0) {
            return false;
        }

        for (String code : orderTrainCodeList) {
            if (code.equals(stationCode)) {
                return true;
            }
        }

        return false;
    }
}
