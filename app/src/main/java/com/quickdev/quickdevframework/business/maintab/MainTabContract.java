package com.quickdev.quickdevframework.business.maintab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.quickdev.baseframework.base.BaseModel;
import com.quickdev.baseframework.base.BasePresenter;
import com.quickdev.baseframework.base.BaseView;
import com.quickdev.quickdevframework.customview.tabview.ITabView;

import java.util.List;


public interface MainTabContract {
    interface Model extends BaseModel {
        void click(List<ITabView> views, int position);
    }

    interface View extends BaseView {
        ITabView getHomeTab();

        ITabView getRoomTab();

        ITabView getMarketTab();

        ITabView getSceneTab();

        ITabView getMyTab();

        ViewGroup getRootTab();

    }

    abstract class Presenter extends BasePresenter<Model, View> {
        protected abstract void setOnClickListener(FragmentManager fragmentTransaction);

        protected abstract void addSome();

        protected abstract void onSaveInstanceState(Bundle outState);

        protected abstract void onCreate2(Bundle savedInstanceState, FragmentManager ft);


        protected abstract void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}

