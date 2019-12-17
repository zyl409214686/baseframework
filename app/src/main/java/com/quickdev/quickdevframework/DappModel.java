package com.quickdev.quickdevframework;


import com.quickdev.baseframework.network.IRequest;

import io.reactivex.Observable;

public class DappModel implements DappContract.Model {
    @Override
    public Observable<DappRecommendOutput> getRecommendData(String lan, String address) {
        return IRequest.getAPI(HttpAPI.class)
                .getDappRecommend(lan, address);
//                .compose(RxSchedulers.io_main());/**/
    }
//
//
//    @Override
//    public Observable<Object> updateRecommendState(String address, String id) {
//        return IRequest.getAPI(HttpAPI.class)
//                .updateRecommendState(address, id)
//                .compose(RxSchedulers.io_main());
//    }
//
//    @Override
//    public Observable<DappHotBean> getDappHotList() {
//        return IRequest.getAPI(HttpAPI.class)
//                .getDappHotList()
//                .compose(RxSchedulers.io_main());
//    }
}
