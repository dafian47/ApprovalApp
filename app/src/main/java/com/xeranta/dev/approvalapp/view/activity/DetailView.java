package com.xeranta.dev.approvalapp.view.activity;

import com.xeranta.dev.approvalapp.view.BaseView;

import java.util.HashMap;

public interface DetailView extends BaseView {

    void showMessage(String msg);

    void showError(String msg);

    void showTaskById(HashMap<String, String> map);

    void onUpdateSuccess();

}
