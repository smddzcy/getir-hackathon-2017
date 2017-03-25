package com.wow.wowmeet.screens.createevent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Calendar;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class CreateEventPresenter implements CreateEventContract.Presenter {

    private CreateEventContract.View view;

    private Calendar dateTimePickerReference;

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateTimePickerReference.set(Calendar.HOUR_OF_DAY, hour);
            dateTimePickerReference.set(Calendar.MINUTE, minute);
            view.updateTimeField(hour, minute);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateTimePickerReference.set(year, month, day);
            view.updateDateField(day, month, year);
        }
    };

    public CreateEventPresenter(CreateEventContract.View view) {
        this.view = view;
        this.dateTimePickerReference = Calendar.getInstance();
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onCreateEvent() {

    }

    @Override
    public void onPlaceChooserClicked() {
        view.showPlaceAutocompleteDialog();
    }

    @Override
    public void onDateSelectorClicked() {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(dateTimePickerReference);
        datePickerFragment.setOnDateSetListener(onDateSetListener);
        view.showDialog(datePickerFragment, "DatePickerDialog");
    }

    @Override
    public void onTimeSelectorClicked() {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(dateTimePickerReference);
        timePickerFragment.setOnTimeSetListener(onTimeSetListener);
        view.showDialog(timePickerFragment, "TimePickerDialog");
    }

    @Override
    public void onChooseSuggestion() {

    }

    @Override
    public void onPlaceAutocompleteResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            view.updatePlaceField(data);
        } else if(resultCode == PlaceAutocomplete.RESULT_ERROR) {
            //TODO handle error
        } else if(resultCode == Activity.RESULT_CANCELED) {
            // TODO Do nothing?
        }
    }


}
