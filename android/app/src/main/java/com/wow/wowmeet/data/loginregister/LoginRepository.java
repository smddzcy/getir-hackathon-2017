package com.wow.wowmeet.data.loginregister;

import com.wow.wowmeet.exceptions.LoginFailedException;
import com.wow.wowmeet.utils.OkHttpUtils;

import java.util.HashMap;

import static com.wow.wowmeet.data.loginregister.Constants.*;

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

    private OkHttpClient client;

    public LoginRepository(){
        this.client = new OkHttpClient();
    }

    private void doLogin(String email, String password){

    }

    public Single<Void> login(final String email, final String password){
        return Single.create(new SingleOnSubscribe<Void>() {
            @Override
            public void subscribe(SingleEmitter<Void> e) throws Exception {
                HashMap<String, String> fieldsMap = new HashMap<String, String>();
                fieldsMap.put(EMAIL_PARAM_NAME, email);
                fieldsMap.put(PASSWORD_PARAM_NAME, password);

                Response response = OkHttpUtils.makePostRequest(client, API_URL, fieldsMap);
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