package com.sxenon.pure.core.router.support;

import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.core.router.PureBaseRootPresenter;

/**
 * Created by Sui on 2016/12/25.
 */

public abstract class PureCompactRootPresenter<VM extends BaseRootViewModule> extends PureBaseRootPresenter<VM> {
    public PureCompactRootPresenter(VM viewModule) {
        super(viewModule);
    }

    protected boolean startActivityForResultBySelf(int requestCode) {
        return false;
    }

    protected boolean requestCommonPermissionsBySelf(int requestCode) {
        return true;
    }
}
