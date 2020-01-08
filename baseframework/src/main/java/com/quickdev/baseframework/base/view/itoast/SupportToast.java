package com.quickdev.baseframework.base.view.itoast;

import android.app.Application;


final class SupportToast extends BaseToast {

    /**
     * Toast Bullet Window Display Auxiliary Class
     */
    private final ToastHelper mToastHelper;

    SupportToast(Application application) {
        super(application);
        mToastHelper = new ToastHelper(this, application);
    }

    @Override
    public void show() {
        // show toast
        mToastHelper.show();
    }

    @Override
    public void cancel() {
        // cancle toast
        mToastHelper.cancel();
    }
}