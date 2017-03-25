package com.wow.wowmeet.screens.createevent;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

interface CreateEventContract {


    interface View extends BaseView<CreateEventContract.Presenter> {
        void updateSuggestions();
    }

    interface Presenter extends BasePresenter{
        void onCreateEvent(/*TODO event bilgileri */);
        void onChoosePlace();
        void onDateSelectorClicked();
        void onTimeSelectorClicked();
        void onChooseSuggestion();
    }

}
