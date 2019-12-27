package com.quickdev.quickdevframework;


import com.quickdev.baseframework.network.IRequest;
import com.quickdev.baseframework.network.RetrofitFactory;
import com.quickdev.baseframework.utils.AppContextUtil;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class DappModel implements DappContract.Model {
    @Override
    public Observable<DappRecommendOutput> getRecommendData(String lan, String address) {
        return RetrofitFactory.getHttpAPI(HttpAPI.class).getDappRecommend(lan, address);
    }


    public static Observable<String> getTop() {
        return RetrofitFactory.getHttpAPI(HttpAPI.class).getTop250(1, 20);
    }

    /**
     * 获取天气数据@Query
     */
    public static void getTop(DisposableObserver<String> subscriber) {
        Observable<String> observable =  getTop();
        RetrofitFactory.getInstance(AppContextUtil.getContext()).toSubscribe(observable, subscriber);
    }
}
