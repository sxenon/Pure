package com.sxenon.pure.core.mvp.root;

import com.sxenon.pure.core.mvp.BasePresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.IViewModule;
import com.sxenon.pure.router.IRouter;

/**
 * Include several {@link BaseViewModule}，with its related{@link BasePresenter}
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootViewModule<P extends BaseRootPresenter> extends BaseViewModule<P> implements IViewModule<P> {

    public BaseRootViewModule(IRouter router) {
        super(router);
    }

}
