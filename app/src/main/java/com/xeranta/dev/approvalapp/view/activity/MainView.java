package com.xeranta.dev.approvalapp.view.activity;

import com.xeranta.dev.approvalapp.model.entity.ItemTask;
import com.xeranta.dev.approvalapp.view.BaseView;

import java.util.List;

public interface MainView extends BaseView {

    void showMessage(String msg);

    void showError(String msg);

    void reloadData();

    void showItemTaskList(List<ItemTask> list, String remainTask);
}
