package com.sxenon.pure.core.mvp.root;

import com.hwangjr.rxbus.RxBus;
import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.sub.BaseSubPresenter;
import com.sxenon.pure.core.mvp.sub.BaseSubViewModule;

import java.util.List;

/**
 * * Include several {@link BaseSubPresenter}，with its related {@link BaseSubViewModule}
 * Don`t use it directly,use PureRootPresenter instead.
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootPresenter<VM extends BaseRootViewModule> extends BasePresenter<VM> implements ILifeCycle {
    private RootPresenterEvent currentEvent;

    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    @Override
    public void onCreate(List<Event> savedEventList) {
        currentEvent = RootPresenterEvent.CREATE;
        RxBus.get().register(this);
    }

    @Override
    public void onResume() {
        currentEvent = RootPresenterEvent.RESUME;
    }

    @Override
    public void onPause() {
        currentEvent = RootPresenterEvent.PAUSE;
    }

    @Override
    public void onStop() {
        currentEvent = RootPresenterEvent.STOP;
    }

    @Override
    public void onDestroy() {
        currentEvent = RootPresenterEvent.DESTROY;
        RxBus.get().unregister(this);
    }

    @Override
    public BaseRootPresenter getRootPresenter() {
        return this;
    }

    public RootPresenterEvent getCurrentEvent(){
        return currentEvent;
    }

    public abstract List<Event> getEventForSave();

}
