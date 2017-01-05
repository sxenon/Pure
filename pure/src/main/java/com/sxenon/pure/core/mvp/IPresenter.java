package com.sxenon.pure.core.mvp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.trello.rxlifecycle.LifecycleTransformer;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.functions.Action0;

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

    void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action);

    void requestSystemAlertPermission(int requestCode, Action0 action);

    void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener);

    <T> LifecycleTransformer<T> autoUnsubscribe();

    void registerActionOnDestroy(Action0 action);
}
