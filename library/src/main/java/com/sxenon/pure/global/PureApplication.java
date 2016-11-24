package com.sxenon.pure.global;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Sui on 2016/11/24.
 */

public abstract class PureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDebugConfig();
    }

    private void initDebugConfig(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Stetho.initializeWithDefaults(this);
        CrashReport.initCrashReport(this);
    }
}
