package com.sxenon.pure.core.router;

import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureRootPresenter<VM extends BaseRootViewModule> extends PureBaseRootPresenter<VM> {
    public PureRootPresenter(VM viewModule) {
        super(viewModule);
    }

    @Override
    protected final boolean requestCommonPermissionsBySelf(int requestCode) {
        return true;
    }

    @Override
    protected final boolean startActivityForResultBySelf(int requestCode) {
        return true;
    }
}
