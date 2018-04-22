package com.kanade.ushio.utils

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 应用到compose改变流程流向 <br></br>
 * 从io发出，切换到主线程执行
 * @param <T> T
 * @return observable
 */
fun <T> Flowable<T>.IO2MainThread(): Flowable<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.IO2MainThread(): Maybe<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.IO2MainThread(): Single<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())