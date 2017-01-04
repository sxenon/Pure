package com.sxenon.pure.core.mvp.sub;

import com.sxenon.pure.core.mvp.BaseViewModule;
import com.sxenon.pure.core.router.IRouter;

/**
 * To replace those fragments,for example,which are used for Master/Detail to support both phone and tab
 * Created by Sui on 2017/1/4.
 */

public abstract class BaseSubViewModule<P extends BaseSubPresenter> extends BaseViewModule<P> {
    public BaseSubViewModule(IRouter router) {
        super(router);
    }
}
