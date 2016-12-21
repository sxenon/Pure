package com.sxenon.pure.filler.pull;

/**
 * Layout for pull down or up
 * Created by Sui on 2016/12/11.
 */

public interface IBasePullLayout {
    void beginRefreshing();

    void beginLoadingMore();

    void endLoadingMore();

    void endRefreshing();

    void setVisibility(int visibility);

    void setDelegate(PullDelegate delegate);

    interface PullDelegate {
        void onBeginRefreshing();

        boolean onBeginLoadingMore();
    }
}
