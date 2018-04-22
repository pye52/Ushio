package com.kanade.ushio;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.kanade.ushio.arch.AppDatabase;

import me.yokeyword.fragmentation.Fragmentation;

public class RootApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppDatabase.init(this);

        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(BuildConfig.DEBUG ? Fragmentation.BUBBLE : Fragmentation.NONE)
                .debug(BuildConfig.DEBUG)
                .handleException(e -> {})
                .install();

        // 初始化日志组件
        LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)
                .setLog2FileSwitch(false)
                .setBorderSwitch(false);
    }
}
