package com.sxenon.pure.core.mvp.sub;

import android.support.annotation.NonNull;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.trello.rxlifecycle.LifecycleTransformer;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.functions.Action0;

/**
 * Optional,for those complex {@link BaseRootPresenter}s
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseSubPresenter<VM extends BaseSubViewModule> extends BasePresenter<VM> {
    private final BaseRootPresenter mRootPresenter;

    public BaseSubPresenter(VM viewModule,BaseRootPresenter rootPresenter) {
        super(viewModule);
        mRootPresenter = rootPresenter;
    }

    @Override
    public BaseRootPresenter getRootPresenter() {
        return mRootPresenter;
    }

    public void requestCommonPermissions(@NonNull String[] permissions, int requestCode, Action0 action) {
        mRootPresenter.requestCommonPermissions(permissions, requestCode, action);
    }

    public void requestSystemAlertPermission(int requestCode, Action0 action) {
        mRootPresenter.requestSystemAlertPermission(requestCode, action);
    }

    public void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener) {
        mRootPresenter.setOnKeyboardShowingListener(listener);
    }

    @Override
    public <T> LifecycleTransformer<T> autoUnsubscribe() {
        //noinspection unchecked
        return mRootPresenter.autoUnsubscribe();
    }

    @Override
    public void registerActionOnDestroy(Action0 action) {
        mRootPresenter.registerActionOnDestroy(action);
    }
}
