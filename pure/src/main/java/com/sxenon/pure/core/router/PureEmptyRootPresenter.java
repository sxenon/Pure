package com.sxenon.pure.core.router;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;

import java.util.List;

/**
 * PureEmptyRootPresenter
 * Created by Sui on 2016/12/15.
 */

public class PureEmptyRootPresenter extends PureRootPresenter<BaseRootViewModule> {
    public PureEmptyRootPresenter(BaseRootViewModule viewModule) {
        super(viewModule);
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }
}
