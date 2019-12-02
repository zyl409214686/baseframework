package com.quickdev.quickdevframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseModel;
import com.quickdev.baseframework.base.BasePresenter;

public class MainActivity extends BaseActivity<BasePresenter, BaseModel> {

    @Override
    protected void setLayout() {
        setView(R.layout.activity_main, TYPE_HEADER);
        setHeaderBar("hello world");
    }

    @Override
    protected void processData() {

    }

    @Override
    protected void onLeftButtonClick() {
        super.onLeftButtonClick();
        finish();
    }
}
