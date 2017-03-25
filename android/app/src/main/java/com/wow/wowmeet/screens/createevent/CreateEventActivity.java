package com.wow.wowmeet.screens.createevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wow.wowmeet.R;

public class CreateEventActivity extends AppCompatActivity implements CreateEventContract.View {

    private CreateEventContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    @Override
    public void setPresenter(CreateEventContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }
}
