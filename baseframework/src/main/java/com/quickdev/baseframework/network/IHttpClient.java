package com.quickdev.baseframework.network;


import com.quickdev.baseframework.utils.AppContextUtil;
import com.quickdev.baseframework.utils.MobileInfoUtil;

import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class IHttpClient {
    private static final int DEFAULT_TIMEOUT = 8;


    private static class VersionNested {
        static String version;
        static {
            try {
                version = AppContextUtil.getContext().getPackageManager().getPackageInfo(AppContextUtil.getContext().getPackageName(), 0).versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static OkHttpClient.Builder getHttpClientBuilder() {
        OkHttpClient.Builder Ibuilder = new OkHttpClient.Builder();
        Ibuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //忽略https证书验证 start
        Ibuilder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        Ibuilder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        //忽略https证书验证 end
        Ibuilder.addInterceptor(loggingInterceptor);
        Ibuilder.addInterceptor(chain -> {
            Request.Builder builder = getBuilder(chain);
            Request request = builder.build();
            Response response = chain.proceed(request);
            return response;
        });
        return Ibuilder;
    }


    public static OkHttpClient.Builder getHttpClientBuilder1() {
        OkHttpClient.Builder Ibuilder = new OkHttpClient.Builder();
        Ibuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Ibuilder.addInterceptor(chain -> {
            Request.Builder builder = getBuilder(chain);
            Request request = builder.build();
            Response response = chain.proceed(request);

            return response;
        });
        return Ibuilder;
    }

    private static Request.Builder getBuilder(Interceptor.Chain chain) {
        Request org = chain.request();
        String macAddress;
        try {
            macAddress = MobileInfoUtil.getMacAddress();
        } catch (SocketException e) {
            e.printStackTrace();
            macAddress = "null_Imei_Android";
        }
        Request.Builder builder = org.newBuilder()
                .addHeader("System", "Android")
                .addHeader("Version", VersionNested.version)
                .addHeader("DeviceID", macAddress);
        return builder;
    }
}



