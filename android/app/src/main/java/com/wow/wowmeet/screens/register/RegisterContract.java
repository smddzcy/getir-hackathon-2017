package com.wow.wowmeet.screens.register;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccessful();
    }

    interface Presenter extends BasePresenter {
        void onRegisterClicked();
    }
}
