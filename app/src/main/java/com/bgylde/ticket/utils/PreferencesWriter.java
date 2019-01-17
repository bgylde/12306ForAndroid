package com.bgylde.ticket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangyan on 2019/1/17
 */
abstract class PreferencesWriter {
    protected Context mContext;

    private String mName;

    private static final String KEY_PREFERENCES_VERSION = "preferences_version";
    private static final int DEFAULT_PREFERENCES_VERSION = 0;

    PreferencesWriter(Context context, String name) {
        this.mContext = context;
        this.mName = name;
        initPreferencesChanages();
    }

    protected abstract void initPreferencesChanages();

    public Context getContext() {
        return mContext;
    }

    public String getName() {
        return mName;
    }

    protected int getVersion() {
        return getPreference().getInt(KEY_PREFERENCES_VERSION, DEFAULT_PREFERENCES_VERSION);
    }

    protected boolean updateVersion(int version) {
        return version > DEFAULT_PREFERENCES_VERSION && updateValue(KEY_PREFERENCES_VERSION, version);
    }

    public boolean removeKey(String key) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.remove(key);
        return editor.commit();
    }

    public boolean updateValue(String key, long value) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return getPreference().getLong(key, defaultValue);
    }

    public boolean updateValue(String key, String value) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return getPreference().getString(key, defaultValue);
    }

    public boolean updateValue(String key, int value) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return getPreference().getInt(key, defaultValue);
    }

    public boolean updateValue(String key, float value) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float getFloat(String key, float defaultValue) {
        return getPreference().getFloat(key, defaultValue);
    }

    public boolean updateValue(String key, boolean value) {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getPreference().getBoolean(key, defaultValue);
    }

    protected SharedPreferences getPreference() {
        return mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
    }
}
