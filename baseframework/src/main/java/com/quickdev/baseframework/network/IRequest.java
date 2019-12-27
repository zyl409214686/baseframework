package com.quickdev.baseframework.network;



import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class IRequest {

    public enum NET_ENVIRONMENT {
        RELEASE,
        PRE_RELEASE,
        TEST,
    }


    public static final NET_ENVIRONMENT ENVIRONMENT = NET_ENVIRONMENT.TEST;
    public static String HOME_HOST = "https://list.tronlink.org/";//host

    public static String RELEASE_CRT = "tronlink.crt";
    public static String TEST_CRT = "test_tronlink.crt";
    public static String PRE_CRT = "pre_release_tronlink.crt";

    public static final String HOST = HOME_HOST;//getHost();
    public static final boolean onLine = isOnLine();

    public static String getCrt() {
        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
            return RELEASE_CRT;
        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
            return PRE_CRT;
        }
        return "";
    }

    private static boolean isOnLine() {
        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
            return true;
        }
        return false;
    }


    public static Retrofit getInstance() {
        return RetrofitNested.retrofit;
    }


    public static <T> T getAPI(Class<T> serviceapi) {
        return getInstance().create(serviceapi);
    }


    private static class RetrofitNested {
        private static final Retrofit retrofit = getRetrofit();
    }

    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().client(OkHttpManager.getInstance().build())
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


}
