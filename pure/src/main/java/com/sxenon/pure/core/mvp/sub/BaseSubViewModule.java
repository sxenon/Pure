package com.sxenon.pure.core.mvp.sub;

import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.router.IRouter;

/**
 * Optional,for those complex {@link BaseRootViewModule}s
 * Created by Sui on 2017/1/4.
 */

public abstract class BaseSubViewModule<P extends BaseSubPresenter> extends BaseViewModule<P> {
    public BaseSubViewModule(IRouter router) {
        super(router);
    }
}
