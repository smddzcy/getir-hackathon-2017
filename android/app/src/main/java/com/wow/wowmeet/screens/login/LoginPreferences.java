package com.wow.wowmeet.screens.login;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public interface LoginPreferences {
    boolean isUserLoggedIn();

    void saveUserLogin(String userId, String userToken);

    String getUserId();

    String getUserToken();
}
