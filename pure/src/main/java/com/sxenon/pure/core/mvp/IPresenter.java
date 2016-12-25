package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

/**
 * Presenter For Mvp
 * Created by Sui on 2016/11/21.
 */

public interface IPresenter<VM extends IViewModule> {
    VM getViewModule();

    @NonNull
    Context getContext();

    @NonNull
    IRouter getRouter();

    BaseRootPresenter getRootPresenter();
}
