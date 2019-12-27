package com.quickdev.quickdevframework;

import android.app.Application;

import com.quickdev.baseframework.utils.AppContextUtil;
import com.quickdev.baseframework.utils.NetUtils;

public class DevApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetUtils.setContext(this);
        AppContextUtil.init(this, true);
    }
}
