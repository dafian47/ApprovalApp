package com.xeranta.dev.approvalapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xeranta.dev.approvalapp.R;
import com.xeranta.dev.approvalapp.presenter.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "LoginActivity";

    @Bind(R.id.et_username) EditText etUserName;
    @Bind(R.id.et_password) EditText etPassword;
    @Bind(R.id.bt_login) Button btLogin;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter   = new LoginPresenter();
        presenter.attachView(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                presenter.login(username, password);
            }
        });
    }

    @Override
    public void showLoginSuccess() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginFailed() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
