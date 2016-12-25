package com.sxenon.pure.core.mvp.root;

import android.support.annotation.NonNull;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.binder.IViewBinder;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.IPresenter;

import java.util.List;

import rx.functions.Action0;

/**
 * * Include several {@link BasePresenter}，with its related {@link BaseViewModule}
 * Don`t use it directly,use PureBaseRootPresenter instead.
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule> extends BasePresenter<VM> implements IPresenter<VM>, ILifeCycle {

    private boolean mResumed;
    private boolean mPaused;
    private boolean mStopped;
    private boolean mDestroyed;

    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    {
        bindRootPresenter(this);
    }

    @Override
    public void onCreate(List<Event> savedEventList) {
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {
        mResumed = true;
        mPaused = false;
        mStopped = false;
    }

    @Override
    public void onPause() {
        mResumed = false;
        mPaused = true;
        mStopped = false;
    }

    @Override
    public void onStop() {
        mResumed = false;
        mPaused = true;
        mStopped = true;
    }

    @Override
    public void onDestroy() {
        mDestroyed = true;
        RxBus.get().unregister(this);
    }

    public boolean isResumed() {
        return mResumed;
    }

    public boolean isPaused() {
        return mPaused;
    }

    public boolean isStopped() {
        return mStopped;
    }

    public boolean isDestroyed() {
        return mDestroyed;
    }

    public abstract void requestCommonPermissions(int requestCode, @NonNull String[] permissions, Action0 action);

    public abstract void requestSystemAlertPermission(int requestCode, Action0 action);

    public abstract List<Event> getEventForSave();

    public abstract IViewBinder getViewBinder();
}
