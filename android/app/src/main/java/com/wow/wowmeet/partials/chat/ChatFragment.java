package com.wow.wowmeet.partials.chat;


import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.wow.wowmeet.R;
import com.wow.wowmeet.models.Message;
import com.wow.wowmeet.utils.DialogHelper;
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

    @BindView(R.id.fragment_chat_edtText)
    EditText editTextMessage;

    @BindView(R.id.fragment_chat_sendButton)
    Button buttonSend;

    ChatListAdapter chatListAdapter;


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
        final String toId = chatMessageProvider.getToId();


        ChatContract.Presenter presenter = new ChatPresenter(this, toId, messages, userToken);
        this.setPresenter(presenter);

        chatListAdapter = new ChatListAdapter(new ArrayList<Message>());
        chatRecyclerView.setAdapter(chatListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        chatRecyclerView.setLayoutManager(linearLayoutManager);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                editTextMessage.setText("");
                ChatFragment.this.presenter.sendMessage(message, toId);
            }
        });

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
        DialogHelper.showAlertDialogWithError(getActivity(), e);
    }

    @Override
    public void showError(@StringRes int resource) {
        showError(getString(resource));
    }

    @Override
    public void showMessages(List<Message> messages) {
        chatListAdapter.changeDataSet(messages);
        chatRecyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);
    }

    @Override
    public void showNewMessage(Message message) {
        chatListAdapter.addItem(message);
        chatRecyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
