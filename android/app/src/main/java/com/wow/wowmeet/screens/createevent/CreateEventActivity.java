package com.wow.wowmeet.screens.createevent;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
}
