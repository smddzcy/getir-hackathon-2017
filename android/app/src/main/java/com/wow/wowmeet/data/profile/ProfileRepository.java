package com.wow.wowmeet.data.profile;

import com.wow.wowmeet.models.User;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ProfileRepository {

    public Single<User> getUserDetails(User user){
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                     
            }
        });
    }
}
