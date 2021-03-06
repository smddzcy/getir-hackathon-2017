package com.wow.wowmeet.data.loginregister;

import com.google.gson.Gson;
import com.wow.wowmeet.exceptions.RegisterFailedException;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.models.UserApiResponse;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.EMAIL_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.PASSWORD_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.REGISTER_ENDPOINT;
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.USERNAME_PARAM_NAME;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class RegisterRepository {

    private OkHttpClient client;

    public RegisterRepository() {
        this.client = new OkHttpClient();
    }

    public Single<User> register(final String username, final String email, final String password){
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                HashMap<String, String> fieldsMap = new HashMap<>();
                fieldsMap.put(EMAIL_PARAM_NAME, email);
                fieldsMap.put(PASSWORD_PARAM_NAME, password);
                fieldsMap.put(USERNAME_PARAM_NAME, username);

                Response response = OkHttpUtils.makePostRequest(client, REGISTER_ENDPOINT, fieldsMap);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    System.out.println(responseBody);
                    Gson gson = new Gson();
                    UserApiResponse apiResponse = gson.fromJson(responseBody, UserApiResponse.class);
                    //this is trick
                    apiResponse.getUser().setToken(apiResponse.getToken());
                    e.onSuccess(apiResponse.getUser());
                }else{
                    RegisterFailedException registerFailedException =
                            new RegisterFailedException(responseBody);
                    e.onError(registerFailedException);
                }
            }
        });
    }
}
