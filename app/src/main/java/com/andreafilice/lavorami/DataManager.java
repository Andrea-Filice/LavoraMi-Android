package com.andreafilice.lavorami;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class DataManager{
    static SharedPreferences sharedPref;

    public static void refreshDatas(Context context){sharedPref = context.getSharedPreferences("LavoraMiPreferences", Context.MODE_PRIVATE);}

    public static void saveStringData(Context context, DataKeys key, String value){
        refreshDatas(context);
        sharedPref.edit().putString(key.toString(), value).apply();
    }

    public static void saveArrayStringsData(Context context, DataKeys key, Set<String> values){
        refreshDatas(context);
        sharedPref.edit().putStringSet(key.toString(), values).apply();
    }

    public static void saveIntData(Context context, DataKeys key, int value){
        refreshDatas(context);
        sharedPref.edit().putInt(key.toString(), value).apply();
    }

    public static void saveBoolData(Context context, DataKeys key, boolean value){
        refreshDatas(context);
        sharedPref.edit().putBoolean(key.toString(), value).apply();
    }

    public static String getStringData(Context context, DataKeys key, String defaulValue){
        refreshDatas(context);
        return sharedPref.getString(key.toString(), defaulValue);
    }

    public static Set<String> getStringArray(Context context, DataKeys key, Set<String> defaultValue){
        refreshDatas(context);
        return sharedPref.getStringSet(key.toString(), defaultValue);
    }

    public static int getIntData(Context context, DataKeys key, int defaultValue){
        refreshDatas(context);
        return sharedPref.getInt(key.toString(), defaultValue);
    }

    public static boolean getBoolData(Context context, DataKeys key, boolean defaultValue){
        refreshDatas(context);
        return sharedPref.getBoolean(key.toString(), defaultValue);
    }
}
