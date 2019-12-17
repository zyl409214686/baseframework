package com.quickdev.quickdevframework;


import com.quickdev.baseframework.base.BaseModel;

import io.reactivex.Observable;


public interface DappContract {

    interface Model extends BaseModel {

        Observable<DappRecommendOutput> getRecommendData(String lan, String address);

    }

}
