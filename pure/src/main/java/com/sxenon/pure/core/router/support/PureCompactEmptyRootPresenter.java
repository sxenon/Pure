package com.sxenon.pure.core.router.support;

import com.sxenon.pure.core.Event;

import java.util.List;

/**
 * Created by Sui on 2016/12/25.
 */

public class PureCompactEmptyRootPresenter extends PureCompactRootPresenter<PureCompactEmptyViewModule> {
    public PureCompactEmptyRootPresenter(PureCompactEmptyViewModule viewModule) {
        super(viewModule);
    }

    @Override
    protected boolean requestCommonPermissionsSelf(int requestCode) {
        return false;
    }

    @Override
    protected boolean requestSystemAlertPermissionSelf(int requestCode) {
        return false;
    }

    @Override
    protected boolean startActivitySelf(int requestCode) {
        return false;
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }
}
