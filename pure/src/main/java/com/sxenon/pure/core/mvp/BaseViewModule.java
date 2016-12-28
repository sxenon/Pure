package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.component.IViewComponentGroup;

/**
 * Include {@link android.view.View} & {@link IViewComponentGroup
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseViewModule<P extends IPresenter> implements IViewModule<P> {
    private final P mPresenter;
    private final Context mContext;
    private final IRouter mRouter;

    public BaseViewModule(IRouter router){
        mContext=router.getActivityCompact();
        mRouter=router;
        mPresenter= bindPresenter();
    }

    @Override
    public P getPresenter() {
        return mPresenter;
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

    protected abstract P bindPresenter();
}
