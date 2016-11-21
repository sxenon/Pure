package com.sxenon.pure.core.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.interfaces.IPresenter;
import com.sxenon.pure.core.mvp.interfaces.IViewModule;

/**
 * Created by Sui on 2016/11/22.
 */

public class BasePresenter<VM extends IViewModule> implements IPresenter<VM> {
    private final VM mViewModule;
    private final Context mContext;
    private final IRouter mRouter;

    public BasePresenter(VM viewModule) {
        mViewModule = viewModule;
        mContext=mViewModule.getContext();
        mRouter=mViewModule.getRouter();
    }

    @Override
    public VM getViewModule() {
        return mViewModule;
    }

    @NonNull
    @Override
    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public IRouter getRouter() {
        return mRouter;
    }
}
