package com.wow.wowmeet.utils;

import com.wow.wowmeet.models.User;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class UserProvider {
    private static final UserProvider ourInstance = new UserProvider();

    public static UserProvider getInstance() {
        return ourInstance;
    }

    private UserProvider() {
    }

    private User user;

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }
}
