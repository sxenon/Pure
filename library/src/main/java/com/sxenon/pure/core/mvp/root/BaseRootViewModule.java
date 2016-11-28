package com.sxenon.pure.core.mvp.root;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.ILifeCycle;
import com.sxenon.pure.core.mvp.IViewModule;

/**
 * 包含多个{@link BaseViewModule}，各自对应一个{@link BasePresenter}
 * Created by Sui on 2016/11/22.
 */

public class BaseRootViewModule<P extends BaseRootPresenter> extends BaseViewModule<P> implements IViewModule<P>,ILifeCycle {
    public BaseRootViewModule(IRouter router) {
        super(router);
    }

    @Override
    public void onCreate(Event savedEvent) {

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

    }

}
