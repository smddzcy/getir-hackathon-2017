package com.wow.wowmeet.data.chat;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.socket.client.Socket;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */
public class ChatRepositoryTest {
    @Test
    public void socketTry() throws Exception {
        String roomId = "123";
        String message = "deneme";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnZXRpci1oYWNrYXRob24tMjAxNy13b3ctdGVhbS5oZXJva3VhcHAuY29tIiwic3ViIjoiNThkNWQxMGE2ZThjMGRiMTdlMTZlMDEzIiwiaWF0IjoxNDkwNDYxNTg4LCJleHAiOjE0OTEwNjYzODh9.Wn1DXQgR__UUpWyKHoMuaIqBu92QVyKLp9mW7IB_T4M";

        ChatRepository chatRepository = new ChatRepository();
        Socket socket = chatRepository.connectToSocket();
        chatRepository.socketListen(socket, roomId);
        chatRepository.socketTry(socket, roomId, message, token);
    }

    @Test
    public void sendMessage() throws Exception {
        ChatRepository chatRepository = new ChatRepository();
        Single<String> single = chatRepository.sendMessage("deneme", "58d6ab7ea38b0ce3938f1d7a", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnZXRpci1oYWNrYXRob24tMjAxNy13b3ctdGVhbS5oZXJva3VhcHAuY29tIiwic3ViIjoiNThkNWQxMGE2ZThjMGRiMTdlMTZlMDEzIiwiaWF0IjoxNDkwNDUxMDczLCJleHAiOjE0OTEwNTU4NzN9.Fna1w2Y-g5MvpWLHQoCiEP8tiZjyW1K_FNFC93kPxO0");

        single.subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

}