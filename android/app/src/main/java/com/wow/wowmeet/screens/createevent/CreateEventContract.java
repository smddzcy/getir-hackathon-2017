package com.wow.wowmeet.screens.createevent;

import android.content.Intent;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.location.places.Place;
import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

interface CreateEventContract {


    interface View extends BaseView<CreateEventContract.Presenter> {
        void updateSuggestions();

        void updateTimeField(int hour, int minute);
        void updateDateField(int day, int month, int year);
        Place updatePlaceField(Intent placeData);

        void showDialog(DialogFragment fragment, String tag);
        void showPlacePickerDialog();
    }

    interface Presenter extends BasePresenter{
        void onCreateEvent(/*TODO event bilgileri */);
        void onPlaceChooserClicked();
        void onDateSelectorClicked();
        void onTimeSelectorClicked();
        void onChooseSuggestion();
        void onPlacePickerResult(int resultCode, Intent data);
    }

}
