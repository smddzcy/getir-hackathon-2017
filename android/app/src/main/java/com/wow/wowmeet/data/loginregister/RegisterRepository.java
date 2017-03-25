package com.wow.wowmeet.data.loginregister;

import com.wow.wowmeet.exceptions.RegisterFailedException;
import com.wow.wowmeet.models.User;
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

/**
 * Created by ergunerdogmus on 24.03.2017.
 */

public class RegisterRepository {

    private OkHttpClient client;

    public RegisterRepository() {
        this.client = new OkHttpClient();
    }

    public Single<User> register(final String email, final String password){
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                HashMap<String, String> fieldsMap = new HashMap<String, String>();
                fieldsMap.put(EMAIL_PARAM_NAME, email);
                fieldsMap.put(PASSWORD_PARAM_NAME, password);

                Response response = OkHttpUtils.makePostRequest(client, REGISTER_ENDPOINT, fieldsMap);
                String responseBody = response.body().string();
                if(response.isSuccessful()){
                    System.out.println(responseBody);
                    User user = new User.UserBuilder().setUserId("").setName(email).setEmail(password).createUser();
                    e.onSuccess(user);
                }else{
                    RegisterFailedException registerFailedException =
                            new RegisterFailedException(responseBody);
                    e.onError(registerFailedException);
                }
            }
        });
    }
}
