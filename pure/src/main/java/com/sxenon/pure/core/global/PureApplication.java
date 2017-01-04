package com.sxenon.pure.core.global;

import android.app.Application;

/**
 * 做最纯粹的Application二次封装
 * Created by Sui on 2016/11/24.
 */

public abstract class PureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext.INSTANCE.value = this;
        initDebugConfig();
    }

    /**
     * Recommend
     * 1、LeakCanary
     * 2、Stetho
     * 3、StrictMode
     * 4、Bugly
     */
    protected void initDebugConfig() {

    }

//    protected void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }

//    protected void initStetho() {
//         Stetho.initializeWithDefaults(this);
//    }

//    protected void initStrictMode() {
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
//        }
//    }

}
