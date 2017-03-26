package com.wow.wowmeet.screens.createevent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.wow.wowmeet.R;
import com.wow.wowmeet.data.EventTypeRepository;
import com.wow.wowmeet.data.createevent.CreateEventRepository;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.partials.dialogs.DatePickerFragment;
import com.wow.wowmeet.partials.dialogs.TimePickerFragment;
import com.wow.wowmeet.utils.CalendarUtils;

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

    private Calendar dateStartTimePickerReference;

    private Calendar dateEndTimePickerReference;

    private Place pickedPlace;

    private CreateEventRepository createEventRepository;
    private EventTypeRepository eventTypeRepository;

    private DisposableSingleObserver<List<Type>> disposableSingleEventTypesObserver;
    private DisposableSingleObserver<String> disposableSingleCreateEventObserver;

    private TimePickerDialog.OnTimeSetListener onStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateStartTimePickerReference.set(Calendar.HOUR_OF_DAY, hour);
            dateStartTimePickerReference.set(Calendar.MINUTE, minute);
            view.updateStartTimeField(hour, minute);
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            dateStartTimePickerReference.set(year, month, day);
            dateEndTimePickerReference.set(year, month, day);
            view.updateDateField(day, month, year);
        }
    };

    private TimePickerDialog.OnTimeSetListener onEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            dateEndTimePickerReference.set(Calendar.HOUR_OF_DAY, hour);
            dateEndTimePickerReference.set(Calendar.MINUTE, minute);
            view.updateEndTimeField(hour, minute);
        }
    };


    public CreateEventPresenter(CreateEventContract.View view) {
        this.view = view;
        this.dateStartTimePickerReference = Calendar.getInstance();
        this.dateEndTimePickerReference = Calendar.getInstance();
        this.createEventRepository = new CreateEventRepository();
        this.eventTypeRepository = new EventTypeRepository();
    }


    @Override
    public void start() {
        Single<List<Type>> singleTypes = eventTypeRepository.getTypes();

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

    //EventType, ISO - StartTime, ISO - EndTime, Location
    @Override
    public void onCreateEventClicked(Type eventType, String token) {
        Location location = getLocationFromPlace(pickedPlace);
        String startTime = CalendarUtils.calendarToDateString(dateStartTimePickerReference);
        String endTime = CalendarUtils.calendarToDateString(dateEndTimePickerReference);
        Event event = new Event(eventType, startTime, endTime, location);
        Single<String> singleCreateEvent = createEventRepository.createEvent(event, token);

        disposableSingleCreateEventObserver = singleCreateEvent
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String value) {
                        view.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.showError(e.getMessage());
                    }
                });
    }

    private Location getLocationFromPlace(Place place){
        LatLng placeLatLng = place.getLatLng();
        double lat = placeLatLng.latitude;
        double lng = placeLatLng.longitude;
        String placeName = place.getName().toString();
        return new Location(placeName, lat, lng);
    }

    @Override
    public void onPlaceChooserClicked() {
        view.showPlacePickerDialog();
    }

    @Override
    public void onDateSelectorClicked() {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(dateStartTimePickerReference);
        datePickerFragment.setOnDateSetListener(onDateSetListener);
        view.showDialog(datePickerFragment, "DatePickerDialog");
    }

    @Override
    public void onStartTimeSelectorClicked() {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(dateStartTimePickerReference);
        timePickerFragment.setOnTimeSetListener(onStartTimeSetListener);
        view.showDialog(timePickerFragment, "TimePickerDialog");
    }

    @Override
    public void onEndTimeSelectorClicked() {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(dateEndTimePickerReference);
        timePickerFragment.setOnTimeSetListener(onEndTimeSetListener);
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
            view.showPlacePickerError(data);
        } else if(resultCode == Activity.RESULT_CANCELED) {
            view.showError(R.string.place_selection_cancelled_info_text);
        }
    }

}
