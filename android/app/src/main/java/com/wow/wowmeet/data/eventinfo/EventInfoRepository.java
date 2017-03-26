package com.wow.wowmeet.data.eventinfo;

import com.wow.wowmeet.exceptions.ResponseUnsuccessfulException;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 26.03.2017.
 */

public class EventInfoRepository {

    private String EVENT_ENDPOINT = Constants.API_URL + "/event";
    private String JOIN_ENDPOINT = "/join";

    public Single<String> joinEvent(final String eventId, final String token){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();

                String endpoint = EVENT_ENDPOINT + "/" + eventId + JOIN_ENDPOINT;
                HashMap<String, String> fields = new HashMap<>();
                Response response = OkHttpUtils.makePostRequestWithUser(okHttpClient, endpoint, fields, token);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    e.onError(new ResponseUnsuccessfulException(responseBody));
                }
            }
        });
    }

}
