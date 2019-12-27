package com.quickdev.quickdevframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseModel;
import com.quickdev.baseframework.base.BasePresenter;
import com.quickdev.baseframework.bean.EventMessage;
import com.quickdev.baseframework.utils.EventBusUtils;
import com.quickdev.baseframework.utils.LogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.observers.DisposableObserver;

import static com.quickdev.baseframework.base.BaseActivity.HEADER_TYPE.TYPE_HEADER;


public class MainActivity extends BaseActivity<BasePresenter, BaseModel> {
    private TestDialog mTestDialog;
    @BindView(R.id.bt_test)
    Button mTestBtn;

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

    @OnClick({R.id.bt_test, R.id.bt_eventbus})
    public void onClickView(View view) {
        switch (view.getId()){
            case R.id.bt_test:
                if(!mTestDialog.getDialog().isShowing())
                    mTestDialog.show(getSupportFragmentManager(), "testdialog");
                else{
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
        }
    }

    @Override
    public boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
        if(event.getCode() == EventMessageConstants.EVENT_A){
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container,  TestFragment.newInstance()).commit();
            Toast.makeText(mContext, "receive EVENT_A evnet and currentThread is:"+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
