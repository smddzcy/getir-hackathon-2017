package com.wow.wowmeet.partials.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.screens.eventinfo.EventInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListContract.View {

    private ListContract.Presenter presenter;
    private EventListAdapter eventListAdapter;

    @BindView(R.id.swipeRefreshEventList) SwipeRefreshLayout swipeRefreshEventList;
    @BindView(R.id.event_list) RecyclerView eventList;

    private OnRefreshListRequestedListener onRefreshListRequestedListener;

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

        final ListPresenter presenter = new ListPresenter(this);
        setPresenter(presenter);

        presenter.start();
        ArrayList<Event> events = new ArrayList<>();

        eventListAdapter = new EventListAdapter(getContext(), events, presenter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        eventList.setLayoutManager(llm);
        eventList.setAdapter(eventListAdapter);

        swipeRefreshEventList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshListRequestedListener.onRefreshListRequested();
            }
        });

        return v;
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {

    }

    @Override
    public void showEvents(List<Event> events) {
        eventListAdapter.changeDataSet(events);
        eventList.scrollToPosition(eventListAdapter.getItemCount() - 1);
    }

    @Override
    public void showEventInfo(Event event) {
        Intent i = new Intent(getActivity(), EventInfoActivity.class);
        i.putExtra(EventInfoActivity.EXTRA_EVENT, event);
        startActivity(i);
    }

    @Override
    public void stopRefreshLayoutRefreshingAnimation() {
        swipeRefreshEventList.setRefreshing(false);
    }

    public void setOnRefreshListRequestedListener(OnRefreshListRequestedListener onRefreshListRequestedListener) {
        this.onRefreshListRequestedListener = onRefreshListRequestedListener;
    }

    public interface OnRefreshListRequestedListener {
        void onRefreshListRequested();
    }
}
