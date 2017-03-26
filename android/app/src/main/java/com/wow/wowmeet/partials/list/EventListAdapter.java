package com.wow.wowmeet.partials.list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.utils.CalendarUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    private List<Event> eventList;
    private ListEventClickListener eventClickListener;
    Context context;

    public EventListAdapter(Context context, List<Event> eventList, ListEventClickListener eventClickListener) {
        this.context = context;
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


        if(event.getCreator() != null){
            holder.textViewProfileName.setText(event.getCreator().getName());
            Picasso.with(context).load(event.getCreator().getPicture()).into(holder.imageViewProfilePhoto);
        }

        try {
            Date dateStart = CalendarUtils.stringToDate(event.getStartTime());
            Date dateEnd = CalendarUtils.stringToDate(event.getEndTime());
            String startString = CalendarUtils.dateToPrettyDateString(dateStart);
            String endString = CalendarUtils.dateToPrettyDateString(dateEnd);

            String datesString = startString + " - " + endString;
            holder.textViewDate.setText(datesString);
        } catch (ParseException e) {
            holder.textViewDate.setText(event.getStartTime());
            e.printStackTrace();
        }

        holder.textViewPlaceName.setText(event.getLocation().getName());
        holder.textViewType.setText(event.getType().getName());
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
