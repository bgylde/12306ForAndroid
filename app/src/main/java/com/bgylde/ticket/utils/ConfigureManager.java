package com.bgylde.ticket.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyan on 2019/1/8
 */
public class ConfigureManager {

    private static final String TAG = "ConfigureManager";

    private static ConfigureManager instance = null;

    private List<String> stationDates;

    private List<String> stationTrains;

    private String fromStation;

    private String toStation;

    private List<String> setTypes;

    private List<String> peoples;

    private String accountUser;

    private String accountPwd;

    private ConfigureManager() {
    }

    public static ConfigureManager getInstance() {
        if (instance == null) {
            synchronized (ConfigureManager.class) {
                if (instance == null) {
                    instance = new ConfigureManager();
                }
            }
        }

        return instance;
    }

    public void addStationDate(String stationDate) {
        if (stationDates == null) {
            stationDates = new ArrayList<>();
        }

        stationDates.add(stationDate);
    }

    public void addStationTrains(String stationTrain) {
        if (stationTrains == null) {
            stationTrains = new ArrayList<>();
        }

        stationTrains.add(stationTrain);
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public void addSetType(String setType) {
        if (setTypes == null) {
            setTypes = new ArrayList<>();
        }

        setTypes.add(setType);
    }

    public void addPeople(String name) {
        if (peoples == null) {
            peoples = new ArrayList<>();
        }

        peoples.add(name);
    }

    public void setUsername(String accountUser) {
        this.accountUser = accountUser;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }

    public List<String> getPeoples() {
        return peoples;
    }

    public List<String> getSetTypes() {
        return setTypes;
    }

    public List<String> getStationDates() {
        return stationDates;
    }

    public List<String> getStationTrains() {
        return stationTrains;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public String getAccountUser() {
        return accountUser;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }
}
