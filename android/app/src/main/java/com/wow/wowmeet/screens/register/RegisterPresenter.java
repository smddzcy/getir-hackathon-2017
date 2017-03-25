package com.wow.wowmeet.screens.register;

import com.wow.wowmeet.data.loginregister.RegisterRepository;
import com.wow.wowmeet.models.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private RegisterRepository registerRepository;

    private DisposableSingleObserver<User> disposableSingleUserObserver;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        this.registerRepository = new RegisterRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if(disposableSingleUserObserver != null)
            disposableSingleUserObserver.dispose();
    }

    @Override
    public void onRegisterClicked(String usernameText, String emailText, String passwordText) {
        view.showLoading();

        Single<User> singleUser = registerRepository.register(usernameText, emailText, passwordText);

        singleUser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User value) {
                        view.hideLoading();
                        view.goMainWithUser(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showError(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }
}
