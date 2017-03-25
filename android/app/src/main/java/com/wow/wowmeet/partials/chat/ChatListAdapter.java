package com.wow.wowmeet.partials.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>{


    private List<Message> messages;
    public ChatListAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public void changeDataSet(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vhView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
        return new ChatViewHolder(vhView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.textViewMessage.setText(message.getMessage());
        holder.textViewUsername.setText(message.getFrom().getName());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.row_chat_username)
        TextView textViewUsername;

        @BindView(R.id.row_chat_message)
        TextView textViewMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
