package com.sxenon.pure.core.mvp.sub;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxenon.pure.core.mvp.IPresenter;
import com.sxenon.pure.core.mvp.sub.BaseSubViewModule;
import com.sxenon.pure.core.router.IRouter;
import com.sxenon.pure.core.mvp.root.BaseRootPresenter;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.Observable;
import rx.functions.Action0;

/**
 * To replace those fragments,for example,which are used for Master/Detail to support both phone and tab
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseSubPresenter<VM extends BaseSubViewModule> implements IPresenter<VM> {
    private final VM mViewModule;
    private final Context mContext;
    private final IRouter mRouter;
    private final BaseRootPresenter mRootPresenter;

    public BaseSubPresenter(VM viewModule,BaseRootPresenter rootPresenter) {
        mViewModule = viewModule;
        mContext = mViewModule.getContext();
        mRouter = mViewModule.getRouter();
        mRootPresenter = rootPresenter;
    }

    @Override
    public BaseRootPresenter getRootPresenter() {
        return mRootPresenter;
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
    public <R> Observable<R> autoUnsubscribe(Observable<R> observable) {
        //noinspection unchecked
        return mRootPresenter.autoUnsubscribe(observable);
    }

    public void registerActionOnResume(Action0 action){
        mRootPresenter.registerActionOnResume(action);
    }

    public void registerActionOnPause(Action0 action){
        mRootPresenter.registerActionOnPause(action);
    }

    public void registerActionOnStop(Action0 action){
        mRootPresenter.registerActionOnStop(action);
    }

    public void registerActionOnDestroy(Action0 action){
        mRootPresenter.registerActionOnDestroy(action);
    }

}
