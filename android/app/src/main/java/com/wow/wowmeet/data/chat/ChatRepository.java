package com.wow.wowmeet.data.chat;

import com.wow.wowmeet.exceptions.SendMessageFailedException;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 25.03.2017.
 */

public class ChatRepository {

    private static final String MESSAGE_ENDPOINT = Constants.API_URL + "/message";
    private static final String PARAM_TO = "to";
    private static final String PARAM_MESSAGE = "message";

    public Single<String> sendMessage(final String message, final String toId, final String token){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                OkHttpClient okHttpClient = new OkHttpClient();
                HashMap<String, String> fields = new HashMap<>();
                fields.put(PARAM_TO, toId);
                fields.put(PARAM_MESSAGE, message);

                Response response = OkHttpUtils.makePostRequestWithUser
                        (okHttpClient, MESSAGE_ENDPOINT, fields, token);

                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    e.onSuccess(responseBody);
                }else{
                    SendMessageFailedException sendMessageFailedException =
                            new SendMessageFailedException(responseBody);

                    e.onError(sendMessageFailedException);
                }
            }
        });
    }

}
