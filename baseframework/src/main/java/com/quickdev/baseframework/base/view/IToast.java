package com.quickdev.baseframework.base.view;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quickdev.baseframework.R;
import com.quickdev.baseframework.base.view.itoast.ToastUtils;
import com.quickdev.baseframework.utils.AppContextUtil;


public class IToast {
    private static IToast iToast;
    private Toast toast;
    public ImageView iv_img;
    private TextView tv, tvTop;
    private View v;

    public IToast() {
        v = View.inflate(AppContextUtil.getmApplication(), R.layout.toast, null);
        iv_img = v.findViewById(R.id.iv_img);
        tvTop = v.findViewById(R.id.tv_toast_top);

    }

    public static IToast getIToast() {
        if (null == iToast) {
            iToast = new IToast();
        }
        iToast.iv_img.setVisibility(View.GONE);
        return iToast;
    }

    public IToast setImage(int resId) {
        if (null == iToast) {
            iToast = new IToast();
        }
//        iv_img.setImageResource(resId);
        return iToast;
    }

    public IToast setIcon(int resId) {
        if (null == iToast) {
            iToast = new IToast();
        }
        iToast.iv_img.setImageResource(resId);
        iToast.iv_img.setVisibility(View.VISIBLE);
        return iToast;
    }

    public void show(String text) {
        tvTop.setVisibility(View.GONE);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.show(text);
    }

    public void showLong(String text) {

        tvTop.setVisibility(View.GONE);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.getToast().setDuration(Toast.LENGTH_LONG);
        ToastUtils.show(text);

    }

    public void showLongBottom(String text) {

        tvTop.setVisibility(View.GONE);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.BOTTOM, 0, dip2px(130));
        ToastUtils.getToast().setDuration(Toast.LENGTH_LONG);
        ToastUtils.show(text);

    }

    public void show(int resId) {
        tvTop.setVisibility(View.GONE);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.show(resId);

    }

    public void showAsBottomn(int resId) {
        tvTop.setVisibility(View.GONE);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.BOTTOM, 0, dip2px(130));
        ToastUtils.show(resId);

    }

    public void showAsBottomnDouble(int resId, int resId2) {

        tvTop.setVisibility(View.VISIBLE);
        tvTop.setText(resId);
        ToastUtils.setView(v);
        ToastUtils.setGravity(Gravity.BOTTOM, 0, dip2px(130));
        ToastUtils.show(resId2);

    }

    public void cancleToast() {
        if (toast != null) {
            toast.cancel();
        }
    }


    /**
     * dip转换px
     */
    private int dip2px(float dip) {
        final float scale = AppContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
