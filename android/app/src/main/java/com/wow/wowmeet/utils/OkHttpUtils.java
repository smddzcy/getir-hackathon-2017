package com.wow.wowmeet.utils;

import com.wow.wowmeet.models.User;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
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

        FormBody.Builder builder = new FormBody.Builder();

        for(String key : fields.keySet()){
            builder.add(key, fields.get(key));
        }


        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
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
