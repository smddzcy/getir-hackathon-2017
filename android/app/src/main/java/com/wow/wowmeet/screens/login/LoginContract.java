package com.wow.wowmeet.screens.login;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.User;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        public void onLoginSuccess(User user);

        void goRegister();

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends BasePresenter {
        public void onLoginClicked(String email, String password);
        public void onRegisterClicked();
    }


}
