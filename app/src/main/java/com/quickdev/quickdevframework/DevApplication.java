package com.quickdev.quickdevframework;

import android.app.Application;

import com.quickdev.baseframework.utils.AppContextUtil;

public class DevApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this, true);
    }
}
