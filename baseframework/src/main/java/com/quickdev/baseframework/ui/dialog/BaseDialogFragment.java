package com.quickdev.baseframework.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.quickdev.baseframework.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends DialogFragment {

    /*
     *需要能自定义的功能
     * 1.可以设置dialog的宽高
     * 2.可以设置dialog的背景透明度
     * 3.可以设置dialog的显示位置
     * 4.可以设置dialog的外边距
     * 5.可以自定义dialog的布局
     * 6.可以设置dialog的进出动画
     */

    private int mMargin = 0; //外边距
    private float mDimAmout = 0.5f; //背景透明度
    private boolean mShowBottomEnable = true;//是否显示底部
    private int mWidth, mHeight;//宽高
    private int mContentViewId;  //布局id
    private int mAnimStyle = 0; //进出动画
    private boolean mOutCancel = true;//点击外部取消
    private Context mContext;
    private View mView;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置BaseDialog的样式
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.base_dialog);
        // mContentViewId = getLayoutId();  //获取布局Id
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        if (null == mView) {
            mContentViewId = getContentLayoutId();
            mView = inflater.inflate(mContentViewId, container, false);
        }
        // convertView(BaseHolder,this);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    protected abstract int getContentLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mView, savedInstanceState, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();//这个方法可以写成抽象方法，在具体的dialog里面进行具体的设置 当然这里写一个通用的也很好
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //初始化dialog
    private void initParams() {
        //因为dialogfragment 也是一种特殊的window
        //为什么在这里获取可以看一下fragment 的生命周期
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = mDimAmout;

            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM;
            }

            //设置width
            if (mWidth == 0) {
                params.width = getScreenWidth(mContext) - 2 * dp2px(mContext, mMargin);
            } else {
                params.width = dp2px(mContext, mWidth);
            }

            //设置height
            if (mHeight == 0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                params.height = dp2px(mContext, mHeight);
            }

            //设置动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle);
            }
            //设置窗口的属性
            window.setAttributes(params);
        }
        setCancelable(mOutCancel);
    }

    //操作dialog布局
    protected abstract void initView(View mView, Bundle savedInstanceState, BaseDialogFragment dialog);

    //float 区间 在0——1
    public void setDimAmout(@FloatRange(from = 0, to = 1) float mDimAmout) {
        this.mDimAmout = mDimAmout;
    }

    public void setShowBottomEnable(boolean mShowBottomEnable) {
        this.mShowBottomEnable = mShowBottomEnable;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setOutCancel(boolean mOutCancel) {
        this.mOutCancel = mOutCancel;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
