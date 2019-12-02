package com.quickdev.baseframework.base.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quickdev.baseframework.R;
import com.quickdev.baseframework.R2;
import com.quickdev.baseframework.base.BaseHolder;
import com.quickdev.baseframework.base.interfaces.OnHeaderClickListener;
import com.quickdev.baseframework.utils.AppContextUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class HeaderLayout extends RelativeLayout {

    private View headerBar;
    private View progressPageView;

    private LayoutInflater layoutInflater;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_PROGRESS = 2;
    private static final int TYPE_HEADER_PROGRESS = 3;
    private static final int TYPE_NOHEADER_STATUSBAR = 4;
    private static final int TYPE_NOHEADER_STATUSBAR_PROGRESS = 5;
    private static final int TYPE_BLUE_HEADER = 6;

    private OnHeaderClickListener mListener;
//    private ProgressHolder progressHolder;
    private HeaderHolder headerHolder;
    private int statusBarHeight;

    public HeaderLayout(Context context, int layoutResourceId, int type) {
        super(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        statusBarHeight = getStatusBarHeight(context);
        setHeaderLayout(context, layoutResourceId, type);
    }

    public HeaderLayout(Context context, LayoutInflater inflater, int layoutResourceId, int type) {
        super(context);
        layoutInflater = inflater;
        statusBarHeight = getStatusBarHeight(context);
        setHeaderLayout(context, layoutResourceId, type);
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        if (context != null) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    private void setHeaderLayout(Context context, int layoutResourceId, int type) {
        switch (type) {
            case TYPE_PROGRESS:
                addProgressPage();
                break;
            case TYPE_HEADER:
                addHeaderBar();
                break;
            case TYPE_HEADER_PROGRESS:
                addHeaderBar();
                addProgressPage();
                break;
            case TYPE_NOHEADER_STATUSBAR:
                addHeaderBar();
                headerHolder.backview.setVisibility(GONE);
                break;
            case TYPE_NOHEADER_STATUSBAR_PROGRESS:
                addHeaderBar();
                headerHolder.backview.setVisibility(GONE);
                addProgressPage();
                break;
            case TYPE_BLUE_HEADER:
                addHeaderBar();
                headerHolder.statusbar.setVisibility(INVISIBLE);
//                headerHolder.view.setBackgroundResource(R.mipmap-xxhdpi.header_top_bg);
//                headerHolder.ivCommonLeft.setBackgroundResource(R.mipmap-xxhdpi.ic_left_white);
                headerHolder.tvCommonTitle.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }

        View view = layoutInflater.inflate(layoutResourceId, null);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (progressPageView != null) {
//            params.addRule(RelativeLayout.BELOW, R.id.root_progress_page);
        } else {
            params.addRule(RelativeLayout.BELOW, R.id.root_header_bar);
        }
        addView(view, params);
    }

    public void setHeaderClickListener(OnHeaderClickListener listener) {
        mListener = listener;
    }

    protected void addHeaderBar() {
        headerBar = layoutInflater.inflate(R.layout.header_bar, null);
        headerHolder = new HeaderHolder(headerBar);
        LayoutParams statusParams = new LayoutParams(LayoutParams.MATCH_PARENT, statusBarHeight);
        headerHolder.statusbar.setLayoutParams(statusParams);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //居顶。
//        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);


        addView(headerBar, params);
    }

    protected void addProgressPage() {
//        progressPageView = layoutInflater.inflate(R.layout.progress_page, null);
//        progressHolder = new ProgressHolder(progressPageView);
        progressPageView.setOnClickListener(onClicker);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.root_header_bar);
        addView(progressPageView, params);

        showLoadingPage();
    }

    private OnClickListener onClicker = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
//            if (i == R.id.root_progress_page) {
//                if (progressHolder.rlNodata.getVisibility() == VISIBLE && progressHolder.rlReload.getVisibility() == VISIBLE) {
//                    progressHolder.tvReload.setVisibility(GONE);
//                    progressHolder.btLoadding.setVisibility(VISIBLE);
//                    startAnima(progressHolder.btLoadding);
//                    mListener.onClickReLoadButton();
//                }
//
//            } else {
//            }
        }
    };

    public void showOrHideHeader(boolean isShow) {
        if (headerHolder == null) return;
        if (isShow)
            headerHolder.backview.post(() -> headerHolder.backview.setVisibility(VISIBLE));
        else
            headerHolder.backview.post(() -> headerHolder.backview.setVisibility(GONE));
    }

    /**
     * 显示“返回”按钮和title
     *
     * @param titleText
     */
    public void setHeaderBar(String titleText) {
        //显示返回按钮
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(GONE);

        headerHolder.tvCommonTitle.setText(titleText);
    }


    /**
     * 显示左边按钮、title、右边按钮
     *
     * @param leftText
     * @param titleText
     * @param rightText
     */
    public void setHeaderBar(String leftText, String titleText, String rightText) {
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setText(rightText);
        headerHolder.tvCommonTitle.setText(titleText);
    }

    /**
     * 获取 headerBar
     */
    public View getHeadBar() {
        return headerBar;
    }

    /**
     * 显示左边按钮、title、右边图片按钮
     *
     * @param titleText
     * @param imageRes  为0 可不传 默认是扫一扫
     */
    public void setHeaderBarSpe(String titleText, int imageRes) {
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
        headerHolder.ivQr.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(GONE);
        if (imageRes != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                headerHolder.ivQr.setBackground(null);
            }
            headerHolder.ivQr.setImageResource(imageRes);
        }

        headerHolder.tvCommonTitle.setText(titleText);
    }

    /**
     * 显示左边按钮、title、右边图片按钮
     *
     * @param titleText
     * @param imageRes  为0 可不传 默认是扫一扫
     */
    public void setHeaderBar(String titleText, int imageRes) {
        headerHolder.rlRight.setVisibility(View.VISIBLE);
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
        headerHolder.ivQr.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(GONE);
        if (imageRes != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                headerHolder.ivQr.setBackground(null);
            }
            headerHolder.ivQr.setImageResource(imageRes);
        }
        headerHolder.tvCommonTitle.setText(titleText);
    }


    /**
     * 显示右侧刷新图标按钮
     *
     * @param imageRes 为0  默认是刷新
     */
    public void setRightBar(int imageRes) {
        headerHolder.rlIconRight.setVisibility(VISIBLE);
        if (imageRes != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                headerHolder.ivRefresh.setBackground(null);
            }
            headerHolder.ivRefresh.setImageResource(imageRes);
        }
    }


    /**
     * 显示左边按钮、title、右边文字
     *
     * @param titleText
     * @param right     为null 可不传 默认是从相册选择
     */
    public void setHeaderBarAndRightTv(String titleText, String right) {
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(GONE);
        headerHolder.ivQr.setVisibility(GONE);
//        if (!StringUtils.isEmpty(right)) {
//            headerHolder.tvBgRight.setVisibility(VISIBLE);
//            headerHolder.tvBgRight.setText(right);
//        }

        headerHolder.tvCommonTitle.setText(titleText);
    }


    public void setRightTextShow(boolean isShow) {
        headerHolder.tvBgRight.setVisibility(isShow ? VISIBLE : GONE);
    }


    /**
     * 显示左侧关闭按钮
     *
     * @param imageRes 为0 可不传 默认是关闭
     */
    public void setLeftBar(int imageRes) {
        headerHolder.llClose.setVisibility(VISIBLE);
        headerHolder.ivClose.setVisibility(VISIBLE);
        if (imageRes != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                headerHolder.ivClose.setBackground(null);
            }
            headerHolder.ivClose.setImageResource(imageRes);
        }
    }

    /**
     * 显示左边按钮、title、右边文字
     *
     * @param titleText
     * @param right     为null 可不传 默认是从相册选择
     */
    public void setHeaderBar(String titleText, String right) {
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
        headerHolder.ivQr.setVisibility(GONE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
//        if (!StringUtils.isEmpty(right)) {
//            headerHolder.tvCommonRight.setText(right);
//        }

        headerHolder.tvCommonTitle.setText(titleText);
    }

    /**
     * 显示左边按钮、title、右边文字
     *
     * @param titleText
     * @param right     为null 可不传 默认是从相册选择
     */
    public void setWhiteHeaderBar(String titleText, String right) {
        headerHolder.ivCommonLeft.setVisibility(VISIBLE);
        headerHolder.tvCommonTitle.setVisibility(VISIBLE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
        headerHolder.ivQr.setVisibility(GONE);
        headerHolder.tvCommonRight.setVisibility(VISIBLE);
//        if (!StringUtils.isEmpty(right)) {
//            headerHolder.tvCommonRight.setText(right);
//        }

        headerHolder.tvCommonTitle.setText(titleText);
    }

    public String getTitle() {
        if (headerHolder.tvCommonTitle != null) {
            return headerHolder.tvCommonTitle.getText().toString();
        }
        return "";
    }

    public void setTitle(String title) {
        if (headerHolder.tvCommonTitle != null) {
            headerHolder.tvCommonTitle.setText(title);
        }
    }

    public void setCommonTitle2(String title) {

        if (headerHolder.tvCommonTitle2 != null) {
            headerHolder.tvCommonTitle2.setVisibility(View.VISIBLE);
            headerHolder.tvCommonTitle2.setText(title);
        }
    }

    public void setMiddleTitle(String title) {
        if (headerHolder.tvMiddleTitle != null) {
            headerHolder.tvMiddleTitle.setVisibility(View.VISIBLE);
            headerHolder.tvMiddleTitle.setText(title);
        }
    }


    public void setCommonRightTitle2(String title) {
        if (headerHolder.tvCommonRight2 != null) {
            headerHolder.rlRightshare.setVisibility(VISIBLE);
            headerHolder.tvCommonRight2.setVisibility(View.VISIBLE);
            headerHolder.tvCommonRight2.setText(title);
            headerHolder.ivShare.setVisibility(GONE);
        }
    }


    public void setRightBold() {
        headerHolder.tvCommonRight.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }

    /**
     * 隐藏左边箭头
     */
    public void hideHeaderLeftButton() {
        headerHolder.ivCommonLeft.setVisibility(GONE);
    }


    /**
     * 展示正在加载...
     */
    public void showLoadingPage() {
//        showLoadingPage(StringUtils.getResString(R.string.loading));
    }

    /**
     * 展示正在加载界面。
     *
     * @param loadingText
     */
    public void showLoadingPage(String loadingText) {
        if (null != progressPageView) {
            progressPageView.setVisibility(VISIBLE);
            //
//            progressHolder.rlLoading.setVisibility(VISIBLE);
//            progressHolder.llDialog.setVisibility(VISIBLE);
//            progressHolder.rlNodata.setVisibility(GONE);
//            //
//            progressHolder.loadingView.setVisibility(VISIBLE);
//            startAnima(progressHolder.loadingView);
//            progressHolder.ivLoaderror.setVisibility(GONE);
//            progressHolder.tvLoading.setText(loadingText);
        }
    }

    private void startAnima(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0F, -360F);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    public void hideLoadingPageDialog() {
        if (null != progressPageView) {
//            progressHolder.llDialog.setVisibility(GONE);
        }
    }


    public void showLoadingPageDialog() {
        if (null != progressPageView) {
//            progressHolder.llDialog.setVisibility(GONE);
        }
    }

    /**
     * 加载成功，隐藏加载页
     */
    public void dismissLoadingPage() {
        if (null != progressPageView) {
            progressPageView.setVisibility(GONE);
        }
    }

    /**
     * 网络异常，展示重新加载按钮。
     */
    public void showErrorPage() {
//        showErrorPage(R.mipmap-xxhdpi.net_error, StringUtils.getResString(R.string.net_error));
    }

    /**
     * 网络异常，展示重新加载按钮。
     */
    public void showErrorPage(int imgResID, String errorText) {
//        if (null != progressPageView) {
//            progressPageView.setVisibility(VISIBLE);
//            //
//            progressHolder.rlLoading.setVisibility(GONE);
//            progressHolder.rlNodata.setVisibility(VISIBLE);
//            progressHolder.rlReload.setVisibility(VISIBLE);
//            progressHolder.tvReload.setVisibility(VISIBLE);
//            progressHolder.btLoadding.setVisibility(GONE);
//            //
//            if (0 != imgResID) {
//                progressHolder.ivNodata.setImageResource(imgResID);
//            }
//            if (!StringUtils.isNullOrEmpty(errorText)) {
//                progressHolder.tvMsg.setText(errorText);
//            }
//        }
    }

    /**
     * 返回无数据，展示未找到相关数据
     */
    public void showNoDatasPage() {
//        showNoDatasPage(R.mipmap-xxhdpi.ic_nodata, StringUtils.getResString(R.string.nodata));
    }

    /**
     * 返回无数据，展示空数据页。
     *
     * @param noDatasText
     */
    public void showNoDatasPage(int imgResID, String noDatasText) {
        if (null != progressPageView) {
//            progressPageView.setVisibility(VISIBLE);
//            //
//            progressHolder.rlLoading.setVisibility(GONE);
//            progressHolder.rlNodata.setVisibility(VISIBLE);
//            progressHolder.rlReload.setVisibility(GONE);
//            //
//            if (0 != imgResID) {
//                progressHolder.ivNodata.setImageResource(imgResID);
            }
//            if (!StringUtils.isNullOrEmpty(noDatasText)) {
//                progressHolder.tvMsg.setText(noDatasText);
//            }
        }
//    }

    public HeaderHolder getHeaderHolder() {
        return headerHolder;
    }

//    static class ProgressHolder extends BaseHolder {
//        @BindView(R2.id.loadingview)
//        ImageView loadingView;
//        @BindView(R2.id.iv_loaderror)
//        ImageView ivLoaderror;
//        @BindView(R2.id.tv_loading)
//        TextView tvLoading;
//        @BindView(R2.id.ll_dialog)
//        LinearLayout llDialog;
//        @BindView(R2.id.rl_loading)
//        RelativeLayout rlLoading;
//        @BindView(R2.id.iv_nodata)
//        ImageView ivNodata;
//        @BindView(R2.id.tv_msg)
//        TextView tvMsg;
//        @BindView(R2.id.tv_reload)
//        TextView tvReload;
//        @BindView(R2.id.rl_nodata)
//        RelativeLayout rlNodata;
//        @BindView(R2.id.rl_reload)
//        RelativeLayout rlReload;
//        @BindView(R2.id.bt_loadding)
//        ImageView btLoadding;
//
//        ProgressHolder(View view) {
//            super(view);
//        }
//    }

    public class HeaderHolder extends BaseHolder {
        @BindView(R2.id.iv_common_left)
        public ImageView ivCommonLeft;
        @BindView(R2.id.ll_common_left)
        public LinearLayout llCommonLeft;
        @BindView(R2.id.tv_common_title)
        public TextView tvCommonTitle;
        @BindView(R2.id.tv_common_right)
        public TextView tvCommonRight;
        @BindView(R2.id.statusbar)
        public LinearLayout statusbar;
        @BindView(R2.id.iv_qr)
        public ImageView ivQr;
        @BindView(R2.id.middle_title)
        public TextView tvMiddleTitle;
        @BindView(R2.id.backview)
        public RelativeLayout backview;
        @BindView(R2.id.ll_close)
        public LinearLayout llClose;
        @BindView(R2.id.rl_icon_right)
        public RelativeLayout rlIconRight;
        @BindView(R2.id.iv_refresh)
        public ImageView ivRefresh;
        @BindView(R2.id.iv_close)
        public ImageView ivClose;
        @BindView(R2.id.tv_bg_right)
        public TextView tvBgRight;
        @BindView(R2.id.tv_common_title2)
        public TextView tvCommonTitle2;
        @BindView(R2.id.iv_common_title2)
        public ImageView ivCommonTitle2;
        @BindView(R2.id.tv_common_right2)
        public TextView tvCommonRight2;
        @BindView(R2.id.iv_share)
        public ImageView ivShare;
        public View view;
        @BindView(R2.id.rl_right_share)
        public RelativeLayout rlRightshare;
        @BindView(R2.id.rl_right)
        public RelativeLayout rlRight;

        @OnClick({R2.id.ll_common_left, R2.id.tv_common_right, R2.id.iv_qr, R2.id.ll_close, R2.id.rl_icon_right, R2.id.tv_bg_right, R2.id.iv_refresh})
        public void onViewClicked(View view) {
            int i = view.getId();
            if (i == R.id.ll_common_left) {
                if (null != mListener) mListener.onClickLeftButton();

            } else if (i == R.id.iv_qr || i == R.id.tv_common_right) {
                if (null != mListener) mListener.onClickRightButton();

            } else if (i == R.id.ll_close) {
                if (null != mListener) mListener.onClickCloseButton();

            } else if (i == R.id.rl_icon_right) {
                if (null != mListener) mListener.onClickRefreshButton();

            } else if (i == R.id.tv_bg_right)
                if (null != mListener) mListener.onClickRightTv();
        }

        public HeaderHolder(View view) {
            super(view);
            this.view = view;
            expandViewTouchDelegate(ivQr, dip2px(20), dip2px(10), dip2px(10), dip2px(10));
        }

    }

    public void expandViewTouchDelegate(final View view, final int left,
                                        final int top, final int right, final int bottom) {

        ((View) view.getParent()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);
                bounds.top -= dip2px(top);
                bounds.bottom += dip2px(bottom);
                bounds.left -= dip2px(left);
                bounds.right += dip2px(right);

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        }, 1000);
    }

    public static int dip2px(int dip) {
        final float scale = AppContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

}
