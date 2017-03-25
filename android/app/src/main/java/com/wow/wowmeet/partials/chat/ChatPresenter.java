package com.wow.wowmeet.partials.chat;

import com.wow.wowmeet.data.chat.ChatRepository;

import java.net.URISyntaxException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Socket;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatRepository chatRepository;

    private DisposableSingleObserver<String> disposableSingleChatMessageObserver;
    private ChatContract.View view;
    private String userToken;

    public ChatPresenter(ChatContract.View view, String userToken) {
        this.chatRepository = new ChatRepository();
        this.view = view;
        this.userToken = userToken;
    }

    @Override
    public void start() {
        try {
            Socket socket = chatRepository.connectToSocket();
            chatRepository.socketListen(socket, "58d5d1206e8c0db17e16e014");
            chatRepository.socketTry(socket, "58d5d1206e8c0db17e16e014", "mesaj", userToken);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        if(disposableSingleChatMessageObserver != null)
            disposableSingleChatMessageObserver.dispose();
    }

    @Override
    public void sendMessage(String messageText, String toId) {
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
