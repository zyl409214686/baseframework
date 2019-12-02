package com.quickdev.baseframework.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.quickdev.baseframework.base.interfaces.OnBackground;
import com.quickdev.baseframework.base.interfaces.OnMainThread;


public interface BaseView {

    Context getIContext();

    void showLoading(String dialogs);

    void dismissLoading();

    /**
     * 跳转activity
     */
    void go(Class clazz);

    /**
     * 跳转activity带参数
     *
     * @param clazz
     * @param bundle
     */
    void go(Class clazz, Bundle bundle);

    void go(Intent intent);

    /**
     * 跳转activity
     */
    void goForResult(Class clazz, int requestCode);

    /**
     * 跳转activity带参数
     *
     * @param clazz
     * @param bundle
     */
    void goForResult(Class clazz, int requestCode, Bundle bundle);

    /**
     * 关闭activity
     */
    void exit();

    /**
     * 跳转activity
     */
    void goForResult(Intent intent, int requestCode);

    void goForResult(Intent intent, Class clazz, int requestCode);

    void go(Intent intent, Class clazz);

    void noCancleDialog(boolean b);

    /*
     * 统一添加数据展示相关界面
     * */
    void showLoadingPage(String loadingText);

    void showNoDatasPage(int imgResID, String noDatasText);

    void showNoDatasPage(String noDatasText);

    void showNoDatasPage();

    void showErrorPage(int imgResID, String errorText);

    void showErrorPage(String errorText);

    void showErrorPage();

    void dismissLoadingPage();

    void hideLoadingPageDialog();

    void setEmptyView(int layoutResId, ViewGroup viewGroup);

    void runOnUIThread(OnMainThread onMainThread);

    void runOnThreeThread(OnBackground onBackground);
}
