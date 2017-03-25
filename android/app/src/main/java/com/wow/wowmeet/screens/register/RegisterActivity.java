package com.wow.wowmeet.screens.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wow.wowmeet.R;
import com.wow.wowmeet.base.BaseActivity;
import com.wow.wowmeet.models.User;
import com.wow.wowmeet.screens.main.MainActivity;
import com.wow.wowmeet.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    private RegisterContract.Presenter presenter;

    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.btnRegister) Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        final RegisterPresenter presenter = new RegisterPresenter(this);
        setPresenter(presenter);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showError(String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Hata!")
                .setMessage(errorMessage)
                .create();
        dialog.show();
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

    }

    @Override
    public void hideLoading() {

    }
}
