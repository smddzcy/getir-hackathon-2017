package com.wow.wowmeet.screens.main.list;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.R;
import com.wow.wowmeet.adapters.EventListAdapter;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.View {

    private ListContract.Presenter presenter;

    @BindView(R.id.event_list) RecyclerView eventList;

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();

        return fragment;
    }

    public ListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);

        ListPresenter presenter = new ListPresenter(this);
        setPresenter(presenter);

        ArrayList<Event> events = new ArrayList<>();
        User user = new User.UserBuilder().setUserId("username").setUsername("email").setEmail("pass").createUser();
        Event event = new Event(user, new Location("Biga", 122, 123));
        for(int i = 0; i < 5; i++){
            events.add(event);
        }
        EventListAdapter eventListAdapter = new EventListAdapter(events);
        eventList.setLayoutManager(new LinearLayoutManager(getContext()));
        eventList.setAdapter(eventListAdapter);

        return v;
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void refreshList(ArrayList arr) {

    }
}
