package com.quickdev.baseframework.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.quickdev.baseframework.R;
import com.quickdev.baseframework.base.interfaces.OnBackground;
import com.quickdev.baseframework.base.interfaces.OnHeaderClickListener;
import com.quickdev.baseframework.base.interfaces.OnMainThread;
import com.quickdev.baseframework.base.view.HeaderLayout;
import com.quickdev.baseframework.base.view.LoadingDialog;
import com.quickdev.baseframework.utils.StatusBarUtils;
import com.quickdev.baseframework.utils.ThreadPoolManager;
import com.quickdev.baseframework.utils.TypeUtil;

import butterknife.ButterKnife;

//import com.umeng.message.PushAgent;

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity implements BaseView {
    protected Context mContext;
    private HeaderLayout headerLayout;
    protected LoadingDialog loadingDialog;
    protected static final int TYPE_NORMAL = 0; //什么都没有
    protected static final int TYPE_HEADER = 1;//有头
    protected static final int TYPE_PROGRESS = 2;//有加载动画
    protected static final int TYPE_HEADER_PROGRESS = 3;//有头有加载动画
    protected static final int TYPE_NOHEADER_STATUABAR = 4;//无头有bar
    protected static final int TYPE_NOHEADER_STATUABAR_LIGHT = 5;//无头有bar 白色字体
    protected static final int TYPE_BLUE_HEADER = 6;//沉浸式 蓝色背景头部

    public T mPresenter;
    public E mModel;

    protected BaseActivity() {
        mContext = this;
    }

    protected abstract void setLayout();

    protected abstract void processData();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        onCreate1(savedInstanceState);
        mContext = this;
        setLayout();
        ButterKnife.bind(this);
        mPresenter = TypeUtil.getT(this, 0);
        mModel = TypeUtil.getT(this, 1);
        if (this instanceof BaseView && mPresenter != null) mPresenter.setVM(this, mModel);
        StatusBarUtils.setLightStatusBar(this, true);
        onCreate2(savedInstanceState);
        processData();
//        PushAgent.getInstance(this).onAppStart();
    }

    public void onCreate2(Bundle savedInstanceState) {
    }

    public void onCreate1(Bundle savedInstanceState) {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void setView(int layoutResId, int type) {
        switch (type) {
            case TYPE_NORMAL:
                setContentView(layoutResId);
                break;
            default:
                headerLayout = new HeaderLayout(this, layoutResId, type);
                setContentView(headerLayout);
                break;
        }
        if (null != headerLayout) {
            headerLayout.setHeaderClickListener(onHeaderClickListener);
        }
    }

    public void setTitle(String title) {
        if (null != headerLayout) {
            headerLayout.setTitle(title);
        }
    }

    public void setMiddleTtitle(String title) {
        if (null != headerLayout) {
            headerLayout.setMiddleTitle(title);
        }
    }

    public void setRightBold() {
        if (null != headerLayout) {
            headerLayout.setRightBold();
        }
    }

    public void setCommonTitle2(String text) {
        if (null != headerLayout) {
            headerLayout.setCommonTitle2(text);
        }
    }

    public void setCommonRight2(String text) {
        if (null != headerLayout) {
            headerLayout.setCommonRightTitle2(text);
        }
    }

    public void showOrHideHeader(boolean isShow) {
        if (headerLayout == null) return;
        if (isShow)
            headerLayout.showOrHideHeader(true);
        else
            headerLayout.showOrHideHeader(false);
    }

    private OnHeaderClickListener onHeaderClickListener = new OnHeaderClickListener() {
        @Override
        public void onClickLeftButton() {
            onLeftButtonClick();
        }

        @Override
        public void onClickRightButton() {
            onRightButtonClick();
        }

        @Override
        public void onClickReLoadButton() {
            onReLoadButtonClick();
        }

        @Override
        public void onClickCloseButton() {
            onCloseButtonClick();
        }

        @Override
        public void onClickRefreshButton() {
            onRefreshButtonClick();
        }

        @Override
        public void onClickRightTv() {
            onRightTextClick();
        }

    };

    protected void onLeftButtonClick() {
        //子类选择性实现
        finish();
    }

    protected void onRightButtonClick() {
        //子类选择性实现
    }

    public void onReLoadButtonClick() {
        //子类选择性实现
    }

    protected void onCloseButtonClick() {
        //子类选择性实现
    }

    protected void onRefreshButtonClick() {
        //子类选择性实现
    }

    protected void onRightTextClick() {
        //子类选择性实现
    }

    ;


    /**
     * 显示“返回”按钮和title
     *
     * @param titleText
     */
    public void setHeaderBar(String titleText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText);
        }
    }

    /**
     * 显示“刷新”按钮
     *
     * @param res
     */
    public void setRefreshBar(int res) {
        if (null != headerLayout) {
            headerLayout.setRightBar(res);
        }
    }


    /**
     * 显示“跳过”文字
     *
     * @param title
     * @param rightContent
     */
    public void setHeaderBarAndRightTv(String title, String rightContent) {
        if (null != headerLayout) {
            headerLayout.setHeaderBarAndRightTv(title, rightContent);
        }
    }

    /**
     * 是否显示“跳过”文字
     *
     * @param isShow
     */
    public void setRightTextShow(boolean isShow) {
        if (null != headerLayout) {
            headerLayout.setRightTextShow(isShow);
        }
    }

    /**
     * 显示左侧“关闭”按钮
     *
     * @param res
     */
    public void setCloseBar(int res) {
        if (null != headerLayout) {
            headerLayout.setLeftBar(res);
        }
    }


    /**
     * 显示“返回”按钮和title加右侧从相册选择
     *
     * @param titleText
     */
    public void setHeaderBarAndRight(String titleText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, "");
        }
    }

    /**
     * 显示“返回”按钮和title加右侧扫一扫
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImage(String titleText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, 0);
        }
    }


    /**
     * 显示“返回”按钮和 右侧图片
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImage(String titleText, int res) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, res);
        }
    }

    /**
     * 显示“返回”按钮和 右侧图片
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImageSpe(String titleText, int res) {
        if (null != headerLayout) {
            headerLayout.setHeaderBarSpe(titleText, res);
        }
    }


    /**
     * 显示“返回”按钮、title、右边按钮。
     *
     * @param titleText
     * @param rightText
     */
    public void setHeaderBar(String titleText, String rightText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, rightText);
        }
    }

    /**
     * 显示“返回”按钮、title、右边按钮。
     *
     * @param titleText
     * @param rightText
     */
    public void setWhiteHeaderBar(String titleText, String rightText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, rightText);
        }
    }


    /**
     * 显示左边按钮、title、右边按钮
     *
     * @param leftText
     * @param titleText
     * @param rightText
     */
    public void setHeaderBar(String leftText, String titleText, String rightText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(leftText, titleText, rightText);
        }
    }

    /**
     * 获取headerBar
     */
    public View getHeaderBar() {
        if (null != headerLayout)
            return headerLayout.getHeadBar();
        return null;
    }

    /**
     * 获取HeaderHolder
     */
    public HeaderLayout.HeaderHolder getHeaderHolder() {
        return headerLayout.getHeaderHolder();
    }

    /**
     * 隐藏左边箭头
     */
    public void hideHeaderLeftArrow() {
        if (null != headerLayout) {
//            headerLayout.hideHeaderLeftArrow();
        }
    }

    /**
     * 隐藏左边按钮
     */
    public void hideHeaderLeftButton() {
        if (null != headerLayout) {
            headerLayout.hideHeaderLeftButton();
        }
    }

    /**
     * 展示正在加载...
     */
    public void showLoadingPage() {
        if (null != headerLayout) {
            headerLayout.showLoadingPage();
        }
    }

    /**
     * 展示正在加载界面。
     *
     * @param loadingText
     */
    public void showLoadingPage(String loadingText) {
        if (null != headerLayout) {
            headerLayout.showLoadingPage(loadingText);
        }
    }

    public void hideLoadingPageDialog() {
        if (null != headerLayout) {
            headerLayout.hideLoadingPageDialog();
        }
    }

    /**
     * 加载成功，隐藏加载页
     */
    public void dismissLoadingPage() {
        if (null != headerLayout) {
            headerLayout.dismissLoadingPage();
        }
    }

    /**
     * 网络异常，展示重新加载按钮。
     */
    public void showErrorPage() {
        if (null != headerLayout) {
            headerLayout.showErrorPage();
        }
    }

    public void showErrorPage(String errorText) {
        if (null != headerLayout) {
            headerLayout.showErrorPage(0, errorText);
        }
    }

    public void showErrorPage(int imgResID, String errorText) {
        if (null != headerLayout) {
            headerLayout.showErrorPage(imgResID, errorText);
        }
    }

    /**
     * 返回无数据，展示未找到相关数据
     */
    public void showNoDatasPage() {
        if (null != headerLayout) {
            headerLayout.showNoDatasPage();
        }
    }


    public void showNoDatasPage(String noDatasText) {
        if (null != headerLayout) {
            headerLayout.showNoDatasPage(0, noDatasText);
        }
    }

    /**
     * 返回无数据，展示空数据页。
     *
     * @param noDatasText
     */
    public void showNoDatasPage(int imgResID, String noDatasText) {
        if (null != headerLayout) {
            headerLayout.showNoDatasPage(imgResID, noDatasText);
        }
    }

    /**
     * 弹出"正在加载..."对话框。
     */
    protected void showLoadingDialog() {
        if (loadingDialog == null || loadingDialog.getContext() != mContext) {
            loadingDialog = new LoadingDialog(mContext);
        }
        try {
            loadingDialog.show(getString(R.string.loading));
        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    @Override
    public void showLoadingDialog(String text) {
        showLoadingDialog(text, true);
    }

    @Override
    public void showLoadingDialog(String text, boolean isCancelable) {
        if (loadingDialog == null || loadingDialog.getContext() != mContext) {
            loadingDialog = new LoadingDialog(mContext);
        }
        try {
            if (!isCancelable) {
                loadingDialog.setCanceledOnTouchOutside(false);
            }
            loadingDialog.show(text);
        } catch (WindowManager.BadTokenException e) {
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        if (null != mPresenter) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public Context getIContext() {
        return mContext;
    }

    @Override
    public void setEmptyView(int layoutResId, ViewGroup viewGroup) {

    }

    @Override
    public void runOnUIThread(OnMainThread onMainThread) {
        runOnUiThread(() -> {
            if (onMainThread != null) onMainThread.doInUIThread();
        });
    }

    @Override
    public void runOnThreeThread(OnBackground onBackground) {
        ThreadPoolManager.newInstance().addExecuteTask(() -> {
            if (onBackground != null) onBackground.doOnBackground();
        }, true);
    }
}