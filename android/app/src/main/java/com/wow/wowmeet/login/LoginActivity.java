package com.wow.wowmeet.login;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wow.wowmeet.BaseView;
import com.wow.wowmeet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.txtUsername) EditText edtUsername;
    @BindView(R.id.txtPassword) EditText edtPassword;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.txtRegister) TextView txtRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        LoginPresenter presenter = new LoginPresenter(this);
        setPresenter(presenter);

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onError(String errorMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Hata!")
                .setMessage(errorMessage)
                .create();
        dialog.show();
    }

    @Override
    public void onLoginSuccess() {

    }
}
