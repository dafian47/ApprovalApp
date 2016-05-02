package com.xeranta.dev.approvalapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class BaseApplication extends Application {

    private String GlobalUserName;
    private String GlobalPassword;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public String getGlobalUserName() {
        return GlobalUserName;
    }

    public void setGlobalUserName(String globalUserName) {
        GlobalUserName = globalUserName;
    }

    public String getGlobalPassword() {
        return GlobalPassword;
    }

    public void setGlobalPassword(String globalPassword) {
        GlobalPassword = globalPassword;
    }
}
