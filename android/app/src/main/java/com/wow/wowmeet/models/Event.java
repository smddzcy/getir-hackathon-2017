package com.wow.wowmeet.models;

import java.io.Serializable;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class Event implements Serializable {
    private String updatedAt;

    private String _id;

    private Location location;

    private String createdAt;

    private Type type;

    private User creator;

    public Event(Location location, Type type, User creator) {
        this.location = location;
        this.type = type;
        this.creator = creator;
    }

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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
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

    @Override
    public String toString() {
        return getLocation().toString();
    }
}
