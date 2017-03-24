package com.wow.wowmeet.data.loginregister;

import static com.wow.wowmeet.utils.Constants.API_URL;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

class Constants {
    static final String LOGIN_ENDPOINT = API_URL + "/signup";
    static final String REGISTER_ENDPOINT = API_URL + "/register";

    static final String EMAIL_PARAM_NAME = "email";
    static final String PASSWORD_PARAM_NAME = "password";
    static final String CONFIRM_PASSWORD_PARAM_NAME = "confirmPassword";
}
