package com.quickdev.quickdevframework.business.maintab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.quickdev.quickdevframework.R;
import com.quickdev.quickdevframework.appconfig.AppConfig;
import com.quickdev.quickdevframework.business.home.HomeFragment;
import com.quickdev.quickdevframework.business.market.MarketFragment;
import com.quickdev.quickdevframework.business.my.MyFragment;
import com.quickdev.quickdevframework.business.room.RoomFragment;
import com.quickdev.quickdevframework.business.scene.SceneFragment;
import com.quickdev.quickdevframework.customview.tabview.ITabView;

import java.util.ArrayList;
import java.util.List;


public class MainTabPresenter extends MainTabContract.Presenter implements View.OnClickListener {
    private HomeFragment mHomeFragment;
    private RoomFragment mRoomFragment;
    private MarketFragment mMarketFragment;
    private SceneFragment mSceneFragment;
    private MyFragment mMyFragment;
    private FragmentManager mFragmentManager;
    private List<ITabView> mLists = new ArrayList<>();


    @Override
    protected void onStart() {

    }

    @Override
    protected void setOnClickListener(FragmentManager fragmentTransaction) {
        this.mFragmentManager = fragmentTransaction;
        mLists.add(mView.getHomeTab());
        mLists.add(mView.getRoomTab());
        mLists.add(mView.getMarketTab());
        mLists.add(mView.getSceneTab());
        mLists.add(mView.getMarketTab());
        mLists.add(mView.getMyTab());
        mModel.click(mLists, 0);

        mView.getHomeTab().setOnClickListener(this);
        mView.getRoomTab().setOnClickListener(this);
        mView.getMarketTab().setOnClickListener(this);
        mView.getSceneTab().setOnClickListener(this);
        mView.getMyTab().setOnClickListener(this);
        addOrShowHomeFragment();

    }

    private void addOrShowHomeFragment() {
        if (mHomeFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().add(R.id.main, mHomeFragment, AppConfig.M_HOME).show(mHomeFragment)
                    .hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void addSome() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mHomeFragment != null) {
            mFragmentManager.putFragment(outState, AppConfig.M_HOME, mHomeFragment);
        }
        if (mRoomFragment != null) {
            mFragmentManager.putFragment(outState, AppConfig.M_ROOM, mRoomFragment);
        }
        if (mMarketFragment != null) {
            mFragmentManager.putFragment(outState, AppConfig.M_MARKET, mMarketFragment);
        }
        if (mSceneFragment != null) {
            mFragmentManager.putFragment(outState, AppConfig.M_SCENE, mSceneFragment);
        }
        if (mMyFragment != null) {
            mFragmentManager.putFragment(outState, AppConfig.M_MY, mMyFragment);
        }

    }

    @Override
    protected void onCreate2(Bundle savedInstanceState, FragmentManager ft) {
        getFragmentForFragmentManager(savedInstanceState, ft);
        initHomeFragment(ft);
        initRoomFragment(ft);
        initMarketFragment(ft);
        initSceneFragment(ft);
        initMyFragment(ft);
    }


    private void getFragmentForFragmentManager(Bundle savedInstanceState, FragmentManager ft) {
        if (savedInstanceState != null) {
            mHomeFragment = (HomeFragment) ft.getFragment(savedInstanceState, AppConfig.M_HOME);
            mRoomFragment = (RoomFragment) ft.getFragment(savedInstanceState, AppConfig.M_ROOM);
            mMarketFragment = (MarketFragment) ft.getFragment(savedInstanceState, AppConfig.M_MARKET);
            mSceneFragment = (SceneFragment) ft.getFragment(savedInstanceState, AppConfig.M_SCENE);
            mMyFragment = (MyFragment) ft.getFragment(savedInstanceState, AppConfig.M_MY);
        }
    }


