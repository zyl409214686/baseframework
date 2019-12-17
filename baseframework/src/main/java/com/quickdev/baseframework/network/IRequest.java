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


//    public static final NET_ENVIRONMENT ENVIRONMENT = NET_ENVIRONMENT.valueOf(BuildConfig.API_HOST);
    public static final NET_ENVIRONMENT ENVIRONMENT = NET_ENVIRONMENT.TEST;
    public static String HOME_HOST = "https://list.tronlink.org/";//host
    public static String TEST_HOST = "https://testlist.tronlink.org/";//test
    public static String PRE_HOST = "https://testpre.tronlink.org";//pre

    public static String RELEASE_CRT = "tronlink.crt";
    public static String TEST_CRT = "test_tronlink.crt";
    public static String PRE_CRT = "pre_release_tronlink.crt";

    public static String TRONSCAN_HOST_MAINCHAIN_RELEASE = "https://tronscan.org/#/transaction/";
    public static String TRONSCAN_HOST_MAINCHAIN_TEST = "http://18.217.215.94:61/#/transaction/";
    public static String TRONSCAN_HOST_MAINCHAIN_PRE = "https://debug.tronscan.org/#/transaction/";
    public static String TRONSCAN_HOST_MAINCHAIN_NILE = " http://18.190.104.70:9000/#/transaction/";


    public static String TRONSCAN_HOST_DAPPCHAIN_RELEASE = "https://dappchain.tronscan.org/#/transaction/";
    public static String TRONSCAN_HOST_DAPPCHAIN_TEST = "http://18.217.215.94:62/#/transaction/";
    public static String TRONSCAN_HOST_DAPPCHAIN_PRE = "https://debugdappchain.transcan.org/#/transaction/";


    public static String DAPP_RELEASE = "https://dapp.tronlink.org";
    //    public static String DAPP_TEST = "http://18.222.178.103:8080/#/";
    public static String DAPP_TEST = "http://18.222.178.103:8080/#/";


    //  socket
    public static String SOCKET_HOST = "ws://list.tronlink.org/";//host
    public static String SOCKET_PRE = "ws://testpre.tronlink.org/";//pre
    public static String SOCKET_TEST = "ws://testlist.tronlink.org/";//test


//    private static final Retrofit localRetrofit = getLocalRetrofit();

    public static final String HOST = HOME_HOST;//getHost();
    public static final boolean onLine = isOnLine();

//    public static String getHost() {
//        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
//            return HOME_HOST;
//        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
//            return PRE_HOST;
//        } else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//
//            return test_host_id > 2 ? TEST_HOST : (test_host_id == 1 ? HOME_HOST : PRE_HOST);
//        }
//    }
//
//    public static boolean isTest() {
//        return ENVIRONMENT == NET_ENVIRONMENT.TEST;
//    }
//
    public static String getCrt() {
        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
            return RELEASE_CRT;
        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
            return PRE_CRT;
        }
        return "";
//        else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//            return test_host_id > 2 ? TEST_CRT : (test_host_id == 1 ? RELEASE_CRT : PRE_CRT);
//        }
    }

    private static boolean isOnLine() {
        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
            return true;
        }
        return false;
    }

//    public static String getDappUrl() {
//        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
//            return DAPP_RELEASE;
//        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
//            return DAPP_TEST;
//        } else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//            return test_host_id == 1 ? DAPP_RELEASE : DAPP_TEST;
//
//        }
//    }
//
//
//    public static String getTronscanDappUrl() {
//        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
//            return TRONSCAN_HOST_DAPPCHAIN_RELEASE;
//        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
//            return TRONSCAN_HOST_DAPPCHAIN_PRE;
//        } else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//            return test_host_id > 2 ? TRONSCAN_HOST_DAPPCHAIN_TEST : (test_host_id == 1 ? TRONSCAN_HOST_DAPPCHAIN_RELEASE : TRONSCAN_HOST_DAPPCHAIN_PRE);
//        }
//    }

//    public static String getTronscanMainUrl() {
//        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
//            return TRONSCAN_HOST_MAINCHAIN_RELEASE;
//        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
//            return TRONSCAN_HOST_MAINCHAIN_PRE;
//        } else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//            return test_host_id > 2 ? (test_host_id == 3 ? TRONSCAN_HOST_MAINCHAIN_TEST : TRONSCAN_HOST_MAINCHAIN_NILE)
//                    : (test_host_id == 1 ? TRONSCAN_HOST_MAINCHAIN_RELEASE : TRONSCAN_HOST_MAINCHAIN_PRE);
//        }
//    }
//
//
//    public static String getSocketHost() {
//        if (ENVIRONMENT == NET_ENVIRONMENT.RELEASE) {
//            return SOCKET_HOST;
//        } else if (ENVIRONMENT == NET_ENVIRONMENT.PRE_RELEASE) {
//            return SOCKET_PRE;
//        } else {
//            int test_host_id = (int) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                    AppContextUtil.getContext().getString(R.string.test_version_key), 3);
//            return test_host_id > 2 ? SOCKET_TEST : (test_host_id == 1 ? SOCKET_HOST : SOCKET_PRE);
//        }
//    }


    public static Retrofit getInstance() {
        return RetrofitNested.retrofit;
    }


    public static <T> T getAPI(Class<T> serviceapi) {
        return getInstance().create(serviceapi);
    }

//    public static <T> T getLocalAPI(Class<T> serviceapi) {
//        return localRetrofit.create(serviceapi);
//    }


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


//    private static Retrofit getLocalRetrofit() {
//        Retrofit retrofit = new Retrofit.Builder().client(OkHttpManager.getInstance().build())
//                .baseUrl("http://172.16.22.43:8090/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        return retrofit;
//    }


}
