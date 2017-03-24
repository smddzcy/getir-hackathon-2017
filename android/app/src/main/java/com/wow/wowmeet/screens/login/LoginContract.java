package com.wow.wowmeet.screens.login;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        public void onLoginSuccess();
    }

    interface Presenter extends BasePresenter {
        public void onLoginClicked();
        public void onRegisterClicked();
    }


}
