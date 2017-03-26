package com.wow.wowmeet.data.createevent;

import com.google.gson.Gson;
import com.wow.wowmeet.models.Location;

import org.junit.Test;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */
public class CreateEventRepositoryTest {
    @Test
    public void getFieldsMapForEvent() throws Exception {
        Location location = new Location("deneme", 123, 123);

        String locationJson = new Gson().toJson(location);

        System.out.println(locationJson);
    }


}