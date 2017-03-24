package com.wow.wowmeet.data.login;

import com.wow.wowmeet.exceptions.LoginFailedException;
import com.wow.wowmeet.utils.Constants;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class LoginRepository {

    private static final String LOGIN_ENDPOINT = Constants.API_URL + "/login";
    private static final String EMAIL_PARAM_NAME = "email";
    private static final String PASSWORD_PARAM_NAME = "password";

    private OkHttpClient client;

    public LoginRepository(){
        this.client = new OkHttpClient();
    }

    // Returned String will be response body
    public Single<Void> login(final String email, final String password){
        return Single.create(new SingleOnSubscribe<Void>() {
            @Override
            public void subscribe(SingleEmitter<Void> e) throws Exception {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(EMAIL_PARAM_NAME, email)
                        .addFormDataPart(PASSWORD_PARAM_NAME, password)
                        .build();

                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(LOGIN_ENDPOINT)
                        .build();

                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    e.onSuccess(null);
                }else{
                    Throwable throwable = new LoginFailedException();
                    e.onError(throwable);
                }
            }
        });
    }
}
