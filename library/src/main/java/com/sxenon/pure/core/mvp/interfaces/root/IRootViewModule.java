package com.sxenon.pure.core.mvp.interfaces.root;

import com.sxenon.pure.core.mvp.interfaces.ILifeCycle;
import com.sxenon.pure.core.mvp.interfaces.IViewModule;

/**
 * Created by Sui on 2016/11/21.
 */

public interface IRootViewModule<P extends IRootPresenter> extends IViewModule<P>,ILifeCycle{

}
