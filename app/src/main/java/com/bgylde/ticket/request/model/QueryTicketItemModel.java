package com.bgylde.ticket.request.model;

/**
 * Created by wangyan on 2019/1/8
 */
public class QueryTicketItemModel {

    // |预订|240000K6090C|K609|BJP|HCY|BJP|TYV|23:55|07:37|07:42|N|8tge3Tsc1%2B0miIf9mTe4r3m0wG0ZCyVdzaLeqY29Lg%2BhOoh4rOMMkufogvc%3D|20190201|3|PA|01|08|0|0||||无|||无||无|无|||||10401030|1413|0|0

    private String secretSrt;   // 0

    private String status;      // 1 预订

    private String trainNo;     // 2 240000K6090C

    private String trainCode;   // 3 K609

    private String fromStation; // 6 BJP

    private String toStation;   // 7 TYV

    private String startTime;   // 8 23:55

    private String arrivalTime; // 9 07:37

    private String distanceTime;// 10 07:42

    private String result;      // 11 N

    private String leftTicket;  // 12 8tge3Tsc1%2B0miIf9mTe4r3m0wG0ZCyVdzaLeqY29Lg%2BhOoh4rOMMkufogvc%3D

    private String softSeat;        // 23 软卧

    private String superSeat;       // 25 特等座

    private String voidSeat;        // 26 无座

    private String hardSeat;        // 28 硬卧

    private String hardSeat2;       // 29 硬座

    private String bussinessSeat;   // 32 商务座

    private String firstSeat;       // 31 一等座

    private String secondSeat;      // 30 二等座

    private String date;

    public QueryTicketItemModel(String str, String date) {
        String[] list = str.split("\\|");
        secretSrt = list[1];
        status = list[1];
        trainNo = list[2];
        trainCode = list[3];
        fromStation = list[6];
        toStation = list[7];
        startTime = list[8];
        arrivalTime = list[9];
        distanceTime = list[10];
        result = list[11];
        leftTicket = list[12];
        softSeat = list[23];
        superSeat = list[25];
        voidSeat = list[26];
        hardSeat = list[28];
        hardSeat2 = list[29];
        bussinessSeat = list[32];
        firstSeat = list[31];
        secondSeat = list[30];

        this.date = date;
    }

    public String getToStation() {
        return toStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getBussinessSeat() {
        return bussinessSeat;
    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public String getFirstSeat() {
        return firstSeat;
    }

    public String getHardSeat() {
        return hardSeat;
    }

    public String getHardSeat2() {
        return hardSeat2;
    }

    public String getLeftTicket() {
        return leftTicket;
    }

    public String getResult() {
        return result;
    }

    public String getSecondSeat() {
        return secondSeat;
    }

    public String getSecretSrt() {
        return secretSrt;
    }

    public String getSoftSeat() {
        return softSeat;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStatus() {
        return status;
    }

    public String getSuperSeat() {
        return superSeat;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getVoidSeat() {
        return voidSeat;
    }

    public String getDate() {
        return date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBussinessSeat(String bussinessSeat) {
        this.bussinessSeat = bussinessSeat;
    }

    public void setDistanceTime(String distanceTime) {
        this.distanceTime = distanceTime;
    }

    public void setFirstSeat(String firstSeat) {
        this.firstSeat = firstSeat;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setHardSeat(String hardSeat) {
        this.hardSeat = hardSeat;
    }

    public void setHardSeat2(String hardSeat2) {
        this.hardSeat2 = hardSeat2;
    }

    public void setLeftTicket(String leftTicket) {
        this.leftTicket = leftTicket;
    }

    public void setSecondSeat(String secondSeat) {
        this.secondSeat = secondSeat;
    }

    public void setSecretSrt(String secretSrt) {
        this.secretSrt = secretSrt;
    }

    public void setSoftSeat(String softSeat) {
        this.softSeat = softSeat;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setSuperSeat(String superSeat) {
        this.superSeat = superSeat;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public void setVoidSeat(String voidSeat) {
        this.voidSeat = voidSeat;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
