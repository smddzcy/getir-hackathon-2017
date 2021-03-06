package com.wow.wowmeet.utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class OkHttpUtils {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

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

    public static Response makePostRequestWithUser(OkHttpClient client, String endpoint,
                                                   Map<String, String> fields, String token) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        for(String key : fields.keySet()){
            builder.add(key, fields.get(key));
        }


        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .url(endpoint)
                .build();

        return client.newCall(request).execute();
    }

    public static Response makePostRequestBodyJsonWithJUser(OkHttpClient client, String endpoint,
                                                   String jsonString, String token) throws IOException {

        RequestBody requestBody = RequestBody.create(JSON, jsonString);

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .url(endpoint)
                .build();

        return client.newCall(request).execute();
    }

    public static Response makePostRequestWithUserJson(OkHttpClient client, String endpoint,
                                                       String json, String token) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(endpoint)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response makeGetRequest(OkHttpClient client, String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(endpoint)
                .build();

        return client.newCall(request).execute();
    }

    //Users' token will be put in here
    public static Response makeGetRequestWithUser(OkHttpClient client, String endpoint, String token) throws IOException {
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return client.newCall(request).execute();
    }
}
