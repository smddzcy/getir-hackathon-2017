package com.wow.wowmeet.data.loginregister;

import com.wow.wowmeet.models.User;

import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by ergunerdogmus on 24.03.2017.
 */
public class LoginRegisterRepositoryTest {

    @Test
    public void login() throws Exception {
        String email = "murat@murat.com";
        String password = "murat";

        LoginRepository loginRepository = new LoginRepository();
        Single<User> single = loginRepository.login(email, password);

        single.subscribeWith(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(User value) {
                System.out.println(value.toString());
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
        String email = "deneme111@deneme.com";
        String password = "123456";

        RegisterRepository registerRepository = new RegisterRepository();
        Single<User> single = registerRepository.register(email, password);

        single.subscribeWith(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(User value) {
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