package com.wow.wowmeet.screens.createevent;

import android.content.Intent;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.location.places.Place;
import com.wow.wowmeet.base.BasePresenter;
import com.wow.wowmeet.base.BaseView;
import com.wow.wowmeet.models.Type;

import java.util.List;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

interface CreateEventContract {


    interface View extends BaseView<CreateEventContract.Presenter> {
        void updateSuggestions();
        void updateEventTypes(List<Type> eventTypes);

        void updateStartTimeField(int hour, int minute);
        void updateEndTimeField(int hour, int minute);
        void updateDateField(int day, int month, int year);
        Place updatePlaceField(Intent placeData);

        void showDialog(DialogFragment fragment, String tag);
        void showPlacePickerDialog();

        void onSuccess();
        void showPlacePickerError(Intent data);
    }

    interface Presenter extends BasePresenter{

        void onCreateEventClicked(Type eventType, String token);

        void onPlaceChooserClicked();
        void onDateSelectorClicked();
        void onStartTimeSelectorClicked();

        void onEndTimeSelectorClicked();

        void onChooseSuggestion();
        void onPlacePickerResult(int resultCode, Intent data);
    }

}
