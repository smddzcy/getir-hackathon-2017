package com.wow.wowmeet.partials.chat;

import com.wow.wowmeet.models.Message;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public abstract class ChatMessageProvider implements Serializable {
    public abstract List<Message> getMessages();
}
