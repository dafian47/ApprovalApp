package com.xeranta.dev.approvalapp.presenter;

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();
}
