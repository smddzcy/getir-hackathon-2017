package com.wow.wowmeet.partials.chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatFragment extends Fragment {



    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        //bundle.putSerializable("deneme", messages);
        return fragment;
    }

    @BindView(R.id.fragment_chat_rv)
    RecyclerView chatRecyclerView;

    ChatListAdapter chatListAdapter;

    private ArrayList<Message> messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, rootView);

        messages = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(messages);
        chatRecyclerView.setAdapter(chatListAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

}
