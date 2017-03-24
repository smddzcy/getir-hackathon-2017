package com.wow.wowmeet.screens.main.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    private List<Event> eventList;

    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void changeDataSet(List<Event> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @Override
    public EventListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vhView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_list, parent, false);
        return new EventListViewHolder(vhView);
    }

    @Override
    public void onBindViewHolder(EventListViewHolder holder, int position) {
        Event event = eventList.get(position);

        //holder.imageViewProfilePhoto.setImageDrawable();
        holder.textViewPlaceName.setText(event.getLocationName());
        holder.textViewProfileName.setText(event.getUser().getUsername());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.row_event_list_imgProfilePhoto)
        ImageView imageViewProfilePhoto;

        @BindView(R.id.row_event_list_txtPlaceName)
        TextView textViewPlaceName;

        @BindView(R.id.row_event_list_txtProfileName)
        TextView textViewProfileName;

        EventListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
