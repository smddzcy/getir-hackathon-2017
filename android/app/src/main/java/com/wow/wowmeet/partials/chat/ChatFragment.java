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
import com.wow.wowmeet.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatFragment extends Fragment implements ChatContract.View {


    private ChatContract.Presenter presenter;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(ChatMessageProvider chatMessageProvider) {
        ChatFragment fragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("deneme", chatMessageProvider);
        fragment.setArguments(bundle);
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
        String userToken = SharedPreferencesUtil.getInstance(getContext()).getUserToken();
        ChatMessageProvider chatMessageProvider = (ChatMessageProvider) getArguments().getSerializable("deneme");
        List<Message> messages = chatMessageProvider.getMessages();
        ChatContract.Presenter presenter = new ChatPresenter(this, messages, userToken);
        this.setPresenter(presenter);


        messages = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(messages);
        chatRecyclerView.setAdapter(chatListAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String e) {
        //TODO SHOW ERROR
    }

    @Override
    public void showMessages(List<Message> messages) {
        chatListAdapter.changeDataSet(messages);
    }

    @Override
    public void showNewMessage(Message message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
