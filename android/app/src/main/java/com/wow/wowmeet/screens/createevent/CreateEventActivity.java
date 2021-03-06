package com.wow.wowmeet.screens.createevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.utils.googleapi.GoogleApiProvider;
import com.wow.wowmeet.utils.googleapi.GooglePlacesAPIWrapper;
import com.wow.wowmeet.utils.DialogHelper;
import com.wow.wowmeet.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity implements CreateEventContract.View {

    public static final int PLACE_PICKER_REQUEST_CODE = 0x0101;

    private CreateEventContract.Presenter presenter;

    @BindView(R.id.textInputCreateEventPlace) TextInputLayout textInputPlace;
    @BindView(R.id.textInputDate) TextInputLayout textInputDate;
    @BindView(R.id.textInputStartTime) TextInputLayout textInputTime;
    @BindView(R.id.spinnerActivityType) Spinner spinnerActivityType;
    @BindView(R.id.bestPlacesList) RecyclerView suggestionsList;
    @BindView(R.id.btnCreateEvent) Button btnCreateEvent;

    @BindView(R.id.edtPlace) EditText edtPlace;
    @BindView(R.id.edtStartTime) EditText edtStartTime;
    @BindView(R.id.edtEndTime) EditText edtEndTime;
    @BindView(R.id.edtDate) EditText edtDate;

    private ArrayAdapter<Type> spinnerAdapter;

    private GooglePlacesAPIWrapper placesAPIWrapper;
    private List<Type> eventTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);


        final CreateEventPresenter presenter = new CreateEventPresenter(this);
        setPresenter(presenter);

        final String token = SharedPreferencesUtil.getInstance(this).getUserToken();

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type eventType = (Type)spinnerActivityType.getSelectedItem();
                presenter.onCreateEventClicked(eventType, token);
            }
        });
        eventTypes = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter<Type>(this, android.R.layout.simple_spinner_item, eventTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerActivityType.setAdapter(spinnerAdapter);

        edtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    presenter.onDateSelectorClicked();
                }
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDateSelectorClicked();
            }
        });

        edtStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    presenter.onStartTimeSelectorClicked();
                }
            }
        });

        edtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onStartTimeSelectorClicked();
            }
        });

        edtEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    presenter.onEndTimeSelectorClicked();
                }
            }
        });

        edtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEndTimeSelectorClicked();
            }
        });


        edtPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPlaceChooserClicked();
            }
        });

        edtDate.setKeyListener(null);
        edtStartTime.setKeyListener(null);
        edtPlace.setKeyListener(null);
        edtEndTime.setKeyListener(null);

        edtPlace.setEnabled(false);

        placesAPIWrapper = new GooglePlacesAPIWrapper(this,
                new GoogleApiProvider.OnProviderConnectedListener() {
                    @Override
                    public void onConnected() {
                        edtPlace.setEnabled(true);
                    }
                },
                new GoogleApiProvider.OnProviderConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed() {
                        showError(R.string.api_conn_failed_error_text);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        placesAPIWrapper.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        placesAPIWrapper.onStop();
        presenter.stop();
    }

    @Override
    public void setPresenter(CreateEventContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {
        DialogHelper.showAlertDialogWithError(this, e);
    }

    @Override
    public void showError(@StringRes int resource) {
        showError(getString(resource));
    }

    @Override
    public void updateSuggestions() {

    }

    @Override
    public void updateEventTypes(List<Type> eventTypes) {
        this.eventTypes.clear();
        this.eventTypes.addAll(eventTypes);
        spinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateStartTimeField(int hour, int minute) {
        edtStartTime.setText(getTimeText(hour, minute));
    }

    @Override
    public void updateEndTimeField(int hour, int minute) {
        edtEndTime.setText(getTimeText(hour, minute));
    }


    private String getTimeText(int hour, int minute){
        return ((hour < 10) ? "0" : "") + hour + ":" + ((minute < 10) ? "0" : "") + minute;
    }

    @Override
    public void updateDateField(int day, int month, int year) {
        edtDate.setText(day + "/" + (month + 1) + "/" + year);
    }

    @Override
    public Place updatePlaceField(Intent placeData) {
        Place place = PlacePicker.getPlace(this, placeData);
        edtPlace.setText(place.getName());

        return place;
    }

    @Override
    public void showDialog(DialogFragment fragment, String tag) {
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void showPlacePickerDialog() {
        try {
            Intent placeAutocompleteIntent = new PlacePicker.IntentBuilder()
                    .build(this);
            startActivityForResult(placeAutocompleteIntent, PLACE_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            DialogHelper.showAlertDialogWithError(this, e.getLocalizedMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            DialogHelper.showAlertDialogWithError(this, e.getLocalizedMessage());
        }

    }

    @Override
    public void showPlacePickerError(Intent data) {
        showError(PlacePicker.getStatus(this, data).getStatusMessage());
    }

    @Override
    public void onSuccess() {
        this.finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST_CODE) {
            presenter.onPlacePickerResult(resultCode, data);
        }
    }
}