    private void initHomeFragment(FragmentManager ft) {
        Fragment fragment;
        if (null == mHomeFragment) {
            fragment = ft.findFragmentByTag(AppConfig.M_HOME);
            if (fragment != null) {
                mHomeFragment = (HomeFragment) fragment;
            } else
                mHomeFragment = new HomeFragment();
        }
    }

    private void initRoomFragment(FragmentManager ft) {
        Fragment fragment;
        if (null == mRoomFragment) {
            fragment = ft.findFragmentByTag(AppConfig.M_ROOM);
            if (fragment != null) {
                mRoomFragment = (RoomFragment) fragment;
            } else
                mRoomFragment = new RoomFragment();
        }
    }


    private void initMyFragment(FragmentManager ft) {
        Fragment fragment;
        if (null == mMyFragment) {
            fragment = ft.findFragmentByTag(AppConfig.M_MY);
            if (fragment != null) {
                mMyFragment = (MyFragment) fragment;
            } else
                mMyFragment = new MyFragment();
        }
    }

    private void initSceneFragment(FragmentManager ft) {
        Fragment fragment;
        if (null == mSceneFragment) {
            fragment = ft.findFragmentByTag(AppConfig.M_SCENE);
            if (fragment != null) {
                mSceneFragment = (SceneFragment) fragment;
            } else
                mSceneFragment = new SceneFragment();
        }
    }

    private void initMarketFragment(FragmentManager ft) {
        Fragment fragment;
        if (null == mMarketFragment) {
            fragment = ft.findFragmentByTag(AppConfig.M_MARKET);
            if (fragment != null) {
                mMarketFragment = (MarketFragment) fragment;
            } else
                mMarketFragment = new MarketFragment();
        }
    }


    @Override
    protected void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                              @NonNull int[] grantResults) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab:
                clickHomeTab();
                break;
            case R.id.room_tab:
                clickRoomTab();
                break;
            case R.id.market_tab:
                clickMarketTab();
                break;
            case R.id.scene_tab:
                clickSceneTab();
                break;
            case R.id.my_tab:
                clickMyTab();
                break;
        }

    }


    private void clickHomeTab() {
        mModel.click(mLists, 0);
        if (mHomeFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().remove(mHomeFragment).commitAllowingStateLoss();
            mFragmentManager.beginTransaction().add(R.id.main, mHomeFragment, AppConfig.M_HOME).show(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        }
    }

    private void clickRoomTab() {
        mModel.click(mLists, 1);
        if (mRoomFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mRoomFragment).hide(mHomeFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().remove(mRoomFragment).commitAllowingStateLoss();
            mFragmentManager.beginTransaction().add(R.id.main, mRoomFragment, AppConfig.M_ROOM).show(mRoomFragment).hide(mHomeFragment).hide(mMarketFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        }
    }

    private void clickMarketTab() {
        mModel.click(mLists, 2);
        if (mMarketFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mMarketFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().remove(mMarketFragment).commitAllowingStateLoss();
            mFragmentManager.beginTransaction().add(R.id.main, mMarketFragment, AppConfig.M_MARKET).show(mMarketFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mSceneFragment).hide(mMyFragment).commitAllowingStateLoss();
        }
    }


    private void clickSceneTab() {
        mModel.click(mLists, 3);
        if (mSceneFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mSceneFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mMyFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().remove(mSceneFragment).commitAllowingStateLoss();
            mFragmentManager.beginTransaction().add(R.id.main, mSceneFragment, AppConfig.M_SCENE).show(mSceneFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mMyFragment).commitAllowingStateLoss();
        }
    }


    private void clickMyTab() {
        mModel.click(mLists, 4);
        if (mMyFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mMyFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().remove(mMyFragment).commitAllowingStateLoss();
            mFragmentManager.beginTransaction().add(R.id.main, mMyFragment, AppConfig.M_MY).show(mMyFragment).hide(mHomeFragment).hide(mRoomFragment).hide(mMarketFragment).hide(mSceneFragment).commitAllowingStateLoss();
        }
    }
}
