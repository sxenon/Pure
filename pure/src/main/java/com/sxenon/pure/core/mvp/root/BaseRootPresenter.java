package com.sxenon.pure.core.mvp.root;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.binder.IViewBinder;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.IPresenter;
import com.sxenon.pure.core.router.IRouter;

import java.util.List;

import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import rx.functions.Action0;

/**
 * * Include several {@link BasePresenter}，with its related {@link BaseViewModule}
 * Don`t use it directly,use PureBaseRootPresenter instead.
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule>  implements IPresenter<VM>, ILifeCycle {

    private final VM mViewModule;
    private final Context mContext;
    private final IRouter mRouter;

    private boolean mResumed;
    private boolean mPaused;
    private boolean mStopped;
    private boolean mDestroyed;

    public BaseRootPresenter(VM viewModule) {
        mViewModule = viewModule;
        mContext=mViewModule.getContext();
        mRouter=mViewModule.getRouter();
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

    @Override
    public BaseRootPresenter getRootPresenter() {
        return this;
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

    public abstract void setOnKeyboardShowingListener(KeyboardUtil.OnKeyboardShowingListener listener);
}
