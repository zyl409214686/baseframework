package com.quickdev.baseframework.base.interfaces;

import android.os.Bundle;

import com.quickdev.baseframework.base.BaseActivity;

public interface IBase {
    int getLayoutResId();

    BaseActivity.HEADER_TYPE getHeaderType();

    boolean isRegisteredEventBus();

    void setContentViewAfter(Bundle savedInstanceState);

    void setContentViewBefore(Bundle savedInstanceState);
}
