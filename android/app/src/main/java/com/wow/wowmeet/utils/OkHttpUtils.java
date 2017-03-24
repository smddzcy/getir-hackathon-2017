package com.wow.wowmeet.utils;

import com.wow.wowmeet.models.User;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class OkHttpUtils {

    //fields map has PARAM_NAME and VALUE as elements.
    public static Response makePostRequest(OkHttpClient client, String endpoint,
                                           Map<String, String> fields) throws IOException {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for(String key : fields.keySet()){
            builder.addFormDataPart(key, fields.get(key));
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(endpoint)
                .build();

        return client.newCall(request).execute();
    }

    //Users' token will be put in here
    public static Response makeGetRequestWithUser(OkHttpClient client, String endpoint, User user){
        return null;
    }
}
