package com.wow.wowmeet.screens.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.main.MainActivity;
import com.wow.wowmeet.screens.register.RegisterActivity;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.DialogHelper;
import com.wow.wowmeet.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.login_txtInputEdtEmail)
    TextInputEditText edtUsername;
    @BindView(R.id.txtInputEdtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.txtRegister) TextView txtRegister;

    private String getEditTextString(EditText editText){
        return editText.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);
        final LoginPresenter presenter = new LoginPresenter(this, sharedPreferencesUtil);
        setPresenter(presenter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClicked(getEditTextString(edtUsername), getEditTextString(edtPassword));
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String errorMessage) {
        DialogHelper.showAlertDialogWithError(this, errorMessage);
    }

    @Override
    public void showError(@StringRes int resource) {
        showError(getString(resource));
    }


    @Override
    public void goMainWithUser(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_USER, user);
        startActivity(intent);
        finish();
    }

    @Override
    public void goRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }
}
