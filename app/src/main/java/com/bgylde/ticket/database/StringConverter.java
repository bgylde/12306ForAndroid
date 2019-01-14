package com.bgylde.ticket.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyan on 2019/1/14
 */
public class StringConverter implements PropertyConverter<List<String>, String> {

    private final Gson gson;

    public StringConverter() {
        gson = new Gson();
    }

    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();

            ArrayList<String> list = gson.fromJson(databaseValue, type);

            return list;
        }
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        String dbString = gson.toJson(entityProperty);

        return dbString;
    }
}
