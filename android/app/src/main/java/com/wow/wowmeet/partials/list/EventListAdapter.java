package com.wow.wowmeet.partials.list;

import android.support.v7.widget.CardView;
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
    private ListEventClickListener eventClickListener;

    public EventListAdapter(List<Event> eventList, ListEventClickListener eventClickListener) {
        this.eventList = eventList;
        this.eventClickListener = eventClickListener;
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
        holder.textViewPlaceName.setText(event.getLocation().getName());
        holder.textViewProfileName.setText(event.getCreator().getEmail());
        holder.textViewType.setText(event.getType().getName());
        holder.textViewDate.setText(event.getStartTime());
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

        @BindView(R.id.row_event_list_txtType)
        TextView textViewType;

        @BindView(R.id.row_event_list_txtDate)
        TextView textViewDate;

        @BindView(R.id.row_event_list_cardView)
        CardView cardView;

        EventListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Event event = eventList.get(getAdapterPosition());
                    eventClickListener.onEventClicked(event);
                }
            });
        }

    }
}
