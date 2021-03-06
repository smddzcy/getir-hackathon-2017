package com.wow.wowmeet.data.createevent;

import com.google.gson.Gson;
import com.wow.wowmeet.exceptions.ResponseUnsuccessfulException;
import com.wow.wowmeet.models.Event;
import com.wow.wowmeet.models.Location;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final String PARAM_TYPE_NAME = "type";
    private static final String PARAM_START_TIME = "startTime";
    private static final String PARAM_END_TIME = "endTime";
    private static final String PARAM_LOCATION = "location";

    public Single<String> createEvent(final Event event, final String userToken) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                String jsonString = getJsonStringForEvent(event);
                OkHttpClient client = new OkHttpClient();

                Response response =
                        OkHttpUtils.makePostRequestBodyJsonWithJUser(client, EVENT_ENDPOINT, jsonString, userToken);

                String responseBody = response.body().string();

                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    ResponseUnsuccessfulException exception = new ResponseUnsuccessfulException(responseBody);
                    e.onError(exception);
                }
            }
        });


    }

    String getJsonStringForEvent(Event event) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();

        Type eventType = event.getType();
        String startTime = event.getStartTime();
        String endTime = event.getEndTime();
        Location location = event.getLocation();

        String locationJson = gson.toJson(location);

        JSONObject jsonLocationObject = new JSONObject(locationJson);
        jsonObject.put(PARAM_TYPE_NAME, eventType.get_id());
        jsonObject.put(PARAM_START_TIME, startTime);
        jsonObject.put(PARAM_END_TIME, endTime);
        jsonObject.put(PARAM_LOCATION, jsonLocationObject);


        return jsonObject.toString();
    }

}
