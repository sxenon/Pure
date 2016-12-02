package com.sxenon.pure.core.mvp.root;

import android.support.annotation.NonNull;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.IPresenter;

import rx.functions.Action0;

/**
 * * 包含多个{@link BasePresenter}，各自对应一个{@link BaseViewModule}
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule> extends BasePresenter<VM> implements IPresenter<VM>,ILifeCycle {

    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    {
        bindRootPresenter(this);
    }

    @Override
    public void onCreate(Event savedEvent) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public abstract void requestPermissions(@NonNull String[] permissions,int what,Action0 action);
}
