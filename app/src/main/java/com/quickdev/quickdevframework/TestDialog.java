package com.quickdev.quickdevframework;

import android.os.Bundle;
import android.view.View;

import com.quickdev.baseframework.ui.dialog.BaseDialogFragment;

public class TestDialog extends BaseDialogFragment {
    @Override
    protected int getContentLayoutId() {
        return R.layout.test_dialog;
    }

    @Override
    protected void initView(View mView, Bundle savedInstanceState, BaseDialogFragment dialog) {

    }
}
