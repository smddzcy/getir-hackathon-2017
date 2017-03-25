package com.wow.wowmeet.data.createevent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wow.wowmeet.exceptions.CreateEventGetTypesFailedException;
import com.wow.wowmeet.models.Type;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.List;

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

}
