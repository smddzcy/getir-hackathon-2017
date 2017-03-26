package com.wow.wowmeet.partials.dialogs;

import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Type;

import java.util.List;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public interface FilterDialogContract {

    interface View extends BaseView<Presenter> {
        void updateEventTypes(List<Type> eventTypes);
    }

    interface Presenter extends BasePresenter{

    }
}
