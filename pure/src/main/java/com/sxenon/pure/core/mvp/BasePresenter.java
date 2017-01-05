package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.sub.BaseSubPresenter;
import com.sxenon.pure.core.router.IRouter;

/**
 * Please use {@link BaseRootPresenter} with {@link BaseSubPresenter}(if needed).
 * Created by Sui on 2017/1/5.
 */

public abstract class BasePresenter<VM extends IViewModule> implements IPresenter<VM> {
    private final VM mViewModule;
    private final Context mContext;
    private final IRouter mRouter;

    public BasePresenter(VM viewModule){
        mViewModule = viewModule;
        mContext = mViewModule.getContext();
        mRouter = mViewModule.getRouter();
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
