package com.kanade.ushio.utils;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.kanade.ushio.BuildConfig;
import com.liulishuo.filedownloader.FileDownloader;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class RootApp extends Application {
    private static RootApp sRootApp;

    public synchronized static RootApp get() {
        return sRootApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sRootApp = this;

        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(BuildConfig.DEBUG ? Fragmentation.BUBBLE : Fragmentation.NONE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();

        // 初始化realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);

        // 初始化工具类
        Utils.init(this);
        FileDownloader.init(getApplicationContext());

        // 初始化日志组件
        new LogUtils.Builder()
                .setLogSwitch(BuildConfig.DEBUG)    // 设置log总开关，默认开
                .setLog2FileSwitch(false)           // 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(false)             // 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);          // log过滤器，和logcat过滤器同理，默认Verbose
    }
}
