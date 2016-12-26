package com.sxenon.pure.core.router;

import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * PureEmptyRootViewModule
 * Created by Sui on 2016/12/15.
 */

public class PureEmptyRootViewModule extends BaseRootViewModule<PureEmptyRootPresenter> {
    public PureEmptyRootViewModule(IRouter router) {
        super(router);
    }

    @Override
    protected PureEmptyRootPresenter bindPresenter() {
        return new PureEmptyRootPresenter(this);
    }
}
