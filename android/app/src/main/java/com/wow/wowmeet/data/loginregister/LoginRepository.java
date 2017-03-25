package com.wow.wowmeet.data.loginregister;

import android.util.Log;

import com.google.gson.Gson;
import com.wow.wowmeet.exceptions.GetUserFailedException;
import com.wow.wowmeet.exceptions.LoginFailedException;
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
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.LOGIN_ENDPOINT;
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.PASSWORD_PARAM_NAME;
import static com.wow.wowmeet.data.loginregister.LoginRegisterConstants.USERS_ENDPOINT;


/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class LoginRepository {

    private OkHttpClient client;

    public LoginRepository(){
        this.client = new OkHttpClient();
    }

    public Single<User> login(final String email, final String password){
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                HashMap<String, String> fieldsMap = new HashMap<String, String>();
                fieldsMap.put(EMAIL_PARAM_NAME, email);
                fieldsMap.put(PASSWORD_PARAM_NAME, password);

                Response response = OkHttpUtils.makePostRequest(client, LOGIN_ENDPOINT, fieldsMap);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    UserApiResponse apiResponse = gson.fromJson(responseBody, UserApiResponse.class);
                    //this is trick
                    apiResponse.getUser().setToken(apiResponse.getToken());
                    e.onSuccess(apiResponse.getUser());
                }else{
                    Throwable throwable = new LoginFailedException(responseBody);
                    e.onError(throwable);
                }
            }
        });
    }

    public Single<User> getUser(final String userId){
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                Response response = OkHttpUtils.makeGetRequest(client, USERS_ENDPOINT + "/" + userId);
                String responseBody = response.body().string();
                Log.d("response", responseBody);
                if(response.isSuccessful()){
                    User user = new Gson().fromJson(responseBody, User.class);
                    e.onSuccess(user);
                }else{
                    GetUserFailedException getUserFailedException = new GetUserFailedException(responseBody);
                    e.onError(getUserFailedException);
                }
            }
        });
    }

}
