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
    private LoginPreferences loginPreferences;

    private DisposableSingleObserver<User> disposableSingleLoginObserver;
    private DisposableSingleObserver<User> disposableSingleUserObserver;

    public LoginPresenter(LoginContract.View view, LoginPreferences loginPreferences) {
        this.view = view;
        this.loginRepository = new LoginRepository();
        this.loginPreferences = loginPreferences;
    }

    @Override
    public void start() {
        if(loginPreferences.isUserLoggedIn()){
            view.showLoading();
            String userId = loginPreferences.getUserId();
            final String token = loginPreferences.getUserToken(); //TODO CHECK EXPIRE

            Single<User> userSingle = loginRepository.getUser(userId);
            disposableSingleUserObserver = userSingle
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<User>() {
                        @Override
                        public void onSuccess(User value) {
                            value.setToken(token);
                            view.goMainWithUser(value);
                            view.hideLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError(e.getMessage());
                        }
                    });

        }
    }

    @Override
    public void stop() {
        if(disposableSingleLoginObserver != null)
            disposableSingleLoginObserver.dispose();

        if(disposableSingleUserObserver != null)
            disposableSingleUserObserver.dispose();
    }

    @Override
    public void onLoginClicked(String email, String password) {
        view.showLoading();
        Single<User> loginSingle = loginRepository.login(email, password);
        disposableSingleLoginObserver = loginSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User value) {
                        view.goMainWithUser(value);
                        view.hideLoading();
                        loginPreferences.saveUserLogin(value.get_id(), value.getToken());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void onRegisterClicked() {
        view.goRegister();
    }
}
