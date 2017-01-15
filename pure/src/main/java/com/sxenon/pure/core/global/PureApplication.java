/*
 * Copyright (c) 2017 sxenon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sxenon.pure.core.global;

import android.app.Application;

/**
 * Application of the framework
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
