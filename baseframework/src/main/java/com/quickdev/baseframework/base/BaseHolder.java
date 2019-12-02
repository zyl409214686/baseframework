package com.quickdev.baseframework.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseHolder extends RecyclerView.ViewHolder {

    private Unbinder bind;

    public BaseHolder(View view) {
        super(view);
        if (null != view)
            bind = ButterKnife.bind(this, view);
    }

    public void unBind() {
        if (null != bind) bind.unbind();
    }
}
