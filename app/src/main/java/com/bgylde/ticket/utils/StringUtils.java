package com.bgylde.ticket.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

/**
 * Created by wangyan on 2019/1/7
 */
public class StringUtils {

    public static boolean isNotBlank(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static String formatString(String format, Object... args) {
        return String.format(Locale.CHINA, format, args);
    }
}
