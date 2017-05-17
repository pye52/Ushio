package com.kanade.ushio.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava工具类
 * Created by kanade on 2017/2/9.
 */

public class RxJavaUtils {
    /**
     * 应用到compose改变流程流向 <br/>
     * 从io发出，切换到主线程执行
     * @param <T> T
     * @return observable
     */
    public static <T> ObservableTransformer<T, T> IO2MainThread() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
