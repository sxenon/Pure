package com.sxenon.pure.core.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.IViewComponentGroup;
import com.sxenon.pure.core.mvp.interfaces.IPresenter;
import com.sxenon.pure.core.mvp.interfaces.IViewModule;

/**
 * 包含{@link android.view.View} & {@link IViewComponentGroup
 * Created by Sui on 2016/11/22.
 */

public class BaseViewModule<P extends IPresenter> implements IViewModule<P> {
    private P mPresenter;
    private final Context mContext;
    private final IRouter mRouter;

    public BaseViewModule(IRouter router){
        mContext=router.getActivity();
        mRouter=router;
    }

    public void setPresenter(P presenter) {
        mPresenter=presenter;
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
}
