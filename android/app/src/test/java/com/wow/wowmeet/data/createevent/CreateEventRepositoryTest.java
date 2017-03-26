package com.wow.wowmeet.data.createevent;

import com.google.gson.Gson;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.Type;

import org.junit.Test;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

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

    @Test
    public void getTypes() throws Exception {
        CreateEventRepository createEventRepository = new CreateEventRepository();

        Single<List<Type>> single = createEventRepository.getTypes();

        single.subscribeWith(new DisposableSingleObserver<List<Type>>() {
            @Override
            public void onSuccess(List<Type> value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error" + e.getMessage());
            }
        });
    }

}