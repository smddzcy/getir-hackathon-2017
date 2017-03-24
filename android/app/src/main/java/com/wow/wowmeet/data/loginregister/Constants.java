package com.wow.wowmeet.data.loginregister;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

class Constants {
    static final String API_URL = "https://getir-hackathon-2017-wow-team.herokuapp.com";

    static final String LOGIN_ENDPOINT = Constants.API_URL + "/signup";
    static final String REGISTER_ENDPOINT = Constants.API_URL + "/register";

    static final String EMAIL_PARAM_NAME = "email";
    static final String PASSWORD_PARAM_NAME = "password";
    static final String CONFIRM_PASSWORD_PARAM_NAME = "confirmPassword";
}
