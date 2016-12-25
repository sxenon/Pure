package com.sxenon.pure.core.router;

import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * EmptyRootViewModule
 * Created by Sui on 2016/12/15.
 */

public class EmptyRootViewModule extends BaseRootViewModule<EmptyRootPresenter> {
    public EmptyRootViewModule(IRouter router) {
        super(router);
    }

    @Override
    protected EmptyRootPresenter bindPresenter() {
        return new EmptyRootPresenter(this);
    }
}
