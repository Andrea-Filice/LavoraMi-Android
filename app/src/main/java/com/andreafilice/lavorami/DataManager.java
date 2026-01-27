package com.andreafilice.lavorami;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class DataManager{
    static SharedPreferences sharedPref;

    public static void refreshDatas(Context context){
        sharedPref = context.getSharedPreferences("LavoraMiPreferences", Context.MODE_PRIVATE);
    }

    public static void saveStringData(String key, String value){
        sharedPref.edit().putString(key, value).apply();
    }

    public static void saveArrayStringsData(String key, Set<String> values){
        sharedPref.edit().putStringSet(key, values).apply();
    }

    public static void saveIntData(String key, int value){
        sharedPref.edit().putInt(key, value).apply();
    }

    public static void saveBoolData(String key, boolean value){
        sharedPref.edit().putBoolean(key, value).apply();
    }

    public static String getStringData(Context context, String key, String defaulValue){
        refreshDatas(context);
        return sharedPref.getString(key, defaulValue);
    }

    public static int getIntData(Context context, String key, int defaultValue){
        refreshDatas(context);
        return sharedPref.getInt(key, defaultValue);
    }

    public static boolean getBoolData(Context context, String key, boolean defaultValue){
        refreshDatas(context);
        return sharedPref.getBoolean(key, defaultValue);
    }
}
