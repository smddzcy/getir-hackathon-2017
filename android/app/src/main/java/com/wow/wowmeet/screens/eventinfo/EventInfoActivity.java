package com.wow.wowmeet.screens.eventinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.partials.chat.ChatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventInfoActivity extends AppCompatActivity implements EventInfoContract.View {

    public static final String EXTRA_EVENT = "com.wow.wowmeet.screens.eventinfo.EventInfoActivity.event";

    private EventInfoContract.Presenter presenter;

    private Event event;

    @BindView(R.id.txtPlace) TextView txtPlace;
    @BindView(R.id.txtType) TextView txtType;
    @BindView(R.id.txtDateTime) TextView txtDateTime;
    @BindView(R.id.txtUsernameEventInfo) TextView txtUsername;
    @BindView(R.id.btnJoin) Button btnJoin;
    @BindView(R.id.activity_event_info_chatContainer)
    FrameLayout chatContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        event = (Event) i.getSerializableExtra(EXTRA_EVENT);
        showEventInfo(event);

        presenter = new EventInfoPresenter(this);

        ChatFragment chatFragment = ChatFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.activity_event_info_chatContainer, chatFragment).commit();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onJoinClicked();
            }
        });

    }

    @Override
    public void setPresenter(EventInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEventInfo(Event event) {
        this.event = event;
        txtPlace.setText(event.getLocation().getName());
        txtType.setText(event.getType().getName());
        txtDateTime.setText(event.getCreatedAt());
        txtUsername.setText(event.getCreator().getName());
    }
}
