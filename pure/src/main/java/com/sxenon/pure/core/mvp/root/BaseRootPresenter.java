package com.sxenon.pure.core.mvp.root;

import android.support.annotation.NonNull;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.IPresenter;

import rx.functions.Action0;

/**
 * * Include several {@link BasePresenter}，with its related {@link BaseViewModule}
 * Don`t use it directly,use PureRootPresenter instead.
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule> extends BasePresenter<VM> implements IPresenter<VM>,ILifeCycle {

    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    {
        bindRootPresenter(this);
    }

    @Override
    public void onCreate(Event savedEvent) {
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        RxBus.get().unregister(this);
    }

    public abstract void requestCommonPermissions(@NonNull String[] permissions, int what, Action0 action);

    public abstract void requestSystemAlertPermission(int what, Action0 action);

    public abstract Event getEventForSave();
}
