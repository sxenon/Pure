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
    protected boolean requestCommonPermissionsBySelf(int requestCode) {
        return false;
    }

    @Override
    protected boolean requestSystemAlertPermissionBySelf(int requestCode) {
        return false;
    }

    @Override
    protected boolean startActivityForResultBySelf(int requestCode) {
        return false;
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }
}
