package com.wow.wowmeet.data.main;

import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.User;

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
    public void addEventTest() throws Exception {
        User u = new User.UserBuilder()
                .setUserId("1")
                .setUsername("KaracaSoft")
                .setEmail("coolcocuk@cool.com")
                .setPassword("asdf")
                .setToken("token")
                .createUser();

        Location loc = new Location("asdf", 23.59, 23.59);
        Event e = new Event(loc, "denem", u);

        mainRepository.addEvent(e).subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}