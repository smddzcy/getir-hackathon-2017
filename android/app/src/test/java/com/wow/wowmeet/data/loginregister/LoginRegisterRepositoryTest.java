package com.wow.wowmeet.data.loginregister;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

import static org.junit.Assert.*;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */
public class LoginRegisterRepositoryTest {

    @Test
    public void login() throws Exception {
        String email = "deneme@deneme.com";
        String password = "123456";

        LoginRepository loginRepository = new LoginRepository();
        Single<String> single = loginRepository.login(email, password);

        single.subscribeWith(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String value) {
                System.out.println("oldu");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                System.out.println("error");
            }
        });
    }

    @Test
    public void register() throws Exception{
        String email = "deneme@deneme.com";
        String password = "123456";

        RegisterRepository registerRepository = new RegisterRepository();
        Single<Void> single = registerRepository.register(email, password);

        single.subscribeWith(new DisposableSingleObserver<Void>() {
            @Override
            public void onSuccess(Void value) {
                System.out.println("oldu");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                System.out.println("error");
            }
        });
    }

}