package com.kanade.ushio.api

import com.google.gson.GsonBuilder
import com.kanade.ushio.entity.Alias
import com.kanade.ushio.entity.Info
import com.kanade.ushio.entity.WatchStatusResult
import com.kanade.ushio.entity.typeadapter.AliasTypeAdapter
import com.kanade.ushio.entity.typeadapter.InfoTypeAdapter
import com.kanade.ushio.entity.typeadapter.WatchStatusResultTypeAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private const val API_HOST = "http://api.bgm.tv/"

    val retrofit: Retrofit by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .authenticator(TokenAuthenticator())
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(TokenInterceptor())
                .addInterceptor(AuthInterceptor())
                .build()

        val gson = GsonBuilder()
                .registerTypeAdapter(WatchStatusResult::class.java, WatchStatusResultTypeAdapter())
                .registerTypeAdapter(Info::class.java, InfoTypeAdapter())
                .registerTypeAdapter(Alias::class.java, AliasTypeAdapter())
                .create()

        Retrofit.Builder()
                .baseUrl(API_HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }
}