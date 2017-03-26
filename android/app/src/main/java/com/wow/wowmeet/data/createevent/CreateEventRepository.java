package com.wow.wowmeet.data.createevent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wow.wowmeet.exceptions.CreateEventGetTypesFailedException;
import com.wow.wowmeet.exceptions.ResponseUnsuccessfulException;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class CreateEventRepository {

    private static final String EVENT_TYPES_ENDPOINT = Constants.API_URL + "/event-type";
    private static final String EVENT_ENDPOINT = Constants.API_URL + "/event";

    private static final String PARAM_CREATOR_NAME = "creator";
    private static final String PARAM_TYPE_NAME = "type";
    private static final String PARAM_START_TIME = "start_time";
    private static final String PARAM_END_TIME = "end_time";
    private static final String PARAM_LOCATION = "location";

    public Single<List<Type>> getTypes(){
        return Single.create(new SingleOnSubscribe<List<Type>>() {
            @Override
            public void subscribe(SingleEmitter<List<Type>> e) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();
                Response response = OkHttpUtils.makeGetRequest(okHttpClient, EVENT_TYPES_ENDPOINT);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    java.lang.reflect.Type typesType = new TypeToken<List<Type>>(){}.getType();
                    List<Type> eventTypes = gson.fromJson(responseBody, typesType);
                    e.onSuccess(eventTypes);
                }else{
                    CreateEventGetTypesFailedException exception = new CreateEventGetTypesFailedException(responseBody);
                    e.onError(exception);
                }
            }
        });
    }

    public Single<String> createEvent(final Event event, final String userToken) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                Map<String, String> fields = getFieldsMapForEvent(event);
                OkHttpClient client = new OkHttpClient();

                Response response =
                        OkHttpUtils.makePostRequestWithUser(client, EVENT_ENDPOINT, fields, userToken);

                String responseBody = response.body().toString();

                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    ResponseUnsuccessfulException exception = new ResponseUnsuccessfulException(responseBody);
                    e.onError(exception);
                }
            }
        });


    }

    private Map<String, String> getFieldsMapForEvent(Event event){
        Gson gson = new Gson();

        User creator = event.getCreator();
        Type eventType = event.getType();
        String startTime = event.getStartTime();
        String endTime = event.getEndTime();
        Location location = event.getLocation();

        String creatorJson = gson.toJson(creator);
        String eventTypeJson = gson.toJson(eventType);
        String locationJson = gson.toJson(location);

        HashMap<String, String> fields = new HashMap<>();
        fields.put(PARAM_CREATOR_NAME, creatorJson);
        fields.put(PARAM_TYPE_NAME, eventTypeJson);
        fields.put(PARAM_START_TIME, startTime);
        fields.put(PARAM_END_TIME, endTime);
        fields.put(PARAM_LOCATION, locationJson);

        return fields;
    }

}
