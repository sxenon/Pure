package com.sxenon.pure.core.mvp.impl.root;

import com.sxenon.pure.core.Event;
import com.sxenon.pure.core.mvp.impl.BasePresenter;
import com.sxenon.pure.core.mvp.interfaces.root.IRootPresenter;
import com.sxenon.pure.core.mvp.interfaces.root.IRootViewModule;

/**
 * Created by Sui on 2016/11/22.
 */

public class BaseRootPresenter<VM extends IRootViewModule> extends BasePresenter<VM> implements IRootPresenter<VM> {
    public BaseRootPresenter(VM viewModule) {
        super(viewModule);
    }

    @Override
    public void onCreate(Event savedEvent) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
