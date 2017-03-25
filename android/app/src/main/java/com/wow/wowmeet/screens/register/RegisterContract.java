package com.wow.wowmeet.screens.register;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.User;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void goMainWithUser(User user);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        void onRegisterClicked(String usernameText, String emailText, String passwordText);
    }
}
