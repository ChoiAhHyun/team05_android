package com.yapp14th.yappapp.Base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String FILE_NAME = "SIMO-preference";
    private static Preferences instance;

    public static Preferences getInstance() {
        if (null == instance) {
            instance = new Preferences();
        }
        return instance;
    }

    public void putSharedPreference(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putSharedPreference(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void deleteSharePreference(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

    public void putSharedPreference(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getSharedPreference(Context context, String key,
                                      String defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        return pref.getString(key, defaultValue);
    }

    public int getSharedPreference(Context context, String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    public boolean getSharedPreference(Context context, String key,
                                       boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public void resetSharedPreference(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
