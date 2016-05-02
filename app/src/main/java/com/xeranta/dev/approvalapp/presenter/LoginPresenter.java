package com.xeranta.dev.approvalapp.presenter;

import com.xeranta.dev.approvalapp.BaseApplication;
import com.xeranta.dev.approvalapp.view.activity.LoginView;

public class LoginPresenter implements BasePresenter<LoginView> {

    private LoginView loginView;

    public LoginPresenter() {

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

        BaseApplication application = (BaseApplication) loginView.getContext();

        if (validate(username, password)) {

            application.setGlobalUserName(username);
            application.setGlobalPassword(password);

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
