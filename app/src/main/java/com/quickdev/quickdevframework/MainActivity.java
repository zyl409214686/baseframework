package com.quickdev.quickdevframework;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseModel;
import com.quickdev.baseframework.base.BasePresenter;
import com.quickdev.baseframework.bean.EventMessage;
import com.quickdev.baseframework.utils.EventBusUtils;
import com.quickdev.baseframework.utils.LogUtils;
import com.quickdev.quickdevframework.utils.permission.PermissionHelper;
import com.quickdev.quickdevframework.utils.permission.PermissionInterface;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;

import static com.quickdev.baseframework.base.BaseActivity.HEADER_TYPE.TYPE_HEADER;


public class MainActivity extends BaseActivity<BasePresenter, BaseModel> implements PermissionInterface {
    private TestDialog mTestDialog;
    @BindView(R.id.bt_test)
    Button mTestBtn;
    @BindView(R.id.iv)
    ImageView iv;
    private PermissionHelper mPermissionHelper;

    @Override
    public void setContentViewAfter(Bundle savedInstanceState) {
        setHeaderBar("hello world");
        showLoadingDialog();
        mTestDialog = new TestDialog();
        mTestDialog.setShowBottomEnable(false);
        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestDialog.show(getSupportFragmentManager(), "testdialog");
                LogUtils.d("getTop is requesting");
                new DappModel().getTop(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        LogUtils.d(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("getTop is onComplete");
                    }
                });
            }
        });
//        mTestDialog.dismiss();

        //        测试加载glide
        String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578396117607&di=947d64b8a4039288038289203d7deadd&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D1160838793%2C3532850507%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D1280";
        Glide.with(MainActivity.this)
                .load(imgUrl)
                .error(R.mipmap.test_bg)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("xiangxiang", "onLoadFailed");
                        iv.setImageResource(R.mipmap.ic_launcher_round);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i("xiangxiang", "onResourceReady");
                        iv.setImageResource(R.mipmap.ic_launcher);
                        return false;
                    }
                })
                .into(iv);

    }

    @Override
    public void setContentViewBefore(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public HEADER_TYPE getHeaderType() {
        return TYPE_HEADER;
    }

    @Override
    protected void onLeftButtonClick() {
        super.onLeftButtonClick();
        finish();
    }

    @OnClick({R.id.bt_test, R.id.bt_eventbus, R.id.bt_permission})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.bt_test:
                if (!mTestDialog.getDialog().isShowing())
                    mTestDialog.show(getSupportFragmentManager(), "testdialog");
                else {
                    mTestDialog.dismiss();
                }
                LogUtils.d("getTop is requesting");
                new DappModel().getTop(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        LogUtils.d(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("getTop is onComplete");
                    }
                });
                break;
            case R.id.bt_eventbus:
                EventBusUtils.post(new EventMessage<>(EventMessageConstants.EVENT_A, "EventData"));
                break;

            case R.id.bt_permission:
                mPermissionHelper = new PermissionHelper(MainActivity.this, this);
                mPermissionHelper.requestPermissions();
                break;


        }
    }

    @Override
    public boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
        if (event.getCode() == EventMessageConstants.EVENT_A) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, TestFragment.newInstance()).commit();
            Toast.makeText(mContext, "receive EVENT_A evnet and currentThread is:" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getPermissionsRequestCode() {
        return 20000;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        Log.i("xiangxiang", "requestPermissionsSuccess");

    }

    @Override
    public void requestPermissionsFail() {
        Log.i("xiangxiang", "requestPermissionsFail");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        try {
            if (mPermissionHelper == null) {
                mPermissionHelper = new PermissionHelper(MainActivity.this, this);
            }
            if (mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)) {
                return;
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } catch (Exception e) {
            Log.i("xiangxiang", "e -- " + e.toString());
        }
    }
}
