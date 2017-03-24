package com.wow.wowmeet.screens.main.list;

import com.wow.wowmeet.BasePresenter;
import com.wow.wowmeet.BaseView;

import java.util.ArrayList;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

interface ListContract {

    interface View extends BaseView<ListContract.Presenter> {
        void refreshList(ArrayList arr);
    }

    interface Presenter extends BasePresenter {
        void onSwipeRefresh();
    }

}
