package com.quickdev.quickdevframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseFragment;
import com.quickdev.baseframework.bean.EventMessage;
import com.quickdev.baseframework.utils.EventBusUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class TestFragment extends BaseFragment {

    @BindView(R.id.bt_test)
    Button mTestBt;

    @Override
    public int getLayoutResId() {
        return R.layout.fg_test;
    }

    @Override
    public BaseActivity.HEADER_TYPE getHeaderType() {
        return BaseActivity.HEADER_TYPE.TYPE_NORMAL;
    }

    @Override
    public boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void setContentViewAfter(Bundle savedInstanceState) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
        if(event.getCode() == EventMessageConstants.EVENT_B){
            Toast.makeText(mContext, "receive EVENT_B evnet and currentThread is:"+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.bt_test)
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.bt_test:
                EventBusUtils.post(new EventMessage<>(EventMessageConstants.EVENT_B, "EventDataB"));
                break;
        }
    }

    public static TestFragment newInstance(){
        return new TestFragment();
    }
}
