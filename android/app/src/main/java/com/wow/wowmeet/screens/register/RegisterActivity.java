package com.wow.wowmeet.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.main.MainActivity;
import com.wow.wowmeet.utils.Constants;
import com.wow.wowmeet.utils.DialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private RegisterContract.Presenter presenter;

    @BindView(R.id.register_txtInputEdtEmail)
    TextInputEditText edtEmail;
    @BindView(R.id.register_txtInputEdtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.register_txtInputEdtUsername)
    TextInputEditText edtUsername;

    @BindView(R.id.btnRegister) Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final RegisterPresenter presenter = new RegisterPresenter(this);
        setPresenter(presenter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = edtEmail.getText().toString();
                String passwordText = edtPassword.getText().toString();
                String usernameText = edtUsername.getText().toString();
                presenter.onRegisterClicked(usernameText, emailText, passwordText);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String errorMessage) {
        DialogHelper.showAlertDialogWithError(this, errorMessage);
    }


    @Override
    public void goMainWithUser(User user) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_USER, user);
        startActivity(intent);
        finish();
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
