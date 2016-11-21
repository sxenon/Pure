package com.sxenon.pure.core.mvp.impl.root;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.IRouter;
import com.sxenon.pure.core.mvp.impl.BasePresenter;
import com.sxenon.pure.core.mvp.impl.BaseViewModule;
import com.sxenon.pure.core.mvp.interfaces.root.IRootPresenter;
import com.sxenon.pure.core.mvp.interfaces.root.IRootViewModule;

/**
 * 包含多个{@link BaseViewModule}，各自对应一个{@link BasePresenter}
 * Created by Sui on 2016/11/22.
 */

public class BaseRootViewModule<P extends IRootPresenter> extends BaseViewModule<P> implements IRootViewModule<P> {
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
