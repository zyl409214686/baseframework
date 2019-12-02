package com.quickdev.baseframework.utils;

import android.app.Application;
import android.content.Context;

public class AppContextUtil {

    private static Context mContext;
    private static Application mApplication;

    public static void init(Context context, boolean isApplication) {
        mContext = context;
        try {
            if (isApplication) {
                mApplication = (Application) context;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        if (mContext == null) throw new IllegalArgumentException("need init");
        return mContext;
    }


    public static Application getmApplication() {
        if (mApplication == null) throw new IllegalArgumentException("need init");
        return mApplication;
    }


}
