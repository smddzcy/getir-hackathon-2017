package com.wow.wowmeet.partials.dialogs;

import com.wow.wowmeet.data.EventTypeRepository;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class FilterDialogPresenter implements FilterDialogContract.Presenter {

    EventTypeRepository eventTypeRepository;

    public FilterDialogPresenter() {
        eventTypeRepository = new EventTypeRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
