package com.wow.wowmeet.models;

import com.wow.wowmeet.partials.chat.ChatMessageProvider;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class Event extends ChatMessageProvider implements Serializable {

    private String _id;

    private Location location;

    private Type type;

    private User creator;

    private List<Message> messages;

    private String startTime;

    private String endTime;

    private List<User> users;

    public Event(Location location, List<Message> messages, Type type, User creator, String startTime, String endTime, List<User> users) {
        this.location = location;
        this.type = type;
        this.creator = creator;
        this.messages = messages;
        this.startTime = startTime;
        this.endTime = endTime;
        this.users = users;
    }

    public Event(User creator, Type type, String startTime, String endTime, Location location){
        this.creator = creator;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }


    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public User getCreator()
    {
        return creator;
    }

    public void setCreator(User creator)
    {
        this.creator = creator;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return getLocation().toString();
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String getToId() {
        return get_id();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
