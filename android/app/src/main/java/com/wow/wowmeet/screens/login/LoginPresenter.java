package com.wow.wowmeet.screens.login;

import com.wow.wowmeet.data.loginregister.LoginRepository;
import com.wow.wowmeet.models.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginRepository loginRepository;

    private DisposableSingleObserver<User> disposableSingleObserver;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.loginRepository = new LoginRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if(disposableSingleObserver != null)
            disposableSingleObserver.dispose();
    }

    @Override
    public void onLoginClicked(String email, String password) {
        view.showLoading();
        Single<User> loginSingle = loginRepository.login(email, password);
        disposableSingleObserver = loginSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User value) {
                        view.onLoginSuccess(value);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError(e);
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void onRegisterClicked() {
        view.goRegister();
    }
}
