package com.wow.wowmeet.register;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;

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
