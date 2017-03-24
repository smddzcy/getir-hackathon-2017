package com.wow.wowmeet.data.login;

import okhttp3.OkHttpClient;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class LoginRepository {

    private static final String LOGIN_ENDPOINT = "/login";
    private OkHttpClient client;

    public LoginRepository(){
        this.client = new OkHttpClient();
    }


}
