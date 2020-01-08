package com.quickdev.quickdevframework.db.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.quickdev.baseframework.utils.AppContextUtil;
import com.quickdev.quickdevframework.bean.TestBean;
import com.quickdev.quickdevframework.db.dao.DaoUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestBeanDaoManager {
    public static final String TAG = "AssetsHomeDataDaoManager";

    public static boolean clearAndAdd(List<TestBean> data) {
        return clearAndAdd(AppContextUtil.getContext(), data);
    }

    public static boolean clearAndAdd(Context context, final List<TestBean> data) {

        try {
            DaoUtils.getInstance(context).daoSession.callInTx((Callable<Object>) () -> {
                DaoUtils.getInstance(context).deleteAll(TestBean.class);
                DaoUtils.getInstance(context).insertMultObject(data);
                return null;
            });
        } catch (
                Exception e)

        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //查询全部数据
    public static CopyOnWriteArrayList getAllAddedTokenList(Context context) {

        List<TestBean> topBeans = DaoUtils.getInstance(context).daoSession.getTestBeanDao().queryBuilder().list();//.orderAsc(TokenBeanDao.Properties.ShortName)

        CopyOnWriteArrayList beanList = new CopyOnWriteArrayList();

        beanList.addAll(topBeans);

        return beanList;
    }

    //    更新数据
    public static void updateUnAddToken(List<TestBean> tokens) {
        if (tokens == null || tokens.size() <= 0)
            return;
        DaoUtils.getInstance().updateMultObject(tokens, TestBean.class);
    }

}
