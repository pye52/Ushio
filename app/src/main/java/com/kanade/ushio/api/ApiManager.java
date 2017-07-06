package com.kanade.ushio.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * In fact, you'd better ask @Sai for the Bangumi API document.
 */
public class ApiManager {
    public final static String SOURCE = "onAir";
    private final static String API_HOST = "http://api.bgm.tv";
    private final static String URL_RAKUEN = "http://bangumi.tv/m";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (ApiManager.class) {
                if (retrofit == null) {
                    retrofit = initRetrofit();
                }
            }
        }
        return retrofit;
    }

    private static Retrofit initRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
