package com.wow.wowmeet.data.loginregister;

import com.wow.wowmeet.exceptions.RegisterFailedException;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.wow.wowmeet.data.loginregister.Constants.CONFIRM_PASSWORD_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.Constants.EMAIL_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.Constants.PASSWORD_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.Constants.REGISTER_ENDPOINT;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class RegisterRepository {

    private OkHttpClient client;

    public RegisterRepository() {
        this.client = new OkHttpClient();
    }

    public Single<String> register(final String email, final String password){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                HashMap<String, String> fieldsMap = new HashMap<String, String>();
                fieldsMap.put(EMAIL_PARAM_NAME, email);
                fieldsMap.put(PASSWORD_PARAM_NAME, password);
                fieldsMap.put(CONFIRM_PASSWORD_PARAM_NAME, password);

                Response response = OkHttpUtils.makePostRequest(client, REGISTER_ENDPOINT, fieldsMap);
                if(response.isSuccessful()){
                    e.onSuccess("");
                }else{
                    RegisterFailedException registerFailedException = new RegisterFailedException();
                    e.onError(registerFailedException);
                }
            }
        });
    }
}
