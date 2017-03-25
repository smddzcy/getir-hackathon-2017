package com.wow.wowmeet.screens.createevent;

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

import com.wow.wowmeet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity implements CreateEventContract.View {

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

        edtDate.setKeyListener(null);
        edtTime.setKeyListener(null);

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
    public void showDialog(DialogFragment fragment, String tag) {
        fragment.show(getSupportFragmentManager(), tag);
    }
}
