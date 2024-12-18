package com.mlf.wanandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/13 12:49
 * @version: 1.0
 */
public class Shape {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("wanAndroid", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void setString(String key, String values) {
        editor.putString(key, values);
        editor.commit();
    }

    public static void setboolean(String key, boolean values) {
        editor.putBoolean(key, values);
        editor.commit();
    }

    public static void setfloat(String key, float values) {
        editor.putFloat(key, values);
        editor.commit();
    }

    public static void setint(String key, int values) {
        editor.putInt(key, values);
        editor.commit();
    }

    public static void setlong(String key, long values) {
        editor.putLong(key, values);
        editor.commit();
    }

    public static void setStringSet(String key, Set<String> values) {
        editor.putStringSet(key, values);
        editor.commit();
    }

    public static boolean getboolean(String key, boolean values) {
        return preferences.getBoolean(key, values);
    }

    public static int getint(String key, int values) {
        return preferences.getInt(key, values);
    }

    public static String getString(String key, String values) {
        return preferences.getString(key, values);
    }

    public static float getfloat(String key, float values) {
        return preferences.getFloat(key, values);
    }

    public static long getlong(String key, long values) {
        return preferences.getLong(key, values);
    }

    public static Set<String> getSetString(String key, Set<String> values) {
        return preferences.getStringSet(key, values);
    }

    public static void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    public static void remgg() {
        editor.clear();
        editor.commit();
    }
}
