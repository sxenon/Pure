package com.sxenon.pure.core.router;

/**
 * Created by Sui on 2017/1/2.
 */

public interface IActivity<P extends PureRootPresenter> extends IRouter<P> {
    boolean shouldKeepWhenBackground(int what);
    void finish();
    boolean isFinishing();
}
