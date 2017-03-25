package com.wow.wowmeet.models;

import java.util.ArrayList;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class WowException {

    private String msg;
    private ArrayList<String> messages;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
