package com.wow.wowmeet.screens.eventinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.partials.chat.ChatFragment;
import com.wow.wowmeet.utils.CalendarUtils;
import com.wow.wowmeet.utils.DialogHelper;
import com.wow.wowmeet.utils.UserProvider;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventInfoActivity extends AppCompatActivity implements EventInfoContract.View{

    public static final String EXTRA_EVENT = "com.wow.wowmeet.screens.eventinfo.EventInfoActivity.event";

    private EventInfoContract.Presenter presenter;

    private Event event;
    private User user;

    @BindView(R.id.txtPlace) TextView txtPlace;
    @BindView(R.id.txtType) TextView txtType;
    @BindView(R.id.txtDateTime) TextView txtDateTime;
    @BindView(R.id.txtUsernameEventInfo) TextView txtUsername;
    @BindView(R.id.btnJoin) Button btnJoin;
    @BindView(R.id.activity_event_info_chatContainer)
    FrameLayout chatContainer;
    @BindView(R.id.txtJoinedUsers)
    TextView txtJoinedUsers;

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
        user = UserProvider.getInstance().getUser();

        presenter = new EventInfoPresenter(this, user, event);

        ChatFragment chatFragment = ChatFragment.newInstance(event);
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
        DialogHelper.showAlertDialogWithError(this, e);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
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
    public void showEventInfo(Event event, boolean isUserJoined) {
        this.event = event;
        txtPlace.setText(event.getLocation().getName());
        txtType.setText(event.getType().getName());
        txtUsername.setText(event.getCreator().getName());
        try {
            String datesString =
                    CalendarUtils.getStartEndDateString(event.getStartTime(), event.getEndTime());

            txtDateTime.setText(datesString);
        } catch (ParseException e) {
            e.printStackTrace();
            txtDateTime.setText(event.getStartTime());
        }

        if(isUserJoined){
            btnJoin.setVisibility(View.GONE);
            showJoinedUsers(event.getUsers());
            Log.d("EVENT", event.getUsers() + "");
        }

    }

    private void showJoinedUsers(List<User> users){
        txtJoinedUsers.setVisibility(View.VISIBLE);
        StringBuilder stringBuilder = new StringBuilder();
        for(User user : users){
            stringBuilder.append(user.getName());
        }

        String usersText = stringBuilder.toString();
        txtJoinedUsers.setText(usersText);

    }


}
