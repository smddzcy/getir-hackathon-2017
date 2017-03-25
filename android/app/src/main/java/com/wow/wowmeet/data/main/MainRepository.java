package com.wow.wowmeet.data.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wow.wowmeet.exceptions.AddEventFailedException;
import com.wow.wowmeet.exceptions.GetEventsFailedException;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class MainRepository implements EventsRepository {

    private OkHttpClient client;

    public MainRepository() {
        this.client = new OkHttpClient();
    }

    @Override
    public Single<List<Event>> getEvents() {
        return Single.create(new SingleOnSubscribe<List<Event>>() {
            @Override
            public void subscribe(SingleEmitter<List<Event>> e) throws Exception {
                Response response = OkHttpUtils.makeGetRequest(client, EventsConstants.EVENTS_ENDPOINT);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    Type eventsType = new TypeToken<ArrayList<Event>>(){}.getType();
                    List<Event> events = gson.fromJson(responseBody, eventsType);
                    e.onSuccess(events);
                }else{
                    GetEventsFailedException getEventsFailedException = new GetEventsFailedException(responseBody);
                    e.onError(getEventsFailedException);
                }
            }
        });
    }

    @Override
    public Single<String> addEvent(final Event event) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                User user = event.getCreator();

                Gson gson = new Gson();
                String json = gson.toJson(event);

                Response response = OkHttpUtils.makePostRequestWithUserJson(client, EventsConstants.EVENTS_ENDPOINT, json, user);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    AddEventFailedException addEventFailedException = new AddEventFailedException(responseBody);
                    e.onError(addEventFailedException);
                }
            }
        });
    }

    @Override
    public Single<String> removeEvent(Event event) {
        return null;
    }

    @Override
    public Single<Event> getSingleEvent(String eventId) {
        return null;
    }
}
