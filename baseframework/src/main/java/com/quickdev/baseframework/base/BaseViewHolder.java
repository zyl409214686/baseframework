package com.quickdev.baseframework.base;

import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseViewHolder {

    private Unbinder bind;
    private View parentView;

    public BaseViewHolder(View view) {
        if (null != view)
            bind = ButterKnife.bind(this, view);
        this.parentView = view;
    }

    public void unBind() {
        if (null != bind) bind.unbind();
    }
}
