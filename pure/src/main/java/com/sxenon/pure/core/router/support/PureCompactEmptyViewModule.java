package com.sxenon.pure.core.router.support;

import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.router.IRouter;

/**
 * Created by Sui on 2016/12/25.
 */

public class PureCompactEmptyViewModule extends BaseRootViewModule<PureCompactEmptyRootPresenter> {
    public PureCompactEmptyViewModule(IRouter router) {
        super(router);
    }

    @Override
    protected PureCompactEmptyRootPresenter bindPresenter() {
        return new PureCompactEmptyRootPresenter(this);
    }
}
