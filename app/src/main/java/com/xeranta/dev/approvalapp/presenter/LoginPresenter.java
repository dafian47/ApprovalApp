package com.xeranta.dev.approvalapp.presenter;

import android.app.Activity;

import com.xeranta.dev.approvalapp.BaseApplication;
import com.xeranta.dev.approvalapp.view.activity.LoginView;

public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView loginView;

    private Activity activity;

    public LoginPresenter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void attachView(LoginView view) {
        this.loginView = view;
    }

    @Override
    public void detachView() {
        this.loginView = null;
    }

    public void login(String username, String password) {

        if (validate(username, password)) {

            ((BaseApplication) activity.getApplication()).setGlobalUserName(username);
            ((BaseApplication) activity.getApplication()).setGlobalPassword(password);

            loginView.showLoginSuccess();

        } else {

            loginView.showLoginFailed();
        }
    }

    private boolean validate(String username, String password) {

        boolean valid = true;

        if (username.isEmpty()) {
            valid = false;
        }

        if (password.isEmpty()) {
            valid = false;
        }

        return valid;
    }
}
