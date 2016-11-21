package com.sxenon.pure.core.mvp.interfaces.root;

import com.sxenon.pure.core.mvp.interfaces.ILifeCycle;
import com.sxenon.pure.core.mvp.interfaces.IPresenter;

/**
 * Created by Sui on 2016/11/21.
 */

public interface IRootPresenter<VM extends IRootViewModule> extends IPresenter<VM>,ILifeCycle {

}
