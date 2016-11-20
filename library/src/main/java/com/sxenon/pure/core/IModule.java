package com.sxenon.pure.core;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 通用模块接口
 * 包含了对数据处理逻辑的抽象
 * Created by Sui on 2016/11/20.
 */

public interface IModule {
    @NonNull
    Context getContext();

    void onCreate(Event savedEvent);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
