package com.quickdev.baseframework.base;

import android.content.Context;
import android.view.ViewGroup;

import com.quickdev.baseframework.base.interfaces.OnBackground;
import com.quickdev.baseframework.base.interfaces.OnMainThread;


public interface BaseView {

    Context getIContext();

    void showLoadingDialog();

    void showLoadingDialog(String dialogs);

    void showLoadingDialog(String text, boolean isCancelable);

    void dismissLoadingDialog();

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

    void runOnUIThread(OnMainThread onMainThread);

    void runOnWorkThread(OnBackground onBackground);
}
