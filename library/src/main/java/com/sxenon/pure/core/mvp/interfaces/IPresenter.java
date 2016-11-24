package com.sxenon.pure.core.mvp.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.interfaces.root.IRootPresenter;

/**
 * MVP中 Presenter基础接口
 * Created by Sui on 2016/11/21.
 */

public interface IPresenter<VM extends IViewModule> {
    VM getViewModule();

    @NonNull
    Context getContext();

    @NonNull
    IRouter getRouter();

    IRootPresenter getRootPresenter();
}
