package com.wow.wowmeet.models;

import java.io.Serializable;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class User implements Serializable {
    private String updatedAt;

    private String _id;

    private String email;

    private String[] events;

    private String createdAt;

    private String[] messages;

    private String token;

    private String name;

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String[] getEvents ()
    {
        return events;
    }

    public void setEvents (String[] events)
    {
        this.events = events;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }


    public String[] getMessages ()
    {
        return messages;
    }

    public void setMessages (String[] messages)
    {
        this.messages = messages;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [updatedAt = "+updatedAt+", _id = "+_id+", email = "+email+", " +
                "events = "+events+", createdAt = "+createdAt+", messages = "+messages+"]";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
