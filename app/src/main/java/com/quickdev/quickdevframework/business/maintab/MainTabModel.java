package com.quickdev.quickdevframework.business.maintab;


import com.quickdev.quickdevframework.customview.tabview.ITabView;

import java.util.List;

public class MainTabModel implements MainTabContract.Model {
    @Override
    public void click(List<ITabView> views, int position) {
        for (ITabView view : views) {
            view.setSelectState(false);
        }
        views.get(position).setSelectState(true);
    }

}
