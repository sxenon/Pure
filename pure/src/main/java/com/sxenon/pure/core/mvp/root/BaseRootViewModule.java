package com.sxenon.pure.core.mvp.root;

import com.sxenon.pure.core.mvp.sub.BaseSubPresenter;
import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.router.IRouter;

/**
 * Include several {@link BaseViewModule}，with its related{@link BaseSubPresenter}
 * Created by Sui on 2016/11/22.
 */

public abstract class BaseRootViewModule<P extends BaseRootPresenter> extends BaseViewModule<P> {

    public BaseRootViewModule(IRouter router) {
        super(router);
    }

}
