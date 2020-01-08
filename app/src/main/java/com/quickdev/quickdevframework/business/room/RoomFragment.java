package com.quickdev.quickdevframework.business.room;

import android.os.Bundle;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.BaseFragment;
import com.quickdev.baseframework.base.EmptyModel;
import com.quickdev.baseframework.base.EmptyPresenter;
import com.quickdev.baseframework.base.EmptyView;
import com.quickdev.quickdevframework.R;

/**
 * Description : 房间页面
 * Created by 宋盼想 on 2020/1/7.
 */
public class RoomFragment extends BaseFragment<EmptyPresenter, EmptyModel> implements EmptyView {
    @Override
    public int getLayoutResId() {
        return R.layout.fg_room;
    }

    @Override
    public BaseActivity.HEADER_TYPE getHeaderType() {
        return BaseActivity.HEADER_TYPE.TYPE_NORMAL;
    }

    @Override
    public boolean isRegisteredEventBus() {
        return false;
    }

    @Override
    public void setContentViewAfter(Bundle savedInstanceState) {

    }
}
