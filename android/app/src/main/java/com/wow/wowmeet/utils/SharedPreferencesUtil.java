package com.wow.wowmeet.utils;

import com.wow.wowmeet.models.User;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class SharedPreferencesUtil {
    private static final SharedPreferencesUtil ourInstance = new SharedPreferencesUtil();

    public static SharedPreferencesUtil getInstance() {
        return ourInstance;
    }



    private SharedPreferencesUtil() {

    }

    public void putUser(){

    }

    public boolean checkUserLoggedIn(){

    }

    public User getUser(){

    }
}
