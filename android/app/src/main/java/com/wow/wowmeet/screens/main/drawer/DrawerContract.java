package com.wow.wowmeet.screens.main.drawer;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

interface DrawerContract {

    interface View extends BaseView<DrawerContract.Presenter>{
        void goLogin();
    }

    interface Presenter extends BasePresenter {
        void onItemClick(int position, String action);
    }


}
