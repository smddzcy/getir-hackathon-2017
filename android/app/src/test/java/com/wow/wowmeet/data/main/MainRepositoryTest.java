package com.wow.wowmeet.data.main;

import com.wow.wowmeet.models.Event;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

import static org.junit.Assert.assertFalse;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */
public class MainRepositoryTest {

    MainRepository mainRepository;

    @Before
    public void setUp() throws Exception {
        mainRepository = new MainRepository();
    }

    @Test
    public void getEvents() throws Exception {
        mainRepository.getEvents().subscribeWith(new DisposableSingleObserver<List<Event>>() {
            @Override
            public void onSuccess(List<Event> value) {
                assertFalse(value.isEmpty());
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    @Test
    public void getEventsLatLngRad() throws Exception {
        int lat = 10;
        int lng = 10;
        int rad = 2;

        mainRepository.getEvents(lat, lng, rad).subscribeWith(new DisposableSingleObserver<List<Event>>() {
            @Override
            public void onSuccess(List<Event> value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }
        });

    }

}