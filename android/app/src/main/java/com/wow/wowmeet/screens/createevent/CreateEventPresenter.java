package com.wow.wowmeet.screens.createevent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wow.wowmeet.data.createevent.CreateEventRepository;
import com.wow.wowmeet.models.Type;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/25/17.
 */

public class CreateEventPresenter implements CreateEventContract.Presenter {

    private CreateEventContract.View view;

    private Calendar dateTimePickerReference;

    private Place pickedPlace;

    private CreateEventRepository createEventRepository;

    private DisposableSingleObserver<List<Type>> disposableSingleEventTypesObserver;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateTimePickerReference.set(Calendar.HOUR_OF_DAY, hour);
            dateTimePickerReference.set(Calendar.MINUTE, minute);
            view.updateTimeField(hour, minute);
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateTimePickerReference.set(year, month, day);
            view.updateDateField(day, month, year);
        }
    };

    public CreateEventPresenter(CreateEventContract.View view) {
        this.view = view;
        this.dateTimePickerReference = Calendar.getInstance();
        this.createEventRepository = new CreateEventRepository();
    }


    @Override
    public void start() {
        Single<List<Type>> singleTypes = createEventRepository.getTypes();

        disposableSingleEventTypesObserver = singleTypes.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Type>>() {
                    @Override
                    public void onSuccess(List<Type> value) {
                        view.updateEventTypes(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void stop() {
        if(disposableSingleEventTypesObserver != null)
            disposableSingleEventTypesObserver.dispose();
    }

    @Override
    public void onCreateEvent() {

    }

    @Override
    public void onPlaceChooserClicked() {
        view.showPlacePickerDialog();
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
    public void onPlacePickerResult(int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Place place = view.updatePlaceField(data);
            this.pickedPlace = place;
        } else if(resultCode == PlacePicker.RESULT_ERROR) {
            //TODO handle error
        } else if(resultCode == Activity.RESULT_CANCELED) {
            // TODO Do nothing?
        }
    }

}
