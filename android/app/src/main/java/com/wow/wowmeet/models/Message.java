package com.wow.wowmeet.models;

import java.io.Serializable;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class Message implements Serializable{
    String _id;
    User from;
    String to;
    String message;

    public Message(User from, String _id, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
        this._id = _id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
