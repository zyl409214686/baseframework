package com.quickdev.baseframework.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.quickdev.baseframework.base.interfaces.OnBackground;
import com.quickdev.baseframework.base.interfaces.OnHeaderClickListener;
import com.quickdev.baseframework.base.interfaces.OnMainThread;
import com.quickdev.baseframework.base.interfaces.IBase;
import com.quickdev.baseframework.base.view.HeaderLayout;
import com.quickdev.baseframework.base.view.LoadingDialog;
import com.quickdev.baseframework.utils.EventBusUtils;
import com.quickdev.baseframework.utils.StatusBarUtils;
import com.quickdev.baseframework.utils.ThreadPoolManager;
import com.quickdev.baseframework.utils.TypeUtil;

import butterknife.ButterKnife;


public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment implements BaseView, IBase {
    protected Activity mContext;
    protected View contentView;
    protected HeaderLayout headerLayout;
    private LoadingDialog loadingDialog;
    private FrameLayout mEmptyLayout;

    protected static final int TYPE_NORMAL = 0;
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_PROGRESS = 2;
    protected static final int TYPE_HEADER_PROGRESS = 3;
    protected static final int TYPE_NOHEADER_STATUSBAR = 4;
    protected static final int TYPE_NOHEADER_STATUSBAR_PROGRESS = 5;
    protected static final int TYPE_BLUE_HEADER = 6;//沉浸式 蓝色背景头部


    private View view;

    public T mPresenter;
    public E mModel;


    public void setContentViewBefore(Bundle savedInstanceState) {

    }

    public BaseFragment() {
        mContext = getActivity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @TargetApi(23)
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (activity instanceof Activity) {
            mContext = (Activity) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mContext.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        if (null == mContext) {
            mContext = getActivity();
        }

        mPresenter = TypeUtil.getT(this, 0);
        mModel = TypeUtil.getT(this, 1);
        checkT();
        StatusBarUtils.setLightStatusBar(getActivity(), true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void checkT() {
        if (mPresenter != null && !(mPresenter instanceof BasePresenter))
            throw new IllegalArgumentException("mPresenter is not instanceof BasePresenter");
        if (mModel != null && !(mModel instanceof BaseModel))
            throw new IllegalArgumentException("mModel is not instanceof BaseModel");
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentViewBefore(savedInstanceState);
        view = setView(inflater, getLayoutResId(), getHeaderType());
        ButterKnife.bind(this, view);
        if (this instanceof BaseView && mPresenter != null) mPresenter.setVM(this, mModel);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentViewAfter(savedInstanceState);
    }

    protected View setView(LayoutInflater inflater, int layoutResId, BaseActivity.HEADER_TYPE type) {
        switch (type) {
            case TYPE_NORMAL:
                contentView = inflater.inflate(layoutResId, null);
                break;
            default:
                headerLayout = new HeaderLayout(mContext, inflater, layoutResId, type);
                break;
        }
        if (null != headerLayout) {
            headerLayout.setHeaderClickListener(onHeaderClickListener);
            return headerLayout;
        }
        return contentView;
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
    }

    protected void onRightButtonClick() {
        //子类选择性实现
    }

    protected void onReLoadButtonClick() {
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

    /**
     * 显示“返回”按钮和title
     *
     * @param titleText
     */
    public void setHeaderBar(String titleText) {
        if (null != headerLayout) {
            headerLayout.setHeaderBar(titleText, "");
        }
    }

    /**
     * 外部持有者调用设置参数
     *
     * @param result
     */
    public void setResult(String result) {

    }

    /**
     * 获取HeaderHolder
     */
    public HeaderLayout.HeaderHolder getHeaderHolder() {
        return headerLayout.getHeaderHolder();
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
     * 显示右侧文字
     *
     * @param text
     */
    public void setCommonRight2(String text) {
        if (null != headerLayout) {
            headerLayout.setCommonRightTitle2(text);
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
    public void showLoadingDialog() {
        if (loadingDialog == null || loadingDialog.getContext() != mContext) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.show("正在加载...");
    }

    /**
     * 弹出正在加载...对话框。
     */
    public void showLoadingDialog(String text) {
        if (loadingDialog == null || loadingDialog.getContext() != mContext) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.show(text);
    }

    @Override
    public void showLoadingDialog(String text, boolean isCancelable) {

    }

    public void dismissLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public LoadingDialog getLoadingDialog() {
        return loadingDialog;
    }

    public void setEmptyView(int layoutResId, ViewGroup viewGroup) {
        View emptyView = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
            final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
        }
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);

    }

    @Override
    public void runOnUIThread(OnMainThread onMainThread) {
        mContext.runOnUiThread(() -> {
            if (onMainThread != null) onMainThread.doInUIThread();
        });
    }

    @Override
    public void runOnWorkThread(OnBackground onBackground) {
        ThreadPoolManager.newInstance().addExecuteTask(() -> {
            if (onBackground != null) onBackground.doOnBackground();
        }, true);

    }


    @Override
    public void onStart() {
        super.onStart();
        if (isRegisteredEventBus())
            EventBusUtils.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRegisteredEventBus())
            EventBusUtils.unregister(this);
    }


}
