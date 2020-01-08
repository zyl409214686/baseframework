//package com.quickdev.quickdevframework.customview;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.View;
//
//import com.quickdev.quickdevframework.R;
//
//
//public class ITabView extends View {
//    //是否选中状态
//    private boolean selectState;
//    //默认图片
//    private Bitmap icon;
//    //选中图片
//    private Bitmap selectIcon;
//    //底部标题
//    private String title = "";
//    //图片宽
//    private int iconWidth;
//    //图片高
//    private int iconHeight;
//    //默认文字颜色
//    private int titleColor;
//    //默认文字颜色
//    private int titleTop;
//    //选中文字颜色
//    private int titleSelectColor;
//    private Paint mPaint;
//    //绘制时控制文本绘制的范围
//    private Rect mBound;
//    private int width;
//    private int height;
//    private float titleSize;
//    private onTabViewOnClickListener onTabViewOnClickListener;
//    private String TAG = "ITabView";
//    private int defaultColor;
//    public void setTab(Bitmap icon, Bitmap selectIcon, String title) {
//        this.icon = icon;
//        this.selectIcon = selectIcon;
//        this.title = title;
//
//    }
//
//    private void initialize(Context context, AttributeSet attrs) {
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ITabView);
//        selectState = ta.getBoolean(R.styleable.ITabView_selectState, false);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        icon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.ITabView_icons, 0), options);
//        selectIcon = BitmapFactory.decodeResource(getResources(), ta.getResourceId(R.styleable.ITabView_selectIcon, 0), options);
//
//        iconWidth = (int) ta.getDimension(R.styleable.ITabView_iconWidth, 0);
//        iconHeight = (int) ta.getDimension(R.styleable.ITabView_iconHeight, 0);
//        title = ta.getString(R.styleable.ITabView_titles);
//        titleTop = (int) ta.getDimension(R.styleable.ITabView_titleTop, 0);
//        titleColor = ta.getColor(R.styleable.ITabView_titleColor, Color.GRAY);
//        titleSelectColor = ta.getColor(R.styleable.ITabView_titleSelectColor, Color.BLACK);
//        titleSize = ta.getDimension(R.styleable.ITabView_titleSize, dp2px(15));
//        ta.recycle();
//        mPaint = new Paint();
//
//        mPaint.setAntiAlias(true);
//        mPaint.setTextSize(titleSize);
//        mBound = new Rect();
//        mPaint.getTextBounds(title, 0, title.length(), mBound);
//        mPaint.setFilterBitmap(true);
//        defaultColor = mPaint.getColor();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else {
//            int iconWidth = getPaddingLeft() + getPaddingRight() + selectIcon.getWidth();
//            int desireByTitle = getPaddingLeft() + getPaddingRight() + mBound.width();
//            width = Math.max(iconWidth, desireByTitle);
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else {
//            int desire = getPaddingTop() + getPaddingBottom() + selectIcon.getHeight() + mBound.height();
//            height = Math.min(desire, heightSize);
//
//        }
//        setMeasuredDimension(width, height);
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (null == selectIcon) {
//            throw new RuntimeException("Must be set image");
//        }
//        int left = width / 2 - iconWidth / 2;
//       int top = (int) (height - iconHeight - titleTop - titleSize) / 2;
//
//        if (selectState) {
//            canvas.drawBitmap(selectIcon, left, top, mPaint);
//            mPaint.setColor(titleSelectColor);
//        } else {
//            canvas.drawBitmap(icon, left, top, mPaint);
//            mPaint.setColor(titleColor);
//        }
//        drawTitle(canvas, top);
//        mPaint.setColor(defaultColor);
//    }
//
//    private void drawTitle(Canvas canvas, int top) {
//        canvas.drawText(title, width / 2 - mBound.width() * 1.0f / 2, top + iconHeight + titleTop + titleSize - getPaddingBottom(), mPaint);
//    }
//
//    private int dp2px(int dp) {
//        float density = getContext().getResources().getDisplayMetrics().density;
//        return (int) (dp * density + .5f);
//    }
//
//    /**
//     * 设置选中状态
//     *
//     * @param flag 是否选中
//     */
//    public void setSelectState(boolean flag) {
//        this.selectState = flag;
//        postInvalidate();
//    }
//
//
//    public void setOnBottomTabViewOnClickListener(onTabViewOnClickListener onBottomTabViewOnClickListener, String TAG) {
//        this.onTabViewOnClickListener = onBottomTabViewOnClickListener;
//        this.TAG = TAG;
//    }
//
//    public ITabView(Context context) {
//        this(context, null);
//    }
//
//    public ITabView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public ITabView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initialize(context, attrs);
//    }
//
//    public void setText(String title) {
//        this.title = title;
//        postInvalidate();
//    }
//}