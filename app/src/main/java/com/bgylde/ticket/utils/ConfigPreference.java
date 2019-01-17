package com.bgylde.ticket.utils;

import android.content.Context;

/**
 * Created by wangyan on 2019/1/17
 */
public class ConfigPreference extends PreferencesWriter{

    private static final int VERSION = 1;

    private static final String FILE_NAME = "ConfigureFile";

    private static final String ACCOUNT_USERNAME = "user_name";

    private static final String ACCOUNT_PASSWORD = "user_passwd";

    private static final String FROM_STATION_NAME = "form_station";

    private static final String TO_STATION_NAME = "to_station";

    private static final String QUERY_DATE = "query_date";

    public ConfigPreference(Context context) {
        super(context, FILE_NAME);
    }

    ConfigPreference(Context context, String name) {
        super(context, name);
    }

    @Override
    protected void initPreferencesChanages() {
        int version = getVersion();
        if (version != VERSION) {
            updateVersion(VERSION);
        }
    }

    private boolean updateUserName(String username) {
        return updateValue(ACCOUNT_USERNAME, username);
    }

    private boolean updateUserPasswd(String passwd) {
        return updateValue(ACCOUNT_PASSWORD, passwd);
    }

    private boolean updateFromStation(String fromStation) {
        return updateValue(FROM_STATION_NAME, fromStation);
    }

    private boolean updateToStation(String toStation) {
        return updateValue(TO_STATION_NAME, toStation);
    }

    private boolean updateQueryDate(String queryDate) {
        return updateValue(QUERY_DATE, queryDate);
    }

    public String getUsername() {
        return getString(ACCOUNT_USERNAME, "");
    }

    public String getUserPasswd() {
        return getString(ACCOUNT_PASSWORD, "");
    }

    public String getFromStation() {
        return getString(FROM_STATION_NAME, "");
    }

    public String getToStation() {
        return getString(TO_STATION_NAME, "");
    }

    public String getQueryDate() {
        return getString(QUERY_DATE, "");
    }

    public static void updateUserInfo(Context context, String username, String passwd) {
        ConfigPreference configPreference = new ConfigPreference(context);
        configPreference.updateUserName(username);
        configPreference.updateUserPasswd(passwd);
    }

    public static void updateQueryInfo(Context context, String date, String fromStation, String toStation) {
        ConfigPreference configPreference = new ConfigPreference(context);
        configPreference.updateQueryDate(date);
        configPreference.updateFromStation(fromStation);
        configPreference.updateToStation(toStation);
    }
}
