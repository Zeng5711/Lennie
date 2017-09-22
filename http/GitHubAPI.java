package com.hxyd.dyt.common.http;


import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxyd.dyt.common.Constant;
import com.hxyd.dyt.common.uitl.Tools;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by win7 on 2017/2/16.
 */

public class GitHubAPI {

    private static final String TAG = "GitHubAPI";
    private static String BASE_URL = Constant.BASE_URL;
    private static final int DEFAULT_TIMEOUT = 20;

    private static Gson craetGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
    }

    private static OkHttpClient.Builder getOkHttpClient() {

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //设置超时时间
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //使用拦截器
        httpClientBuilder.addInterceptor(new RequestInterceptor());
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.sslSocketFactory(createSSLSocketFactory());
        httpClientBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
        return httpClientBuilder;
    }


    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;

        try {

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new MyTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslSocketFactory;
    }


    private static class MyTrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }


    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Logger.i("retrofitBack = " + message);
        }
    });


    private static class RequestInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request()
                    .newBuilder()
                    .addHeader("platform", Constant.PLATFORM)
                    .addHeader("osVersion", String.valueOf(android.os.Build.VERSION.SDK_INT))
                    .addHeader("model", android.os.Build.MODEL)
                    .addHeader("appVersion", Tools.getVersionName())
                    .addHeader("channel", Constant.CHANNEL)
                    .addHeader("ABI", Build.CPU_ABI)
                    .build();
            Logger.e("request = " + request.headers().toString());
            return chain.proceed(request);
        }
    }

    public static GithubServer craetRetrofit() {
        return craetRetrofit(getOkHttpClient().build());
    }

    public static GithubServer craetRetrofitProgress() {
        return craetRetrofit(ProgressHelper.addProgress(getOkHttpClient()).build());
    }

    private static GithubServer craetRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(CustomGsonConverterFactory.create(craetGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(GithubServer.class);
    }


    public static void printResponseBody(Call<ResponseBody> call) {
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                Logger.i("OK =======> " + response.body().string());
            } else {
                Logger.e("HttpCode:" + response.code());
                Logger.e("Message:" + response.message());
                Logger.e("errorBody:" + response.errorBody().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void enqueue(Call<ResponseBody> call) {
        Call<ResponseBody> call2 = call.clone();
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Logger.e("HttpCode:" + response.code());
                        Logger.e("Message:" + response.message());
                        String body = response.body().string();
                        Logger.i("OK =======> " + body);
                        Result result = craetGson().fromJson(body, Result.class);
                        Logger.i("result =======> " + result.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Logger.e("HttpCode:" + response.code());
                    Logger.e("Message:" + response.message());
                    Logger.e("errorBody:" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
