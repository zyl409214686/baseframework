package com.quickdev.baseframework.network;

import android.util.Log;

import com.quickdev.baseframework.utils.AppContextUtil;
import com.quickdev.baseframework.utils.MobileInfoUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpManager {
    private static final String TAG = "OkHttpManager";
    private static final int DEFAULT_TIMEOUT = 8;
    static private OkHttpManager mOkhttpManager = null;
    private InputStream mTrustrCertificate;

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

    static public OkHttpManager getInstance() {
        if (mOkhttpManager == null) {
            mOkhttpManager = new OkHttpManager();
        }
        return mOkhttpManager;
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private X509TrustManager trustManagerForCertificates()
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = null;
        Log.d(TAG, "start generateCertificates");
        try {
            certificates = certificateFactory.generateCertificates(AppContextUtil.getContext().getAssets().open(IRequest.getCrt()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (certificates.isEmpty()) {
            Log.d(TAG, "is empty");
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        Log.d(TAG, "not empty");
        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    //获取TrustManager
    private static TrustManager[] getDefaultTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }


    public void setTrustrCertificates(InputStream in) {
        mTrustrCertificate = in;
    }

    public InputStream getTrustrCertificates() {
        return mTrustrCertificate;
    }

    public OkHttpClient build() {
        OkHttpClient okHttpClient = null;
        if (getTrustrCertificates() != null) {
            TrustManager[] trustManagers;
            SSLSocketFactory sslSocketFactory;
            X509TrustManager trustManager = null;
            try {
//                if (BuildConfig.VERIFY_HTTPS) {
//                    Log.d(TAG, "VERIFY_HTTPS:" + BuildConfig.VERIFY_HTTPS);
                    trustManager = trustManagerForCertificates();
                    trustManagers = new TrustManager[]{trustManager};
//                } else {
//                    trustManagers = getDefaultTrustManager();
//                }
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagers, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request.Builder builder = getBuilder(chain);
                        Request request = builder.build();
                        Response response = chain.proceed(request);
                        return response;

                    });
//            if (!BuildConfig.VERIFY_HTTPS) {
//                Log.d(TAG, "VERIFY_HTTPS:" + BuildConfig.VERIFY_HTTPS);
                okHttpClientBuilder.sslSocketFactory(sslSocketFactory);
                okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
//            } else {
//                okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
//            }
            okHttpClient = okHttpClientBuilder.build();

        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .build();
        }
        return okHttpClient;
    }


    private static Request.Builder getBuilder(Interceptor.Chain chain) {
        Request org = chain.request();
//        String lang = (String) SpUtils.getParam("f_TronKey", AppContextUtil.getContext(),
//                AppContextUtil.getContext().getString(R.string.language_key), "1");
//
//        String chainName = (String) SpUtils.getParam("f_Tron", AppContextUtil.getContext(),
//                AppContextUtil.getContext().getString(R.string.chain_name_key), "MainChain");
        String macAddress;
        try {
            macAddress = MobileInfoUtil.getMacAddress();
        } catch (SocketException e) {
            e.printStackTrace();
            macAddress = "null_Imei_Android";
        }

        String packageName;
        try {
            packageName = AppContextUtil.getContext().getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
            packageName = "com.tronlink.wallet";
        }

        Request.Builder builder = org.newBuilder()
                .addHeader("System", "Android")
                .addHeader("Version", VersionNested.version)
                .addHeader("DeviceID", macAddress)
//                .addHeader("Lang", lang)
//                .addHeader("chain", chainName)
                .addHeader("packageName", packageName);


//        String baseUrl = org.url().toString();
////        AppContextUtil.getContext().getSharedPreferences();

//        baseUrl.replaceAll("base&url","");
        return builder;
    }
}
