package com.wow.wowmeet.exceptions;

import com.wow.wowmeet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class BaseException extends Throwable {

    private String errorMessage;

    private boolean useResource = false;
    private int errorMessageResource;

    public BaseException(String message) {
        super(message);
        try {
            JSONObject json = new JSONObject(message);

            if(json.has("msg")) {
                errorMessage = json.getString("msg");
            }



        } catch (JSONException e) {
            try {
                JSONArray jsonArray = new JSONArray(message);

                JSONObject json = jsonArray.getJSONObject(0);
                if(json.has("msg")) {
                    errorMessage = json.getString("msg");
                }

            } catch (JSONException e2) {
                e2.printStackTrace();
                errorMessageResource = R.string.server_error;
                useResource = true;
            }
        }
    }

    public boolean isUseResource() {
        return useResource;
    }

    public int getErrorMessageResource() {
        return errorMessageResource;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
