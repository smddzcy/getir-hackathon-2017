package com.wow.wowmeet.models;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class Message {

    User fromUser;
    String toId;
    String message;

    public Message(User fromUser, String toId, String message) {
        this.fromUser = fromUser;
        this.toId = toId;
        this.message = message;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
