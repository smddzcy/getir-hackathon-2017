package com.wow.wowmeet.screens.createevent;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.wow.wowmeet.R;
import com.wow.wowmeet.partials.googleapi.GoogleApiProvider;
import com.wow.wowmeet.partials.googleapi.GooglePlacesAPIWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity implements CreateEventContract.View {

    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 0x0101;

    private CreateEventContract.Presenter presenter;

    @BindView(R.id.textInputCreateEventPlace) TextInputLayout textInputPlace;
    @BindView(R.id.textInputDate) TextInputLayout textInputDate;
    @BindView(R.id.textInputTime) TextInputLayout textInputTime;
    @BindView(R.id.spinnerActivityType) Spinner spinnerActivityType;
    @BindView(R.id.bestPlacesList) RecyclerView suggestionsList;
    @BindView(R.id.btnCreateEvent) Button btnCreateEvent;

    @BindView(R.id.edtPlace) EditText edtPlace;
    @BindView(R.id.edtTime) EditText edtTime;
    @BindView(R.id.edtDate) EditText edtDate;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    private GooglePlacesAPIWrapper placesAPIWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);


        final CreateEventPresenter presenter = new CreateEventPresenter(this);
        setPresenter(presenter);

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO create event parameters
                presenter.onCreateEvent();
            }
        });

        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.event_types_array, android.R.layout.simple_spinner_item);
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

        edtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    presenter.onTimeSelectorClicked();
                }
            }
        });

        edtPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    presenter.onPlaceChooserClicked();
                }
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDateSelectorClicked();
            }
        });

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTimeSelectorClicked();
            }
        });

        edtPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPlaceChooserClicked();
            }
        });

        edtDate.setKeyListener(null);
        edtTime.setKeyListener(null);
        edtPlace.setKeyListener(null);

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
                        //TODO show error
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        placesAPIWrapper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        placesAPIWrapper.onStop();
    }

    @Override
    public void setPresenter(CreateEventContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void updateSuggestions() {

    }

    @Override
    public void updateTimeField(int hour, int minute) {
        edtTime.setText(hour + ":" + minute);
    }

    @Override
    public void updateDateField(int day, int month, int year) {
        edtDate.setText(day + "/" + (month + 1) + "/" + year);
    }

    @Override
    public void updatePlaceField(Intent placeData) {
        Place place = PlaceAutocomplete.getPlace(this, placeData);
        edtPlace.setText(place.getName());
    }

    @Override
    public void showDialog(DialogFragment fragment, String tag) {
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void showPlaceAutocompleteDialog() {
        try {
            Intent placeAutocompleteIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(placeAutocompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
            //TODO handle error
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            //TODO handle error 2
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            presenter.onPlaceAutocompleteResult(resultCode, data);
        }
    }
}
