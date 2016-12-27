package com.sxenon.pure.core.component.filler;

/**
 * Layout for pull down or up
 * Created by Sui on 2016/12/11.
 */

public interface IPullLayout {
    void beginRefreshing();

    void beginLoadingMore();

    void endLoadingMore();

    void endRefreshing();

    void setVisibility(int visibility);

    interface Delegate {
        boolean onBeginRefreshing();

        boolean onBeginLoadingMore();
    }

}
