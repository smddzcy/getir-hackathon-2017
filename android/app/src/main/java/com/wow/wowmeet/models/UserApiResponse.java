package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class UserApiResponse {
    private String token;

    private User user;

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", user = "+user+"]";
    }
}
