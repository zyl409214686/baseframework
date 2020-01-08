package com.quickdev.quickdevframework;

import android.app.Application;

import com.quickdev.baseframework.base.view.itoast.ToastUtils;
import com.quickdev.baseframework.base.view.itoast.style.ToastBlackStyle;
import com.quickdev.baseframework.utils.AppContextUtil;
import com.quickdev.baseframework.utils.NetUtils;

public class DevApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetUtils.setContext(this);
        AppContextUtil.init(this, true);
        try {
            ToastUtils.init(this, new ToastBlackStyle(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
