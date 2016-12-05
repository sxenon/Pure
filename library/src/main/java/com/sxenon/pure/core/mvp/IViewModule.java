package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.IRouter;

/**
 * View for MVP,include common view and viewComponentGroup
 * Created by Sui on 2016/11/21.
 */

public interface IViewModule<P extends IPresenter> {

    P getPresenter();

    @NonNull
    Context getContext();

    @NonNull
    IRouter getRouter();
}
