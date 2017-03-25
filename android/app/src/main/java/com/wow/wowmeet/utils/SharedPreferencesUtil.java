package com.wow.wowmeet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wow.wowmeet.screens.login.LoginPreferences;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class SharedPreferencesUtil implements LoginPreferences{



    private static SharedPreferencesUtil ourInstance;

    public static SharedPreferencesUtil getInstance(Context context) {
        if(ourInstance == null)
             ourInstance = new SharedPreferencesUtil(context);
        return ourInstance;
    }

    public static final String PREFERENCES_NAME = "prefs";
    public static final String PREFS_USER_ID = "userId";
    public static final String PREFS_USER_TOKEN = "userToken";
    public static final String DEFAULT_NULL = "none";

    private SharedPreferences sharedPreferences;

    private SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private void removeUserId(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREFS_USER_ID);
        editor.apply();
    }

    private void removeUserToken(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREFS_USER_TOKEN);
        editor.apply();
    }

    @Override
    public boolean isUserLoggedIn(){
        return sharedPreferences.getString(PREFS_USER_ID, DEFAULT_NULL).equals(DEFAULT_NULL);
    }

    @Override
    public void saveUserLogin(String userId, String userToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFS_USER_ID, userId);
        editor.putString(PREFS_USER_TOKEN, userToken);
        editor.apply();
    }

    @Override
    public String getUserId(){
        return sharedPreferences.getString(PREFS_USER_ID, DEFAULT_NULL);
    }

    @Override
    public String getUserToken(){
        return sharedPreferences.getString(PREFS_USER_TOKEN, DEFAULT_NULL);
    }
}
