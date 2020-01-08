package com.quickdev.quickdevframework.business.maintab;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.quickdev.baseframework.base.BaseActivity;
import com.quickdev.baseframework.base.EmptyModel;
import com.quickdev.baseframework.base.EmptyPresenter;
import com.quickdev.baseframework.base.EmptyView;
import com.quickdev.quickdevframework.R;
import com.quickdev.quickdevframework.customview.tabview.ITabView;

import butterknife.BindView;

import static com.quickdev.baseframework.base.BaseActivity.HEADER_TYPE.TYPE_NORMAL;

public class MainTabActivity extends BaseActivity<MainTabPresenter, MainTabModel> implements MainTabContract.View {
    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.home_tab)
    ITabView homeTab;
    @BindView(R.id.room_tab)
    ITabView roomTab;
    @BindView(R.id.market_tab)
    ITabView marketTab;
    @BindView(R.id.scene_tab)
    ITabView sceneTab;
    @BindView(R.id.my_tab)
    ITabView myTab;
    @BindView(R.id.root)
    RelativeLayout root;

    private static boolean isExit = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.ac_main;
    }

    @Override
    public HEADER_TYPE getHeaderType() {
        return TYPE_NORMAL;
    }

    @Override
    public boolean isRegisteredEventBus() {
        return false;
    }

    @Override
    public void setContentViewAfter(Bundle savedInstanceState) {
        mPresenter.onCreate2(savedInstanceState, getSupportFragmentManager());
        mPresenter.addSome();
        mPresenter.setOnClickListener(getSupportFragmentManager());


    }

    @Override
    public ITabView getHomeTab() {
        return homeTab;
    }

    @Override
    public ITabView getRoomTab() {
        return roomTab;
    }

    @Override
    public ITabView getMarketTab() {
        return marketTab;
    }

    @Override
    public ITabView getSceneTab() {
        return sceneTab;
    }

    @Override
    public ITabView getMyTab() {
        return myTab;
    }

    @Override
    public ViewGroup getRootTab() {
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        mPresenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitSys();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitSys() {
        if (!isExit) {
            isExit = true;
            //Press again to exit TronLink
//            Toast.makeText(getApplicationContext(), getString(R.string.exit_dec), Toast.LENGTH_SHORT).show();
            try {
                ToastAsBottom(R.string.exit_dec);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


}
