package com.sxenon.pure.router;

import com.sxenon.pure.core.mvp.root.BaseRootPresenter;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * Created by Sui on 2016/11/28.
 */

public class PureRootPresenter<VM extends BaseRootViewModule> extends BaseRootPresenter<VM> {

    public PureRootPresenter(VM viewModule) {
        super(viewModule);
    }
}
