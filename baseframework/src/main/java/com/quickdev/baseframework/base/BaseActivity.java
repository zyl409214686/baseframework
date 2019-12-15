package com.quickdev.baseframework.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.quickdev.baseframework.R;
import com.quickdev.baseframework.base.interfaces.OnBackground;
import com.quickdev.baseframework.base.interfaces.OnHeaderClickListener;
import com.quickdev.baseframework.base.interfaces.OnMainThread;
import com.quickdev.baseframework.base.view.HeaderLayout;
import com.quickdev.baseframework.base.view.LoadingDialog;
import com.quickdev.baseframework.utils.EventBusUtils;
import com.quickdev.baseframework.utils.StatusBarUtils;
import com.quickdev.baseframework.utils.ThreadPoolManager;
import com.quickdev.baseframework.utils.TypeUtil;

import butterknife.ButterKnife;

//import com.umeng.message.PushAgent;

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity implements BaseView {
    protected Context mContext;
    private HeaderLayout mHeaderLayout;
    protected LoadingDialog mLoadingDialog;

    public enum HEADER_TYPE {
        TYPE_NORMAL,//什么都没有
        TYPE_HEADER,//有头
        TYPE_PROGRESS,//有加载动画
        TYPE_HEADER_PROGRESS,//有头有加载动画
        TYPE_NOHEADER_STATUABAR,//无头有bar
        TYPE_NOHEADER_STATUABAR_LIGHT,//无头有bar 白色字体
        TYPE_BLUE_HEADER,//沉浸式 蓝色背景头部
    }

    public T mPresenter;
    public E mModel;

    protected BaseActivity() {
        mContext = this;
    }

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
        mContext = this;
        setContentViewBefore(savedInstanceState);
        setContentView();
        init();
        setContentViewAfter(savedInstanceState);
    }

    private void init() {
        ButterKnife.bind(this);
        initMvp();
        StatusBarUtils.setLightStatusBar(this, true);
    }

    private void setContentView() {
        int layoutResId = getLayoutResId();
        if (layoutResId != 0) {
            setContentView(layoutResId, getHeaderType());
        }
    }

    private void initMvp() {
        mPresenter = TypeUtil.getT(this, 0);
        mModel = TypeUtil.getT(this, 1);
        if (this instanceof BaseView && mPresenter != null) mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isRegisteredEventBus())
            EventBusUtils.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isRegisteredEventBus())
            EventBusUtils.unregister(this);
    }

    protected abstract int getLayoutResId();

    protected abstract HEADER_TYPE getHeaderType();

    protected boolean isRegisteredEventBus() {
        return false;
    }

    protected abstract void setContentViewAfter(Bundle savedInstanceState);

    protected void setContentViewBefore(Bundle savedInstanceState){

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void setContentView(int layoutResId, HEADER_TYPE type) {
        switch (type) {
            case TYPE_NORMAL:
                setContentView(layoutResId);
                break;
            default:
                mHeaderLayout = new HeaderLayout(this, layoutResId, type);
                setContentView(mHeaderLayout);
                break;
        }
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderClickListener(onHeaderClickListener);
        }
    }

    public void setTitle(String title) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setTitle(title);
        }
    }

    public void setMiddleTtitle(String title) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setMiddleTitle(title);
        }
    }

    public void setRightBold() {
        if (null != mHeaderLayout) {
            mHeaderLayout.setRightBold();
        }
    }

    public void setCommonTitle2(String text) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setCommonTitle2(text);
        }
    }

    public void setCommonRight2(String text) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setCommonRightTitle2(text);
        }
    }

    public void showOrHideHeader(boolean isShow) {
        if (mHeaderLayout == null) return;
        if (isShow)
            mHeaderLayout.showOrHideHeader(true);
        else
            mHeaderLayout.showOrHideHeader(false);
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
        if (null != mHeaderLayout) {
            mHeaderLayout.setmHeaderBar(titleText);
        }
    }

    /**
     * 显示“刷新”按钮
     *
     * @param res
     */
    public void setRefreshBar(int res) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setRightBar(res);
        }
    }


    /**
     * 显示“跳过”文字
     *
     * @param title
     * @param rightContent
     */
    public void setHeaderBarAndRightTv(String title, String rightContent) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBarAndRightTv(title, rightContent);
        }
    }

    /**
     * 是否显示“跳过”文字
     *
     * @param isShow
     */
    public void setRightTextShow(boolean isShow) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setRightTextShow(isShow);
        }
    }

    /**
     * 显示左侧“关闭”按钮
     *
     * @param res
     */
    public void setCloseBar(int res) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setLeftBar(res);
        }
    }


    /**
     * 显示“返回”按钮和title加右侧从相册选择
     *
     * @param titleText
     */
    public void setHeaderBarAndRight(String titleText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(titleText, "");
        }
    }

    /**
     * 显示“返回”按钮和title加右侧扫一扫
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImage(String titleText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(titleText, 0);
        }
    }


    /**
     * 显示“返回”按钮和 右侧图片
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImage(String titleText, int res) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(titleText, res);
        }
    }

    /**
     * 显示“返回”按钮和 右侧图片
     *
     * @param titleText
     */
    public void setHeaderBarAndRightImageSpe(String titleText, int res) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBarSpe(titleText, res);
        }
    }


    /**
     * 显示“返回”按钮、title、右边按钮。
     *
     * @param titleText
     * @param rightText
     */
    public void setHeaderBar(String titleText, String rightText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(titleText, rightText);
        }
    }

    /**
     * 显示“返回”按钮、title、右边按钮。
     *
     * @param titleText
     * @param rightText
     */
    public void setWhiteHeaderBar(String titleText, String rightText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(titleText, rightText);
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
        if (null != mHeaderLayout) {
            mHeaderLayout.setHeaderBar(leftText, titleText, rightText);
        }
    }

    /**
     * 获取headerBar
     */
    public View getHeaderBar() {
        if (null != mHeaderLayout)
            return mHeaderLayout.getHeadBar();
        return null;
    }

    /**
     * 获取HeaderHolder
     */
    public HeaderLayout.HeaderHolder getHeaderHolder() {
        return mHeaderLayout.getHeaderHolder();
    }

    /**
     * 隐藏左边按钮
     */
    public void hideHeaderLeftButton() {
        if (null != mHeaderLayout) {
            mHeaderLayout.hideHeaderLeftButton();
        }
    }

    /**
     * 展示正在加载...
     */
    public void showLoadingPage() {
        if (null != mHeaderLayout) {
            mHeaderLayout.showLoadingPage();
        }
    }

    /**
     * 展示正在加载界面。
     *
     * @param loadingText
     */
    public void showLoadingPage(String loadingText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.showLoadingPage(loadingText);
        }
    }

    public void hideLoadingPageDialog() {
        if (null != mHeaderLayout) {
            mHeaderLayout.hideLoadingPageDialog();
        }
    }

    /**
     * 加载成功，隐藏加载页
     */
    public void dismissLoadingPage() {
        if (null != mHeaderLayout) {
            mHeaderLayout.dismissLoadingPage();
        }
    }

    /**
     * 网络异常，展示重新加载按钮。
     */
    public void showErrorPage() {
        if (null != mHeaderLayout) {
            mHeaderLayout.showErrorPage();
        }
    }

    public void showErrorPage(String errorText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.showErrorPage(0, errorText);
        }
    }

    public void showErrorPage(int imgResID, String errorText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.showErrorPage(imgResID, errorText);
        }
    }

    /**
     * 返回无数据，展示未找到相关数据
     */
    public void showNoDatasPage() {
        if (null != mHeaderLayout) {
            mHeaderLayout.showNoDatasPage();
        }
    }


    public void showNoDatasPage(String noDatasText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.showNoDatasPage(0, noDatasText);
        }
    }

    /**
     * 返回无数据，展示空数据页。
     *
     * @param noDatasText
     */
    public void showNoDatasPage(int imgResID, String noDatasText) {
        if (null != mHeaderLayout) {
            mHeaderLayout.showNoDatasPage(imgResID, noDatasText);
        }
    }

    /**
     * 弹出"正在加载..."对话框。
     */
    @Override
    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading), true);
    }

    @Override
    public void showLoadingDialog(String text) {
        showLoadingDialog(text, true);
    }

    @Override
    public void showLoadingDialog(String text, boolean canceldOnTouchOutside) {
        if (mLoadingDialog == null || mLoadingDialog.getContext() != mContext) {
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.setCanceledOnTouchOutside(canceldOnTouchOutside);
        }
        try {
            mLoadingDialog.show(text);
        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    @Override
    public void dismissLoadingDialog() {
        if (null != mLoadingDialog) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        if(mHeaderLayout!=null) {
            mHeaderLayout.onDestory();
        }
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
    public void runOnUIThread(OnMainThread onMainThread) {
        runOnUiThread(() -> {
            if (onMainThread != null) onMainThread.doInUIThread();
        });
    }

    @Override
    public void runOnWorkThread(OnBackground onBackground) {
        ThreadPoolManager.newInstance().addExecuteTask(() -> {
            if (onBackground != null) onBackground.doOnBackground();
        }, true);
    }
}