package com.andreafilice.lavorami;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "LavoraMiSession";
    private static final String KEY_TOKEN = "access_token";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveSession(String token, String email, String name){
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn(){return  prefs.getBoolean(KEY_IS_LOGGED_IN, false);}
    public String getToken(){return prefs.getString(KEY_TOKEN, null);}
    public String getUserEmail(){return prefs.getString(KEY_EMAIL, "");}
    public String getUserName(){return prefs.getString(KEY_NAME, "");}

    public void logout(){
        editor.clear();
        editor.apply();
    }
}
