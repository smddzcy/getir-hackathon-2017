package com.wow.wowmeet.screens.eventinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;

public class EventInfoActivity extends AppCompatActivity implements EventInfoContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
    }

    @Override
    public void setPresenter(EventInfoContract.Presenter presenter) {

    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showEventInfo(Event event) {

    }
}
