package com.sxenon.pure.router.empty;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.root.BaseRootViewModule;
import com.sxenon.pure.router.PureRootPresenter;

import java.util.List;

/**
 * EmptyRootPresenter
 * Created by Sui on 2016/12/15.
 */

public class EmptyRootPresenter extends PureRootPresenter<BaseRootViewModule> {
    public EmptyRootPresenter(BaseRootViewModule viewModule) {
        super(viewModule);
    }

    @Override
    public List<Event> getEventForSave() {
        return null;
    }
}
