package com.wow.wowmeet.data.chat;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */
public class ChatRepositoryTest {
    @Test
    public void sendMessage() throws Exception {
        ChatRepository chatRepository = new ChatRepository();
        Single<String> single = chatRepository.sendMessage("deneme", "58d620db28973c1d1429ebf0", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJnZXRpci1oYWNrYXRob24tMjAxNy13b3ctdGVhbS5oZXJva3VhcHAuY29tIiwic3ViIjoiNThkNWQxMGE2ZThjMGRiMTdlMTZlMDEzIiwiaWF0IjoxNDkwNDUxMDczLCJleHAiOjE0OTEwNTU4NzN9.Fna1w2Y-g5MvpWLHQoCiEP8tiZjyW1K_FNFC93kPxO0");

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