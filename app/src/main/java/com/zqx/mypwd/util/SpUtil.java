package com.zqx.mypwd.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

import static com.zqx.mypwd.MyApp.context;

public class SpUtil {

    private static final String            SP_NAME = "config";
    private static       SharedPreferences sp      = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

    private SpUtil() {
    }

    public static void saveBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static void saveInt(String key, int value) {
        sp.edit().putInt(key, value).apply();

    }

    public static int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static void saveString(String key, String value) {
        sp.edit().putString(key, value).apply();

    }

    public static void remove(String... keys) {
        SharedPreferences.Editor editor = sp.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    public static void saveStringSet(String key, Set<String> set) {
        sp.edit().putStringSet(key, set).apply();
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return sp.getStringSet(key, defValue);
    }

    public static void saveByteArray(String key,byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
            if (i != bytes.length - 1) {
                sb.append(",");
            }
        }
        sp.edit().putString(key, sb.toString()).apply();
    }

    public static byte[] getByteArray(String key, byte[] defValue) {
        String string = sp.getString(key, "");
        if (TextUtils.isEmpty(string)) {
            return defValue;
        } else {
            String[] strings = string.split(",");
            byte[] bytes = new byte[strings.length];
            for (int i = 0; i < strings.length; i++) {
                bytes[i] = Byte.parseByte(strings[i]);
            }
            return bytes;
        }
    }
}
