package com.wow.wowmeet.partials.chat;

import android.util.Log;

import com.wow.wowmeet.data.chat.ChatRepository;
import com.wow.wowmeet.models.Message;

import java.net.URISyntaxException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatRepository chatRepository;

    private DisposableSingleObserver<String> disposableSingleChatMessageObserver;
    private DisposableObserver<Message> disposableSocketObserver;
    private ChatContract.View view;
    private String userToken;
    private List<Message> messages;

    public ChatPresenter(ChatContract.View view, String toId, List<Message> messages,
                         String userToken) {
        this.messages = messages;
        this.view = view;
        try {
            this.chatRepository = new ChatRepository(toId);
        } catch (URISyntaxException e) {
            view.showError(e.getLocalizedMessage());
        }
        this.userToken = userToken;
    }

    @Override
    public void start() {
        view.showMessages(messages);

        Observable<Message> socketObservable = chatRepository.socketListen();

        disposableSocketObserver = socketObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Message>() {
            @Override
            public void onNext(Message value) {
                view.showNewMessage(value);
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void stop() {
        if(disposableSingleChatMessageObserver != null)
            disposableSingleChatMessageObserver.dispose();

        if(disposableSocketObserver != null)
            disposableSocketObserver.dispose();

        chatRepository.socketDisconnect();
    }

    @Override
    public void sendMessage(String messageText, String toId) {
        Single<String> sendMessageSingle = chatRepository.sendMessage(messageText, toId, userToken);
        disposableSingleChatMessageObserver = sendMessageSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String value) {
                        Log.d("MessageSend", value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }
}
