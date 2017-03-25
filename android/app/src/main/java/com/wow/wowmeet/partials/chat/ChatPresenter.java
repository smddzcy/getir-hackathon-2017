package com.wow.wowmeet.partials.chat;

import com.wow.wowmeet.data.chat.ChatRepository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatRepository chatRepository;

    private DisposableSingleObserver<String> disposableSingleChatMessageObserver;
    private ChatContract.View view;

    public ChatPresenter(ChatContract.View view) {
        this.chatRepository = new ChatRepository();
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if(disposableSingleChatMessageObserver != null)
            disposableSingleChatMessageObserver.dispose();
    }

    @Override
    public void sendMessage(String messageText, String toId, String userToken) {
        Single<String> sendMessageSingle = chatRepository.sendMessage(messageText, toId, userToken);
        disposableSingleChatMessageObserver = sendMessageSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                    }
                });
    }
}
